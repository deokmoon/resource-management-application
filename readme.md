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
- [Guava](https://github.com/google/guava)
     - 문자 대소문자 변경을 위해 사용하였습니다.

## 빌드 및 실행 방법
```shell
$ git clone https://github.com/deokmoon/resource-management-application.git
$ cd resource-management-application
$ (mac) ./gradlew clean build 
$ (windows) gradlew clean build
$ java -jar build/libs/resource-management-application-0.0.1-SNAPSHOT.jar
```

## 구현사항
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


