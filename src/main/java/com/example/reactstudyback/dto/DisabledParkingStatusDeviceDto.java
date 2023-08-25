package com.example.reactstudyback.dto;

import lombok.*;

import java.util.Map;

/**
 * DisabledParkingStatusDeviceDto.java
 * 장애인 주차장 디바이스 상태 DTO
 *
 * @author kjm
 * @since 2023.08.21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisabledParkingStatusDeviceDto {

    /** 디바이스 아이디 */
    private String deviceId;
    /** 주차장소 */
    private String packing;
    /** ? */
    private int failure;

    /**
     * 맵데이터 DisabledParkingStatusDeviceDto로 변환 메소드
     * @param map
     * @return
     */
    public static DisabledParkingStatusDeviceDto ofMap(Map<String, Object> map) {
        return DisabledParkingStatusDeviceDto.builder()
                .deviceId((String) map.get("device_id"))
                .packing((String) map.get("packing"))
                .failure((int) map.get("failure"))
                .build();
    }

}
