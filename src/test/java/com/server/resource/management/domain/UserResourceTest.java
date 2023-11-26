package com.server.resource.management.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserResourceTest {

    private final String testServerName = "testServer";
    private final long totalCpu = 10L;
    private final long totalMemory = 500L;
    private final String testUserName = "testUser";

    private final long usedCpu = 5L;
    private final long usedMemory = 100L;

    private ServerResource serverResource;
    private User user;

    @BeforeEach
    final void setUp() {
        serverResource = ServerResource.from(testServerName, totalCpu, totalMemory);
        user = User.of(testUserName);
    }

    @DisplayName("유저의 서버 리소스 사용정보 도메인을 생성할 수 있다.")
    @Test
    final void createUserResource() {
        assertThatCode(
                () -> UserResource.from(usedCpu, usedMemory, serverResource, user))
                .doesNotThrowAnyException();
    }

    @DisplayName("사용한 리소스보다 더 많은 리소스 사용을 요청하면 예외가 발생한다.")
    @Test
    final void makeExceptionWhenResourceRequestMoreThanHavingResource() {
        // given
        final long overCpu = 11L;
        final long overMemory = 501L;
        // when then
        assertThatThrownBy(
                () -> UserResource.from(overCpu, overMemory, serverResource, user))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[감소]사용하고 있는 리소스를 수정할 수 있다.")
    @Test
    final void modifyUsingResources_decrease() {
        // given
        final long modifyCpu = 3L;
        final long modifyMemory = 200L;
        // when
        UserResource userResource = UserResource.from(usedCpu, usedMemory, serverResource, user);
        userResource.modifyUsingResource(modifyCpu, modifyMemory);
        // then
        assertThat(userResource.getUsedCpu()).isEqualTo(modifyCpu);
        assertThat(userResource.getUsedMemory()).isEqualTo(modifyMemory);
        assertThat(serverResource.remainCpu()).isEqualTo(totalCpu - modifyCpu);
        assertThat(serverResource.remainMemory()).isEqualTo(totalMemory - modifyMemory);
    }

    @DisplayName("[증가]사용하고 있는 리소스를 수정할 수 있다.")
    @Test
    final void modifyUsingResources_increase() {
        // given
        final long modifyCpu = 7L;
        final long modifyMemory = 400L;
        // when
        UserResource userResource = UserResource.from(usedCpu, usedMemory, serverResource, user);
        userResource.modifyUsingResource(modifyCpu, modifyMemory);
        // then
        assertThat(userResource.getUsedCpu()).isEqualTo(modifyCpu);
        assertThat(userResource.getUsedMemory()).isEqualTo(modifyMemory);
        assertThat(serverResource.remainCpu()).isEqualTo(totalCpu - modifyCpu);
        assertThat(serverResource.remainMemory()).isEqualTo(totalMemory - modifyMemory);
    }

    @DisplayName("사용하고 있는 리소스를 총 리소스양으로 수정 요청한 경우 예외가 발생한다.")
    @Test
    final void modifyUsingResources_exception() {
        // given
        final long overCpu = 11L;
        final long overMemory = 501L;
        // when then
        UserResource userResource = UserResource.from(usedCpu, usedMemory, serverResource, user);
        assertThatThrownBy(
                () -> userResource.modifyUsingResource(overCpu, overMemory))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
