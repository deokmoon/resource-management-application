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

import java.util.Objects;

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
        serverResource.addUsedResource(usedCpu, usedMemory, this);
    }

    public static UserResource from(long usedCpu, long usedMemory, ServerResource serverResource, User user) {
        return new UserResource(usedCpu, usedMemory, serverResource, user);
    }

    public long modifyUsingResource(long modifyCpu, long modifyMemory) {
        long tempCpu = modifyCpu - this.usedCpu;
        long tempMemory = modifyMemory - this.usedMemory;
        serverResource.addUsedResource(tempCpu, tempMemory, this);
        this.usedCpu = modifyCpu;
        this.usedMemory = modifyMemory;
        return this.serverResource.getServerVersion();
    }

    public long getServerId() {
        return this.serverResource.getId();
    }

    public String getServerName() {
        return this.serverResource.getName();
    }

    public long getServerTotalCpu() {
        return this.serverResource.getTotalCpu();
    }

    public long getServerTotalMemory() {
        return this.serverResource.getTotalMemory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResource that = (UserResource) o;
        return Objects.equals(serverResource, that.serverResource) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverResource, user);
    }
}
