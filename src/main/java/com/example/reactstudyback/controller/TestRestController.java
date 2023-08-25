package com.example.reactstudyback.controller;

import com.example.reactstudyback.dto.DisabledParkingDto;
import com.example.reactstudyback.dto.TestDto;
import com.example.reactstudyback.service.DisabledParkingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TestRestController.java
 * Class 설명을 작성하세요.
 *
 * @author kjm
 * @since 2023.08.17
 */
@RestController
@CrossOrigin(originPatterns = "http://localhost:3001")
@Slf4j
@RequiredArgsConstructor
public class TestRestController {

    private final DisabledParkingService disabledParkingService;

    @GetMapping("/disabled")
    public List<DisabledParkingDto> test() throws Exception {
        return disabledParkingService.getDisabledParkingData();
    }
}
