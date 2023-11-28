package com.server.resource.management.domain.repository;

import com.server.resource.management.domain.ServerResource;
import com.server.resource.management.domain.User;
import com.server.resource.management.domain.UserResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserResourceRepository extends JpaRepository<UserResource, Long> {
    Optional<UserResource> findByUserAndServerResource(User user, ServerResource serverResource);
}
