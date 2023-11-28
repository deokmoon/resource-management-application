package com.server.resource.management.ui.dto;

import com.server.resource.management.domain.UserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResourceListResponseDto {
    private String userName;
    private long serverId;
    private String serverName;
    private long serverTotalCpu;
    private long serverTotalMemory;
    private long userUsedCpu;
    private long userUsedMemory;

    public static UserResourceListResponseDto from(UserResource userResource) {
        return new UserResourceListResponseDto(userResource.getUser().getName()
                , userResource.getServerId()
                , userResource.getServerName()
                , userResource.getServerTotalCpu()
                , userResource.getServerTotalMemory()
                , userResource.getUsedCpu()
                , userResource.getUsedMemory());

    }
}
