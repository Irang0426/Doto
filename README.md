# Doto 📝

Doto는 Spring Boot, JPA, MySQL, Thymeleaf를 기반으로 개발된 간단한 **투두리스트 웹 애플리케이션**입니다.  
할 일을 추가, 수정, 삭제하고 완료 상태를 관리할 수 있습니다.

## 📚 기술 스택

| 기술 | 설명 |
|------|------|
| **Spring Boot** | 자바 기반 웹 프레임워크 |
| **Spring Web** | RESTful 웹 애플리케이션 개발 |
| **Spring Data JPA** | 객체-관계 매핑 (ORM) |
| **MySQL** | 관계형 데이터베이스 |
| **Thymeleaf** | 서버 사이드 템플릿 엔진 |
| **Lombok** | 보일러플레이트 코드 제거 |
| **Spring Boot DevTools** | 개발 편의성 향상 |
| **Validation** | 입력값 유효성 검증 |

## ⚙️ 프로젝트 구조

doto
├── controller # 웹 요청 처리
├── service # 비즈니스 로직
├── repository # 데이터베이스 접근 (JPA)
├── domain # Entity 클래스
├── dto # 요청/응답 데이터 전송 객체
├── templates # Thymeleaf HTML 파일
└── static # CSS, JS 등 정적 자원


## 💾 요구 사항

- Java 17+
- Gradle or Maven
- MySQL 8.x
