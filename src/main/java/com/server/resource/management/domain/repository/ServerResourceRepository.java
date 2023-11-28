package com.server.resource.management.domain.repository;

import com.server.resource.management.domain.ServerResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerResourceRepository extends JpaRepository<ServerResource, Long> {
    ServerResource getByName(String name);

    Optional<ServerResource> findById(Long Id);
}
