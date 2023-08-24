package com.example.reactstudyback.controller;

import com.example.reactstudyback.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TestRestController {

    @GetMapping("/test")
    public List<TestDto> test() {
        List<TestDto> list = new ArrayList<>();
        TestDto testDto = new TestDto();
        testDto.setEmail("email1@asd.com");
        testDto.setName("김정민");
        TestDto testDto2 = new TestDto();
        testDto2.setEmail("email1@asd.com");
        testDto2.setName("김정민");
        TestDto testDto3 = new TestDto();
        testDto3.setEmail("email1@asd.com");
        testDto3.setName("김정민");
        list.add(testDto);
        list.add(testDto2);
        list.add(testDto3);

        log.info("보낸 데이터 = {}",list);
        return list;
    }
}
