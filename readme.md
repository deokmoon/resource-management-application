# 서버 리소스 유저별 등록 수정 API

<img src="https://img.shields.io/badge/Java 17-6DB33F?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/SpringBoot 3.1.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
<img src="https://img.shields.io/badge/H2-1E8CBE?style=for-the-badge&logo=h2&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

## 개요
- 사용자가 사용할 수 있는 리소스 등록/수정/삭제/조회 Rest API 서버 개발


## 사용한 외부 라이브러리
- [Flyway](https://flywaydb.org/)
  - 주어진 서버 데이터를 InMemory DB에 적재 및 스키마 관리를 위해 사용했습니다.

## 빌드 및 실행 방법
```shell
$ git clone https://github.com/deokmoon/resource-management-application.git
$ cd resource-management-application
$ (mac) ./gradlew clean build 
$ (windows) gradlew clean build
$ java -jar build/libs/resource-management-application-0.0.1-SNAPSHOT.jar
```

## 요구사항
- 사용자 리소스 조회 API
  - 사용자가 등록한 사용자 리소스를 조회할 수 있습니다.
- 사용자 리소스 등록 API
  - 사용자 리소스를 등록할 수 있습니다
  - 사용자가 서버별로 사용하고자 하는 서버의 CPU, 메모리 사용량을 등록할 수 있습니다
- 사용자 리소스 수정 API
  - 현재 사용자가 이용하는 리소스를 수정 할 수 있습니다.
- 사용자 리소스 삭제 API
  - 현재 사용자가 이용하는 리소스를 삭제 할 수 있습니다.
- 서버 리소스 조회 API
  - 현재 서버별로 사용할 수 있는 리소스를 조회합니다.
  - 사용자에게 할당 된 사용량을 제외하고 남은 리소스를 조회합니다.

## 구현사항
- 관련 http request 테스트는 아래 경로의 파일로 인텔리제이에서 테스트 가능합니다.
- [http request 테스트 폴더](./http)
### 리소스 생성 요청
- 서버id와 사용자이름으로 할당 요청을 합니다.
- 해당 사용자이름이 없는 경우 User를 생성합니다.
- 비동기 방식으로 처리하여 no-content 204 응답코드를 받습니다.
- 리소스 생성 요청에 대한 동시성 문제를 해결하기 위해 엔티티의 version 정보를 활용했습니다.
- request
~~~ http request
POST http://localhost:8080/resource
Content-Type: application/json

{
  "cpu": 1,
  "memory": 1,
  "userName": "testName",
  "serverId": 1
}
~~~
### 리소스 삭제
- 사용자명과 서버id에 해당하는 리소스를 삭제합니다
- userResource와 serverResource 의 연관관계를 고아객체로 두어 한쪽이 삭제되면 같이 삭제되도록 적용했습니다.
- 비동기 방식으로 적용했습니다.
- request
~~~ http request
DELETE http://localhost:8080/resource
Content-Type: application/json

{
  "userName": "testName",
  "serverId": 1
}
~~~

### 리소스 수정
- PATCH 메서드로 수정을 합니다.
- 비동기 메서드로 처리합니다.
- serverId와 userName을 key로 수정합니다.
- request
~~~ http request
PATCH http://localhost:8080/resource
Content-Type: application/json

{
  "cpu": 2,
  "memory": 5,
  "userName": "testName",
  "serverId": 1
}
~~~

### 사용자별 리소스 조회
- GET 메서드를 활용합니다.
- userName에 해당하는 User가 사용하고 있는 ServerResource를 반환합니다.
- request
~~~http request
GET http://localhost:8080/resource/user?userName=testName
Content-Type: application/json
~~~
- response
~~~http request
{
    "userName": testUser,
    "serverId": 1,
    "serverName": "서버-1",
    "serverTotalCpu": 10,
    "serverTotalMemory": 100,
    "userUsedCpu": 1,
    "userUsedMemory": 1
}
~~~

### 서버별 리소스 조회
- resource/server?serverId={serverId} 로 특정 서버의 자원 현황을 조회할 수 있습니다.
- resource/all/server 요청으로 모든 서버의 자원 현황을 조회할 수 있습니다. 
- request
~~~http request
GET http://localhost:8080/resource/server?serverId=1
Content-Type: application/json

GET http://localhost:8080/resource/all/server
Content-Type: application/json
~~~
- response
~~~ http request
[
  {
    "id": 1,
    "name": "서버-1",
    "totalCpu": 20,
    "totalMemory": 100,
    "usedCpu": 1,
    "usedMemory": 1
  },
  {
    "id": 2,
    "name": "서버-2",
    "totalCpu": 30,
    "totalMemory": 500,
    "usedCpu": 0,
    "usedMemory": 0
  }
]
~~~
### 공통
- Flyway 로 기본 DB 스키마 관리 및 default 데이터를 처리 하였습니다.
- 도메인 테스트코드로 도메인의 기능을 검증하였습니다.