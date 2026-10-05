# 🍱 JEOMMECHU (점메추)

> **Java Servlet 기반 점심 메뉴 추천 웹 애플리케이션**
>
> 퍼블릭 클라우드 DevSecOps 융합 인재 양성 과정 | Project 01

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Servlet](https://img.shields.io/badge/Servlet-007396?style=flat-square&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache_Tomcat-F8DC75?style=flat-square&logo=apachetomcat&logoColor=black)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)

---

## 📌 프로젝트 소개

**JEOMMECHU(점메추)​**는 사용자가 로그인한 후 음식 데이터를 조회하고, 원하는 메뉴를 직접 선택하거나 **사다리 게임을 이용해 점심 메뉴를 랜덤으로 추천받을 수 있는 Java Servlet 기반 웹 애플리케이션**입니다.

Java Servlet과 JDBC를 사용하여 웹 요청 처리부터 데이터베이스 연동, 세션 기반 로그인, 메뉴 추천 기능까지 웹 애플리케이션의 기본적인 동작 구조를 직접 구현했습니다.

### 프로젝트 목표

- Java Servlet 기반 웹 애플리케이션의 요청 처리 흐름 이해
- JDBC를 활용한 MySQL 데이터베이스 연동
- DAO / VO 구조를 통한 데이터 접근 로직 분리
- Session 기반 로그인 및 권한 관리 구현
- WAR 패키징 및 Apache Tomcat 배포 경험
- Canvas API와 Fetch API를 활용한 인터랙티브 기능 구현

---

## 🛠 기술 스택

| 구분 | 기술 |
|---|---|
| Language | Java |
| Backend | Java Servlet (Jakarta EE) |
| Database | MySQL |
| Data Access | JDBC |
| Frontend | HTML5 · CSS3 · JavaScript |
| Web API | Canvas API · Fetch API |
| Server | Apache Tomcat |
| Architecture | MVC |
| Design Pattern | DAO · VO |
| Deployment | WAR |

---

## ✨ 주요 기능

### 1. 🔐 회원 인증

사용자 계정을 관리하고 로그인 상태를 유지할 수 있도록 구현했습니다.

- 회원가입
- 로그인
- 로그아웃
- Session 기반 로그인 상태 유지
- `USER` / `ADMIN` 역할 구분

---

### 2. 🍱 메뉴 목록 조회

저장된 음식 데이터를 조회하여 사용자가 다양한 메뉴와 영양 정보를 확인할 수 있습니다.

- 전체 음식 목록 조회
- 음식 검색
- 음식 영양 정보 표시
- 칼로리 및 주요 영양소 데이터 제공

---

### 3. 🍚 점심 메뉴 선택

사용자가 원하는 음식을 직접 선택하여 오늘의 점심 메뉴로 등록할 수 있습니다.

- 메뉴 카테고리 선택
- 오늘의 점심 등록
- 등록된 점심 내역 조회 및 관리

---

### 4. 🎲 사다리 게임을 이용한 랜덤 추천

점심 메뉴를 직접 고르기 어려운 사용자를 위해 Canvas 기반의 사다리 게임을 구현했습니다.

- JavaScript Canvas API를 활용한 사다리 UI
- 서버에서 메뉴 카테고리 데이터를 Fetch API로 조회
- 서버 데이터 기반 사다리 항목 동적 구성
- 사다리 결과를 이용한 랜덤 메뉴 선택
- 애니메이션 효과 적용

---

## 🏗️ 애플리케이션 구조

```text
Browser
   │
   │ HTTP GET / POST
   ▼
┌─────────────────────┐
│     Servlet         │
│     Controller      │
│                     │
│ - 요청 처리          │
│ - Session 검증       │
│ - Parameter 처리     │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│        DAO          │
│    Data Access      │
│                     │
│ - JDBC Connection   │
│ - SQL 실행           │
│ - 데이터 조회/저장    │
└──────────┬──────────┘
           │
           ▼
       MySQL DB
```

### 데이터 처리 흐름

```text
HTTP Request
     ↓
Servlet
     ↓
Session / Parameter Validation
     ↓
DAO
     ↓
PreparedStatement
     ↓
MySQL
     ↓
VO
     ↓
Servlet
     ↓
HTML Response / Redirect
```

---

## 📂 프로젝트 구조

```text
jeommechu/
├── src/main/java/com/jeommechu/
│   ├── menu/
│   │   ├── common/
│   │   │   └── JDBCUtil.java
│   │   ├── user/
│   │   │   ├── MemberVO.java
│   │   │   └── MemberDAO.java
│   │   ├── menulist/
│   │   │   ├── MenuListVO.java
│   │   │   └── MenuListDAO.java
│   │   └── lunch/
│   │       ├── LunchVO.java
│   │       └── LunchDAO.java
│   │
│   └── web/
│       ├── account/
│       │   └── 로그인 / 회원가입 Servlet
│       ├── menulist/
│       │   └── 메뉴 목록 Servlet
│       └── lunch/
│           └── 점심 선택 / 추천 Servlet
│
└── src/main/webapp/
    ├── static/
    │   └── CSS
    └── LadderGame.html
```

---

## 🗄️ 데이터베이스 설계

프로젝트에서는 회원, 음식 정보, 점심 선택 기록을 중심으로 관계형 데이터베이스를 구성했습니다.

### ERD

```text
┌─────────────────────┐
│       member        │
├─────────────────────┤
│ id (PK)             │
│ memberID (UNIQUE)   │
│ memberPW            │
│ memberName          │
│ role                │
└──────────┬──────────┘
           │
           │ 1:N
           ▼
┌─────────────────────┐
│        lunch        │
├─────────────────────┤
│ lunch_id (PK)       │
│ member_id (FK)      │
│ foodlist_num (FK)   │
└──────────┬──────────┘
           │
           │ N:1
           ▼
┌─────────────────────┐
│      foodlist       │
├─────────────────────┤
│ Num (PK)            │
│ Name                │
│ AllKcal             │
│ OhKcal              │
│ W                   │
│ P                   │
│ F                   │
│ C                   │
│ S                   │
│ Na                  │
│ SF                  │
└─────────────────────┘
```

### 테이블 역할

#### `member`

사용자 계정 및 권한 정보를 저장합니다.

- 회원 ID
- 비밀번호
- 이름
- 사용자 역할
- `USER` / `ADMIN` 권한 구분

#### `foodlist`

음식과 관련된 영양 정보를 저장합니다.

- 음식명
- 총 칼로리
- 탄수화물
- 단백질
- 지방
- 당류
- 나트륨
- 포화지방 등

#### `lunch`

사용자의 점심 선택 기록을 관리합니다.

- 회원 정보와 음식 정보 연결
- 오늘의 점심 선택 기록 저장

---

## 🔧 주요 구현 내용

### 1. DAO 패턴을 이용한 데이터 접근 로직 분리

Servlet에서 직접 데이터베이스 처리 로직을 수행하지 않고, DAO를 별도로 구성하여 데이터 접근 책임을 분리했습니다.

```text
Servlet
   ↓
DAO
   ↓
JDBC
   ↓
MySQL
```

이를 통해 웹 요청 처리와 데이터베이스 접근 로직을 분리하여 코드 구조를 구성했습니다.

---

### 2. PreparedStatement를 이용한 SQL 처리

사용자 입력값을 SQL에 직접 문자열로 조합하지 않고 `PreparedStatement`를 사용하여 데이터베이스 쿼리를 수행했습니다.

```text
사용자 입력
    ↓
PreparedStatement
    ↓
SQL 실행
    ↓
MySQL
```

이를 통해 파라미터 바인딩을 적용한 데이터베이스 처리를 경험했습니다.

---

### 3. Session 기반 인증 및 권한 처리

로그인한 사용자의 상태를 Session으로 관리하고, 사용자 역할에 따라 `USER`와 `ADMIN`을 구분했습니다.

```text
Login
  ↓
Session 생성
  ↓
사용자 정보 저장
  ↓
페이지 접근
  ↓
Session / Role 확인
```

---

### 4. Canvas 기반 사다리 게임

JavaScript의 Canvas API를 사용하여 사다리 게임을 직접 구현했습니다.

서버에서 Fetch API를 통해 음식 카테고리를 전달받고, 전달받은 데이터를 기반으로 화면에 사다리를 동적으로 구성하도록 구현했습니다.

```text
서버
 ↓
카테고리 데이터
 ↓
Fetch API
 ↓
Canvas
 ↓
사다리 생성 / 애니메이션
 ↓
최종 추천 결과
```

---

### 5. CSS 애니메이션

정적인 웹 페이지에 사용자 경험을 더하기 위해 CSS `@keyframes`를 이용한 애니메이션을 적용했습니다.

- 제목 바운스 효과
- 로그인 폼 웨이브 효과
- 페이지 UI 인터랙션 강화

---

## 🚀 배포

애플리케이션을 WAR 형태로 패키징하여 **Apache Tomcat 환경에 배포**하는 과정을 경험했습니다.

```text
Java Application
      ↓
    WAR
      ↓
Apache Tomcat
      ↓
Web Application
```

이를 통해 애플리케이션 개발뿐 아니라 웹 서버 환경에서 배포하는 과정까지 경험했습니다.

---

## 📸 화면 구성

| 화면 | 설명 |
|---|---|
| 로그인 / 회원가입 | Session 기반 사용자 인증 |
| 메뉴 목록 | 음식 목록 및 영양 정보 조회 |
| 점심 선택 | 원하는 메뉴를 점심으로 등록 |
| 사다리 게임 | Canvas 기반 랜덤 메뉴 추천 |

---

## 📚 프로젝트를 통해 배운 점

이 프로젝트를 통해 **Java 웹 애플리케이션의 기본적인 요청 처리 구조를 처음부터 직접 구현**해보았습니다.

특히 Spring과 같은 프레임워크를 사용하지 않고 Java Servlet과 JDBC를 직접 사용하면서 다음과 같은 기반 기술을 이해할 수 있었습니다.

- HTTP 요청과 Servlet 처리 흐름
- Session 기반 인증 및 권한 관리
- JDBC를 이용한 데이터베이스 연동
- DAO / VO를 이용한 계층 분리
- PreparedStatement를 이용한 SQL 처리
- WAR 패키징 및 Tomcat 배포
- Fetch API와 Canvas API를 이용한 클라이언트 기능 구현

이후 Spring과 같은 웹 프레임워크를 학습하면서 **프레임워크가 이러한 반복적인 웹 애플리케이션 기능을 어떻게 추상화하고 관리하는지 이해하는 기반**으로 활용했습니다.

---

## 🔗 Repository

[GitHub - YoChan1017/Jeommechu](https://github.com/YoChan1017/Jeommechu)
