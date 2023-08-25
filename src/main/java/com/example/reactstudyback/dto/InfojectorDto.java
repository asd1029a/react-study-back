package com.example.reactstudyback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * InfojectorDto.java
 * 인포젝터 DTO
 *
 * @author kjm
 * @since 2023.08.21
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InfojectorDto {

    /** ip */
    private String ip;
    /** 상태 값 */
    private String result;

    /**
     * 인포젝터 DTO 생성 메소드
     * @param ip
     * @param result
     * @return
     */
    public static InfojectorDto create(String ip, String result) {
        InfojectorDto infojectorDto = new InfojectorDto();
        infojectorDto.setIp(ip);
        infojectorDto.setResult(result);
        return infojectorDto;
    }

    /**
     * 상태 값에 따른 boolean값 return 메소드
     * @return
     */
    public boolean checked() {
        if ("0".equals(result)) {
            return true;
        }
        return false;
    }
}
