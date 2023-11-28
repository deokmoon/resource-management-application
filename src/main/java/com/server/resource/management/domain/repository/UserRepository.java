package com.server.resource.management.domain.repository;


import com.server.resource.management.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String userName);
}
