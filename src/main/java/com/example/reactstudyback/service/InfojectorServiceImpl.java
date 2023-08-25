package com.example.reactstudyback.service;

import com.example.reactstudyback.api.RestTemplateApi;
import com.example.reactstudyback.dto.InfojectorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * InfojectorServiceImpl.java
 * Class 설명을 작성하세요.
 *
 * @author kjm
 * @since 2023.08.21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InfojectorServiceImpl implements InfojectorService {

    @Value("${api.infojector.url}")
    private String infojectorUrl;

    private final RestTemplateApi restTemplateApi;

    /**
     * 인포젝터 상태 조회 메소드
     */
    @Override
    public void checkStatus() {
        //추후 db에서 데이트 조회 후 List로 가져오기
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("infojectorip", "172.20.14.53");
        Map<String, Object> map2 = new HashMap<>();
        map1.put("infojectorip", "172.20.14.54");
        mapList.add(map1);
        mapList.add(map2);

        List<InfojectorDto> resultList = mapList.stream().map(m -> {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplateApi
                    .sendRequest(infojectorUrl, HttpMethod.GET, m, Map.class);
            HttpStatus statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                Map<String, Object> body = responseEntity.getBody();
                return InfojectorDto.create((String) m.get("infojectorip"), (String) body.get("result"));
            }
            log.error("[INFOJECTOR API ERROR] ERROR IP = {}",m.get("infojectorip"));
            return null;
        }).filter(Objects::isNull)
                .collect(Collectors.toList());

        resultList.stream().forEach(infojectorDto -> {
            if (infojectorDto.checked()) {
                //infojector 정상 처리
            } else {
                //infojector 비정상 처리
            }
        });
    }
}
