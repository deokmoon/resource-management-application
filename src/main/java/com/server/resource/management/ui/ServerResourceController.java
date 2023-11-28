package com.server.resource.management.ui;

import com.server.resource.management.application.ServerResourceService;
import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PatchMapping;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void requestNewResource(@Valid @RequestBody ServerResourceRequestDto requestDto) {
        serverResourceService.requestNewResource(requestDto);
    }

    @Async
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifyResource(@Valid @RequestBody ServerResourceRequestDto requestDto) {
        serverResourceService.modifyResource(requestDto);
    }


}
