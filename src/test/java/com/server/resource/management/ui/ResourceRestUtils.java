package com.server.resource.management.ui;

import com.server.resource.management.ui.dto.ServerResourceRequestDto;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

class ResourceRestUtils {

    public static ExtractableResponse<Response> 자원_사용_정상_요청(ServerResourceRequestDto serverResourceRequestDto) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(serverResourceRequestDto)
                .when().post("/resource")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 사용하는_자원_수정_정상_요청(ServerResourceRequestDto serverResourceRequestDto) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(serverResourceRequestDto)
                .when().patch("/resource")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 자원_삭제_요청(ServerResourceRequestDto serverResourceRequestDto) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(serverResourceRequestDto)
                .when().delete("/resource")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 사용자별_현황_조회_요청(String userName) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/resource/user?userName={userName}", userName)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 특정_서버_현황_조회_요청(long serverId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/resource/server?serverId={serverId}", serverId)
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 모든_서버_현황_조회_요청() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/resource/all/server")
                .then().log().all()
                .extract();
    }
}
