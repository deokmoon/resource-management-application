package com.server.resource.management.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerResource extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(nullable = false)
    private long totalCpu;

    @Column(nullable = false)
    private long totalMemory;

    @ColumnDefault("0")
    private long usedCpu;

    @ColumnDefault("0")
    private long usedMemory;

    @OneToMany(mappedBy = "serverResource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserResource> userResources = new ArrayList<>();

    private ServerResource(String name, long totalCpu, long totalMemory) {
        this.name = name;
        this.totalCpu = totalCpu;
        this.totalMemory = totalMemory;
    }

    public static ServerResource from(String name, long totalCpu, long totalMemory) {
        return new ServerResource(name, totalCpu, totalMemory);
    }


    private void validateUsedResource(long requestCpu, long requestMemory) {
        if (totalCpu - (this.usedCpu + requestCpu) < 0) {
            throw new IllegalArgumentException("할당할 수 있는 CPU 의 양을 초과했습니다. 잔여 CPU: " + remainCpu() + " 요청 CPU: " + requestCpu);
        }
        if (this.totalMemory - (this.usedMemory + requestMemory) < 0) {
            throw new IllegalArgumentException("할당할 수 있는 Memory 의 양을 초과했습니다. 잔여 Memory: " + remainMemory() + " 요청 Memory: " + requestMemory);
        }
    }

    public void addUsedResource(long requestCpu, long requestMemory) {
        validateUsedResource(requestCpu, requestMemory);
        this.usedCpu = this.usedCpu + requestCpu;
        this.usedMemory = this.usedMemory + requestMemory;
    }

    public long remainCpu() {
        return this.totalCpu - this.usedCpu;
    }

    public long remainMemory() {
        return this.totalMemory - this.usedMemory;
    }
}
