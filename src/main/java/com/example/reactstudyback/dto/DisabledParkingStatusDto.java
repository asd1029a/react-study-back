package com.example.reactstudyback.dto;

import lombok.*;

import java.util.List;

/**
 * DisabledParkingStatusDto.java
 * 장애인 주차장 상태 DTO
 *
 * @author kjm
 * @since 2023.08.21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisabledParkingStatusDto {

    /** 주자장 상태 기본키 */
    private String pkLotId;
    /** 주차장 디바이스 상태 리스트 */
    private List<DisabledParkingStatusDeviceDto> disabledParkingStatusDeviceDtos;
}
