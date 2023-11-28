package com.server.resource.management.flyway;

import com.server.resource.management.domain.ServerResource;
import com.server.resource.management.domain.repository.ServerResourceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ServerResourceFlywayTest {

    @Autowired
    private ServerResourceRepository serverResourceRepository;

    @DisplayName("Flyway_를_통해_초기화한_데이터에_대한_검증_테스트")
    @Test
    final void defaultDbDataTest() {
        ServerResource server1 = serverResourceRepository.findById(1L)
                .orElse(serverResourceRepository.getByName("서버-1"));
        ServerResource server2 = serverResourceRepository.findById(2L)
                .orElse(serverResourceRepository.getByName("서버-2"));

        assertAll(
                () -> assertThat(server1.remainCpu()).isEqualTo(20),
                () -> assertThat(server1.remainMemory()).isEqualTo(100),
                () -> assertThat(server2.remainCpu()).isEqualTo(30),
                () -> assertThat(server2.remainMemory()).isEqualTo(500)
        );
    }
}
