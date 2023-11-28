package com.server.resource.management.ui;

import com.server.resource.management.application.ServerResourceService;
import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ServerResourceController {

    private final ServerResourceService serverResourceService;

    @Async
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void requestNewResource(@Valid @RequestBody ServerResourceRequestDto requestDto) {
        /**
         * 1. 사용자 name 으로 찾고 없으면 사용자 생성 후 할당
         * 2. 수정사항도 사용자 NAME에 VALIDATION 적용
         * 3. 리소스 삭제는, 삭제가 아닌 0으로 처리
         */
        serverResourceService.requestNewResource(requestDto);
    }
}
