package com.server.resource.management.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserResource> usedResources;

    private User(String name) {
        this.name = name;
        usedResources = new ArrayList<>();
    }

    private User(String name, List<UserResource> usedResources) {
        this.name = name;
        this.usedResources = usedResources;
    }

    public static User of(String name) {
        return new User(name);
    }

    public static User from(String name, List<UserResource> usedResources) {
        return new User(name, usedResources);
    }

    public void addUsedResource(UserResource requestResource) {
        this.usedResources.add(requestResource);
    }
}
