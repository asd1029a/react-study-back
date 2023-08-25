package com.example.reactstudyback.service;

import com.example.reactstudyback.api.RestTemplateApi;
import com.example.reactstudyback.dto.DisabledParkingDeviceDto;
import com.example.reactstudyback.dto.DisabledParkingDto;
import com.example.reactstudyback.dto.DisabledParkingStatusDeviceDto;
import com.example.reactstudyback.dto.DisabledParkingStatusDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DisabledParkingServiceImpl.java
 * 장애인 주차장 관련 서비스 클래스
 *
 * @author kjm
 * @since 2023.08.21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DisabledParkingServiceImpl implements DisabledParkingService {

    @Value("${api.disabled.parking.url}")
    private String disabledParkingUrl;
    @Value("${api.disabled.parking.status.url}")
    private String disabledParkingStatusUrl;
    @Value("${api.disabled.parking.reset.url}")
    private String disabledParkingResetUrl;

    private final RestTemplateApi restTemplateApi;

    /**
     * 장애인 주차장 위치 저장
     */
    @Override
    public List<DisabledParkingDto> getDisabledParkingData() throws Exception {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplateApi
                .sendRequest(disabledParkingUrl, HttpMethod.GET, null, Map.class);

        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode.is2xxSuccessful()) {
            List<DisabledParkingDto> resultList = convertToDisabledParkingResult(responseEntity);
            if (resultList != null) {
                return resultList;
            }
        }
        throw new Exception("장애인 주차장 위치 데이터 없다");
    }

    /**
     * 장애인 주차장 현재 상태값 조회
     */
    @Override
    public void saveDisabledParkingStatusData() {
        ResponseEntity<Map<String, Object>> responseEntity = restTemplateApi
                .sendRequest(disabledParkingStatusUrl, HttpMethod.GET, null, Map.class);

        HttpStatus statusCode = responseEntity.getStatusCode();

        if (statusCode.is2xxSuccessful()) {
            List<DisabledParkingStatusDto> resultList = convertToDisabledParkingStatusResult(responseEntity);

            if (resultList != null) {

            }
        }
    }

    /**
     * 장비ID(device_id)로 리셋하는 기능
     * @param deviceId
     */
    @Override
    public void resetDevice(String deviceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("device_id", deviceId);
        ResponseEntity<Void> responseEntity = restTemplateApi.sendRequest(disabledParkingResetUrl, HttpMethod.POST, map, Void.class);
    }

    /**
     * 장애인 주차장 위치 값을 api 통신 후 DisabledParkingDto로 변환하는 메소드
     * @param responseEntity
     * @return
     */
    private List<DisabledParkingDto> convertToDisabledParkingResult(ResponseEntity<Map<String, Object>> responseEntity) {
        Map<String, Object> responseMap = responseEntity.getBody();
        boolean isCompleted = (boolean) responseMap.get("status");

        if (isCompleted) {
            String lastUpdate = (String) responseMap.get("last_update");
            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("data");
            int totalLotCnt  = (int) bodyMap.get("total_lot_cnt");
            int totalDeviceCnt = (int) bodyMap.get("total_devices_cnt");

            List<Map<String, Object>> responseBody = (List<Map<String, Object>>) bodyMap.get("list");
            List<DisabledParkingDto> result = responseBody.stream().map(listDto -> {
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) listDto.get("devices");
                List<DisabledParkingDeviceDto> deviceList = mapList.stream()
                        .map(m -> DisabledParkingDeviceDto.ofMap(m)).collect(Collectors.toList());
                return DisabledParkingDto.builder()
                        .pkLotId((String) listDto.get("pk_lot_id"))
                        .name((String) listDto.get("name"))
                        .address((String) listDto.get("address"))
                        .oldAddress((String) listDto.get("old_address"))
                        .latitude(Double.valueOf((String) listDto.get("latitude")))
                        .longitude(Double.valueOf((String) listDto.get("longitude")))
                        .disabledParkingDeviceDtos(deviceList)
                        .build();
            }).collect(Collectors.toList());


            return result;
        }
        return null;
    }

    /**
     * 장애인 주차장 상태 값을 api 통신 후 DisabledParkingStatusDto 변환하는 메소드
     * @param responseEntity
     * @return
     */
    private List<DisabledParkingStatusDto> convertToDisabledParkingStatusResult(ResponseEntity<Map<String, Object>> responseEntity) {
        Map<String, Object> responseMap = responseEntity.getBody();
        boolean isCompleted = (boolean) responseMap.get("status");

        if (isCompleted) {
            String lastUpdate = (String) responseMap.get("last_update");
            Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("data");
            int totalLotCnt  = (int) bodyMap.get("total_lot_cnt");
            int totalDeviceCnt = (int) bodyMap.get("total_devices_cnt");

            List<Map<String, Object>> responseBody = (List<Map<String, Object>>) bodyMap.get("list");
            List<DisabledParkingStatusDto> result = responseBody.stream().map(listDto -> {
                List<Map<String, Object>> mapList = (List<Map<String, Object>>) listDto.get("devices");
                List<DisabledParkingStatusDeviceDto> deviceList = mapList.stream()
                        .map(m -> DisabledParkingStatusDeviceDto.ofMap(m)).collect(Collectors.toList());
                return DisabledParkingStatusDto.builder()
                        .pkLotId((String) listDto.get("pk_lot_id"))
                        .disabledParkingStatusDeviceDtos(deviceList)
                        .build();
            }).collect(Collectors.toList());

            return result;
        }
        return null;
    }
}
