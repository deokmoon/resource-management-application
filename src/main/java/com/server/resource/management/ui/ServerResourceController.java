package com.server.resource.management.ui;

import com.server.resource.management.application.ServerResourceService;
import com.server.resource.management.ui.dto.DeleteResourceRequestDto;
import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import com.server.resource.management.ui.dto.ServerResourceUsageStateResponseDto;
import com.server.resource.management.ui.dto.UserResourceListResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ServerResourceController {

    private final ServerResourceService serverResourceService;

    @Async
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void requestNewResource(@Valid @RequestBody ServerResourceRequestDto requestDto) {
        serverResourceService.requestNewResource(requestDto);
    }

    @Async
    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyResource(@Valid @RequestBody ServerResourceRequestDto requestDto) {
        serverResourceService.modifyResource(requestDto);
    }

    @Async
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@Valid @RequestBody DeleteResourceRequestDto requestDto) {
        serverResourceService.deleteResource(requestDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResourceListResponseDto>> showUserResourceList(@RequestParam @NotBlank String userName) {
        return ResponseEntity.ok(serverResourceService.showUserResourceList(userName));
    }

    @GetMapping("/server")
    public ResponseEntity<ServerResourceUsageStateResponseDto> showServerResource(@RequestParam Long serverId) {
        return ResponseEntity.ok(serverResourceService.showServerResource(serverId));
    }

    @GetMapping("/all/server")
    public ResponseEntity<List<ServerResourceUsageStateResponseDto>> showServerResource() {
        return ResponseEntity.ok(serverResourceService.showServerResource());
    }


}
