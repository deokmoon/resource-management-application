package com.server.resource.management.ui.dto;

import com.server.resource.management.domain.ServerResource;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerResourceUsageStateResponseDto {
    private long id;
    private String name;
    private long totalCpu;
    private long totalMemory;
    private long usedCpu;
    private long usedMemory;

    public static ServerResourceUsageStateResponseDto from(ServerResource serverResource) {
        return new ServerResourceUsageStateResponseDto(
                serverResource.getId()
                , serverResource.getName()
                , serverResource.getTotalCpu()
                , serverResource.getTotalMemory()
                , serverResource.getUsedCpu()
                , serverResource.getUsedMemory());
    }
}
