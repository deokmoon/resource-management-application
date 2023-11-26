package com.server.resource.management.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ServerResourceTest {

    private final String testName = "testServer";
    private final long totalCpu = 10L;
    private final long totalMemory = 500L;

    @DisplayName("서버리소스 엔티티를 생성한다.")
    @Test
    final void createServerResource() {
        assertThatCode(
                () -> ServerResource.from(testName, totalCpu, totalMemory))
                .doesNotThrowAnyException();
    }

    @DisplayName("서버리소스를 사용할 수 있다.")
    @Test
    final void couldAddUsingResource() {
        // given
        final long requestCpu = 5L;
        final long requestMemory = 100L;
        ServerResource serverResource = ServerResource.from(testName, totalCpu, totalMemory);
        // when
        serverResource.addUsedResource(requestCpu, requestMemory);
        // then
        assertThat(serverResource.remainCpu()).isEqualTo(totalCpu - requestCpu);
        assertThat(serverResource.remainMemory()).isEqualTo(totalMemory - requestMemory);

    }

    @DisplayName("사용한 리소스보다 더 많은 리소스 사용을 요청하면 예외가 발생한다.")
    @Test
    final void makeExceptionWhenResourceRequestMoreThanHavingResource() {
        // given
        final long overCpu = 11L;
        final long overMemory = 501L;
        ServerResource serverResource = ServerResource.from(testName, totalCpu, totalMemory);
        // when then
        assertThatThrownBy(
                () -> serverResource.addUsedResource(overCpu, overMemory))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
