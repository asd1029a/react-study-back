package com.example.reactstudyback.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

/**
 * RestTemplateApi.java
 * Restemplate Api 클래스
 *
 * @author kjm
 * @since 2023.08.21
 */
@Slf4j
@Component
public class RestTemplateApi {

    private static final int REQUEST_COUNT = 3;

    /**
     * api 요청 메소드
     * @param url
     * @param method
     * @param requestData
     * @param responseType
     * @return
     */
    public ResponseEntity sendRequest(String url, HttpMethod method, Map<String, Object> requestData, Class<?> responseType) {
        RestTemplate restTemplate = createSSLIgnoreRestTemplate();
        HttpHeaders httpHeaders = createHttpHeaders(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity;
        ResponseEntity<?> responseEntity;
        if (requestData != null) {
            httpEntity = new HttpEntity<>(requestData, httpHeaders);
        } else {
            httpEntity = new HttpEntity<>(httpHeaders);
        }

        int attempts = 0;
        while(attempts < REQUEST_COUNT) {

            try {
                responseEntity = restTemplate.exchange(url, method, httpEntity, responseType);
                return responseEntity;

            } catch (RestClientResponseException badResponse) {
                attempts++;
                log.error("[SunCheon Api] ResponseException ! ERROR COUNT = {}", attempts);

            } catch (ResourceAccessException e) {
                if(e.getCause() instanceof SocketTimeoutException){
                    attempts++;
                    log.error("[SunCheon Packing Api] Time Out !");
                    continue;
                }
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }

    /**
     * 헤더 생성
     *
     * @param mediaType 미디어 타입
     * @return HttpHeaders
     */
    public HttpHeaders createHttpHeaders(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setAccept(Collections.singletonList(mediaType));
        return headers;
    }


    /**
     * SSL 무시 RestTemplate 생성 메소드
     * @return RestTemplate
     */
    public RestTemplate createSSLIgnoreRestTemplate() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setSslcontext(createSSLContextWithTrustingAllCertificates())
                .build();

        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .additionalMessageConverters(
                        new StringHttpMessageConverter(StandardCharsets.UTF_8),
                        new MappingJackson2HttpMessageConverter())
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

    /**
     * SSL 무시 메소드
     * @return
     */
    private SSLContext createSSLContextWithTrustingAllCertificates() {
        try {
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

            return sslContext;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create SSL context.", e);
        }
    }
}
