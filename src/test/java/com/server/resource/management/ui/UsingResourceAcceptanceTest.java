package com.server.resource.management.ui;

import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import com.server.resource.management.ui.dto.UserResourceListResponseDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.stream.Stream;

import static com.server.resource.management.ui.ResourceRestUtils.사용자별_현황_조회_요청;
import static com.server.resource.management.ui.ResourceRestUtils.사용하는_자원_수정_정상_요청;
import static com.server.resource.management.ui.ResourceRestUtils.자원_사용_정상_요청;
import static com.server.resource.management.ui.ResourceRestUtils.특정_서버_현황_조회_요청;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UsingResourceAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    DatabaseSetUp databaseSetUp;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseSetUp.execute();
    }

    @AfterEach
    public void cleanUp() {
        databaseCleanup.execute();
    }

    /**
     * when 사용자 A가 1번 서버의 리소스를 요청한다.
     * when 사용자 A가 1번 서버의 리소스를 수정 요청한다.
     * when 사용자 A가 2번 서버의 리소스를 요청한다.
     * then 서버 리소스 조회하면 최종 버전으로 조회가 가능하다.
     */
    @DisplayName("리소스를 서버별로 생성 및 수정 요청처리한다.")
    @TestFactory
    Stream<DynamicTest> createAndModifyTest() {
        String userName = "user";
        return Stream.of(
                DynamicTest.dynamicTest("사용자 A가 1번 서버의 리소스를 요청한다.", () -> {
                    자원_사용_정상_요청(new ServerResourceRequestDto(1L, 10L, 1L, userName));
                }),
                DynamicTest.dynamicTest("사용자 A가 1번 서버의 리소스를 수정한다.", () -> {
                    Thread.sleep(300);
                    사용하는_자원_수정_정상_요청(new ServerResourceRequestDto(3L, 15L, 1L, userName));
                }),
                DynamicTest.dynamicTest("사용자 A가 2번 서버의 리소스를 요청한다.", () -> {
                    Thread.sleep(200);
                    자원_사용_정상_요청(new ServerResourceRequestDto(2L, 10L, 2L, userName));
                }),
                DynamicTest.dynamicTest("사용자 A의 사용현황을 조회한다.", () -> {
                    Thread.sleep(500);
                    ExtractableResponse<Response> response = 사용자별_현황_조회_요청(userName);
                    List<UserResourceListResponseDto> result =  response.jsonPath().getList(".", UserResourceListResponseDto.class);
                    UserResourceListResponseDto server1 = result.stream().filter(e -> e.getServerId() == 1).findFirst().get();
                    UserResourceListResponseDto server2 = result.stream().filter(e -> e.getServerId() == 2).findFirst().get();

                    assertThat(result.size()).isEqualTo(2);
                    assertThat(server1.getUserUsedCpu()).isEqualTo(3);
                    assertThat(server1.getUserUsedMemory()).isEqualTo(15);
                    assertThat(server2.getUserUsedCpu()).isEqualTo(2);
                    assertThat(server2.getUserUsedMemory()).isEqualTo(10);
                })
        );
    }
    /**
     *  when 사용자 A가 1번 서버의 리소스를 요청한다.
     *  when 사용자 A가 1번 서버의 리소스를 OverSpec으로 수정 요청한다.
     *  then 서버 리소스 조회하면 처음 요청한 리소스만 존재한다.
     */
    @DisplayName("리소스를 OverSpec으로 수정 요청하면 이전 요청사항만 반영된다.")
    @TestFactory
    Stream<DynamicTest> requestOverSpecTest() throws InterruptedException {
        String userName = "user";
        return Stream.of(
                DynamicTest.dynamicTest("사용자 A가 1번 서버의 리소스를 요청한다.", () -> {
                    자원_사용_정상_요청(new ServerResourceRequestDto(1L, 10L, 1L, userName));
                }),
                DynamicTest.dynamicTest("사용자 A가 1번 서버의 리소스를 OverSpec 으로 요청한다.", () -> {
                    Thread.sleep(300);
                    사용하는_자원_수정_정상_요청(new ServerResourceRequestDto(2L, 3000L, 1L, userName));
                }),
                DynamicTest.dynamicTest("사용자 A의 사용현황을 조회한다.", () -> {
                    Thread.sleep(500);
                    ExtractableResponse<Response> response = 사용자별_현황_조회_요청(userName);
                    List<UserResourceListResponseDto> result =  response.jsonPath().getList(".", UserResourceListResponseDto.class);
                    UserResourceListResponseDto server1 = result.stream().filter(e -> e.getServerId() == 1).findFirst().get();

                    assertThat(result.size()).isEqualTo(1);
                    assertThat(server1.getUserUsedCpu()).isEqualTo(1);
                    assertThat(server1.getUserUsedMemory()).isEqualTo(10);
                })
        );
    }
    /**
     *  when 사용자 A가 1번 서버의 리소스를 요청한다.
     *  when 사용자 B가 1번 서버의 리소스를 OverSpec으로 수정 요청한다.
     *  then 서버 리소스 조회하면 처음 요청한 리소스만 존재한다.
     */
    @DisplayName("리소스를 OverSpec으로 요청 건은 저장이 안된다.")
    @TestFactory
    Stream<DynamicTest> requestOverSpecTest2() throws InterruptedException {
        String userName = "user";
        return Stream.of(
                DynamicTest.dynamicTest("사용자 A가 1번 서버의 리소스를 요청한다.", () -> {
                    자원_사용_정상_요청(new ServerResourceRequestDto(1L, 10L, 1L, userName));
                }),
                DynamicTest.dynamicTest("사용자 B가 1번 서버의 리소스를 OverSpec으로 요청한다.", () -> {
                    Thread.sleep(300);
                    자원_사용_정상_요청(new ServerResourceRequestDto(2L, 91L, 1L, userName + 1));
                }),
                DynamicTest.dynamicTest("서버 1의 사용현황을 조회한다.", () -> {
                    Thread.sleep(500);
                    ExtractableResponse<Response> response = 특정_서버_현황_조회_요청(1L);

                    assertThat(response.body().jsonPath().getLong("usedCpu")).isEqualTo(1L);
                    assertThat(response.body().jsonPath().getLong("usedMemory")).isEqualTo(10L);
                })
        );
    }

}
