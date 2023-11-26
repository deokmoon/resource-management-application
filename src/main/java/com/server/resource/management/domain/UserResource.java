package com.server.resource.management.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long usedCpu;
    private long usedMemory;

    @ManyToOne
    @JoinColumn(name = "server_resource_id")
    private ServerResource serverResource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private UserResource(long usedCpu, long usedMemory, ServerResource serverResource, User user) {
        this.usedCpu = usedCpu;
        this.usedMemory = usedMemory;
        this.user = user;
        this.serverResource = serverResource;
        serverResource.addUsedResource(usedCpu, usedMemory);
    }

    public static UserResource from(long usedCpu, long usedMemory, ServerResource serverResource, User user) {
        return new UserResource(usedCpu, usedMemory, serverResource, user);
    }

    public void modifyUsingResource(long modifyCpu, long modifyMemory) {
        long tempCpu = modifyCpu - this.usedCpu;
        long tempMemory = modifyMemory - this.usedMemory;
        serverResource.addUsedResource(tempCpu, tempMemory);
        this.usedCpu = modifyCpu;
        this.usedMemory = modifyMemory;
    }
}
