package com.example.reactstudyback.dto;

import lombok.*;

import java.util.Map;

/**
 * DisabledParkingDeviceDto.java
 * 장애인 주차장 디바이스 DTO
 *
 * @author kjm
 * @since 2023.08.21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisabledParkingDeviceDto {

    /** 디바이스 아이디 */
    private String deviceId;
    /** 디바이스 넘버 */
    private String mgmtNum;
    /** 위도 */
    private Double latitude;
    /** 경도 */
    private Double longitude;
    /** 층 */
    private String floor;

    /**
     * 맵데이터 DisabledParkingDeviceDto로 변환 메소드
     * @param map
     * @return
     */
    public static DisabledParkingDeviceDto ofMap(Map<String, Object> map) {
        return DisabledParkingDeviceDto.builder()
                .deviceId((String) map.get("device_id"))
                .mgmtNum((String) map.get("mgmt_num"))
                .latitude(Double.valueOf((String) map.get("latitude")))
                .longitude(Double.valueOf((String) map.get("longitude")))
                .floor((String) map.get("floor"))
                .build();
    }

}
