package com.server.resource.management.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int usedCpu;
    private int usedMemory;

    @ManyToOne
    @JoinColumn(name = "server_resource_id")
    private ServerResource serverResource;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
