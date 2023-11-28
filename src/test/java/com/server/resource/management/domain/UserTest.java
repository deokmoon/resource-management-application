package com.server.resource.management.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class UserTest {

    private final String testName = "test";

    @Test
    @DisplayName("유저를 생성할 수 있다.")
    final void createUser() {
        assertThatCode(
                () -> User.of(testName))
                .doesNotThrowAnyException();
    }
}
