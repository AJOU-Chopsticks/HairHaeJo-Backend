# HairHaeJo-Backend
#### '헤어해죠~' 플랫폼 백엔드 서버 with Spring Boot

## 기술 스택
<img src="https://github.com/AJOU-Chopsticks/HairHaeJo-Backend/assets/102182032/32571dfc-65b4-4b19-86aa-82e37e1ca475" height="100"/>

* ```Java 11```
* IDE: Intellij
* Framework : Springboot (2.7.10)
* Cloud Server: AWS EC2
* Image Storage: AWS S3
* Database : MySQL (8.0.32)
* CI/CD: Github Action, CodeDeploy

## 패키지 구조
```
src.main.java.Chopsticks.HairHaeJoBackend
  ├─ config
  ├─ controller
  ├─ dto
  ├─ entity
  ├─ jwt
  └─ service
```
* config: 스프링 설정 클래스들로 구성된 디렉토리이다.
* controller: 클라이언트의 요청을 받아 비즈니스 로직을 수행하고 응답을 반환하는 클래스들로 구성된 디렉토리이다.
* dto: 로직 간 데이터 전송을 위한 DTO 클래스들로 구성된 디렉토리이다.
* entity: 데이터베이스 테이블과 매핑되는 클래스와 JpaRepository들로 구성된 디렉토리이다.
* jwt: JWT token 관련 클래스들로 구성된 디렉토리이다.
* service: 비즈니스 로직이 정의된 클래스들로 구성된 디렉토리이다.

## 데이터베이스 ERD

<img src="https://github.com/AJOU-Chopsticks/HairHaeJo-Backend/assets/102182032/2748eaf4-a597-4049-b6da-350d081c09e8">

## RESTful API

* '헤어해죠~' 플랫폼 서비스에 필요한 모든 API들을 Spring Boot을 이용하여 설계하였다.
* 기능 별 API 명세서: [링크](https://ripe-temper-5df.notion.site/API-6eb28a05dba14c05932af501c38467f8)

## 주요 구현 기술

* Spring Security + JWT token
  - 사용자가 로그인에 성공하면 사용자 객체와 권한 정보를 security context에 저장하고 JWT 토큰을 발급한다.
  - Authentication(인증): 사용자의 API 요청 시, HTTP 헤더의 JWT token을 검사함으로써 사용자가 누구인지 판별한다.
  - Authorization(권한): 인증된 사용자가 요청한 API에 대해 접근 권한이 있는지 확인하고 권한이 있다면 비즈니스 로직을 수행하고 응답한다.
  - '헤어헤죠~' 서비스는 client, designer, admin 세 가지 역할로 사용자를 구분하고 각각의 역할에 맞는 권한을 부여하였다.
* Spring WebSocket & STOMP
  - 고객과 디자이너 간 실시간 상담(채팅) 기능을 구현하기 위해 WebSocket과 STOMP를 사용하였다.
  - pub-sub 기반의 메세지 프로토콜인 STOMP를 WebSocket 위에서 동작하도록 구현하여 실시간 메시지 통신이 가능하도록 구현하였다.
  - 이미지 채팅은 먼저 S3에 이미지를 업로드 한 후, 이미지 URL을 텍스트 형식으로 변환하여 전송하는 방식을 사용하였다.
* 외부 API
  - Kakao Pay
    - 시술 예약을 기능을 위해 Kakao Pay API를 연동하여 결제 기능을 구현하였다.
  - FCM(Firebase Cloud Messaging)
    - 새로운 채팅, 새로운 예약이 발생하였을 때, 사용자에게 푸시 알람을 제공하기 위해 FCM을 사용하였다.
