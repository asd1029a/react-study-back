package com.example.reactstudyback.service;

import com.example.reactstudyback.dto.DisabledParkingDto;

import java.util.List;

/**
 * DisabledParkingService.java
 * 장애인 주차장 관련 서비스 클래스
 *
 * @author kjm
 * @since 2023.08.21
 */
public interface DisabledParkingService {

    /**
     * 장애인 주차장 위치 저장
     */
    List<DisabledParkingDto> getDisabledParkingData() throws Exception;

    /**
     * 장애인 주차장 현재 상태값 조회
     */
    void saveDisabledParkingStatusData();

    /**
     * 장비ID(device_id)로 리셋하는 기능
     * @param deviceId
     */
    void resetDevice(String deviceId);

}
