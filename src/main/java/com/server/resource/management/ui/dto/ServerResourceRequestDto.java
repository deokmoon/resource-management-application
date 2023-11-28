package com.server.resource.management.ui.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ServerResourceRequestDto {

    @Min(value = 1, message = "할당하고자 하는 cpu 는 1개 이상의 숫자를 입력해주세요")
    private Long cpu;

    @Min(value = 1, message = "할당하고자 하는 memory 는 1개 이상의 숫자를 입력해주세요")
    private Long memory;

    @Min(value = 1, message = "서버ID는 1부터 시작하는 숫자입니다.")
    private Long serverId;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String userName;

}
