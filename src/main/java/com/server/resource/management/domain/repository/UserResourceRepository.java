package com.server.resource.management.domain.repository;

import com.server.resource.management.domain.UserResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
}
