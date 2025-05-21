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

## 📁 프로젝트 디렉토리 구조

- `controller` : 웹 요청을 처리하는 컨트롤러 클래스 (예: `TodoController`)
- `service` : 비즈니스 로직을 담당하는 서비스 클래스 (예: `TodoService`)
- `repository` : 데이터베이스 접근을 담당하는 JPA 인터페이스 (예: `TodoRepository`)
- `domain` : 엔티티 클래스가 위치한 디렉토리 (예: `Todo`)
- `dto` : 요청(Request) 및 응답(Response) 데이터 객체를 담는 클래스
- `templates` : Thymeleaf 기반 HTML 뷰 파일들이 위치하는 디렉토리
- `static` : CSS, JS, 이미지 등의 정적 자원 파일들이 위치하는 디렉토리

## 💾 요구 사항

- Java 17+
- Gradle
- MySQL 8.x
