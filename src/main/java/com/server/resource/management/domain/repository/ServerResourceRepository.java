package com.server.resource.management.domain.repository;

import com.server.resource.management.domain.ServerResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerResourceRepository extends JpaRepository<ServerResource, Long> {
}
