package com.example.reactstudyback.dto;

import lombok.*;

import java.util.List;

/**
 * DisabledParkingDto.java
 * 장애인 주차장 DTO
 *
 * @author kjm
 * @since 2023.08.21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisabledParkingDto {

    /** 주차장 기본키 */
    private String pkLotId;
    /** 주차장 이름 */
    private String name;
    /** 주소 */
    private String address;
    /** 옛날 주소 */
    private String oldAddress;
    /** 위도 */
    private Double latitude;
    /** 경도 */
    private Double longitude;
    /** 디바이스 리스트 */
    private List<DisabledParkingDeviceDto> disabledParkingDeviceDtos;
}
