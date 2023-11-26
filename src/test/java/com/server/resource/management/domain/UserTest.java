package com.server.resource.management.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

class UserTest {

    private final String testName = "test";
    private List<UserResource> userResources;

    @BeforeEach
    final void setUp() {
        this.userResources = new ArrayList<>();
    }

    @Test
    @DisplayName("유저를 생성할 수 있다.")
    final void createUser() {
        assertThatCode(
                () -> User.from(testName, userResources))
                .doesNotThrowAnyException();
    }
}
