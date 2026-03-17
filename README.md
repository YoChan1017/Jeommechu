# 🍱 JEOMMECHU (점메추)
### 점심 메뉴 추천 웹 애플리케이션

> 퍼블릭 클라우드 DevSecOps 융합 인재 양성 과정 | Project 01

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Servlet](https://img.shields.io/badge/Servlet-007396?style=flat-square&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache_Tomcat-F8DC75?style=flat-square&logo=apachetomcat&logoColor=black)
![HTML](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)

---

## 📌 프로젝트 개요

**점메추(JEOMMECHU)** 는 사용자가 로그인 후 저장된 음식 데이터를 기반으로 점심 메뉴를 추천받거나 직접 선택할 수 있는 **Java Servlet 기반 웹 애플리케이션**입니다.

단순한 메뉴 조회를 넘어, **사다리 게임** 등 방식의 랜덤 메뉴 추천 기능을 통해 재미 요소를 더했습니다.

---

## 🎯 개발 목적

| 목표 | 내용 |
|------|------|
| 웹 구조 이해 | Java Servlet 기반 MVC 흐름 학습 |
| DB 연동 | JDBC를 활용한 MySQL 데이터베이스 연동 |
| 코드 구조화 | DAO / VO 패턴 적용으로 계층 분리 |
| 배포 실습 | WAR 패키징 및 Apache Tomcat 배포 |

---

## 🛠️ 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Java, Servlet (Jakarta EE) |
| Database | MySQL, JDBC |
| Frontend | HTML5, CSS3, JavaScript (Canvas API) |
| Server | Apache Tomcat (WAR 배포) |
| Pattern | DAO / VO, MVC |

---

## 📂 프로젝트 구조

```
jeommechu/
├── src/main/java/com/jeommechu/
│   ├── menu/
│   │   ├── common/        # JDBCUtil (DB 연결 유틸리티)
│   │   ├── user/          # MemberVO, MemberDAO (회원 관리)
│   │   ├── menulist/      # MenuListVO, MenuListDAO (메뉴 목록)
│   │   └── lunch/         # LunchVO, LunchDAO (오늘의 점심)
│   └── web/
│       ├── account/       # 로그인 / 회원가입 서블릿
│       ├── menulist/      # 메뉴 목록 서블릿
│       └── lunch/         # 점심 선택 / 추천 서블릿
└── src/main/webapp/
    ├── static/            # CSS (account.css, main.css)
    └── LadderGame.html    # 사다리 게임 페이지
```

---

## 🗄️ 데이터베이스 설계

### ERD 요약

```
member ──────────────┐
  id (PK)            │ FK: member_id
  memberID (UNIQUE)  │
  memberPW           │
  memberName         │
  role (USER/ADMIN)  │
                     ▼
                  lunch
                  lunch_id (PK)
                  foodlist_num (FK)──────► foodlist
                  member_id (FK)            Num (PK)
                                            Name
                                            AllKcal / OhKcal
                                            W / P / F / C / S / Na / SF
```

### 테이블 설명

**`member`** — 회원 정보 테이블
- `role` 컬럼으로 USER / ADMIN 권한 구분

**`foodlist`** — 음식 영양 정보 테이블
- 총 칼로리, 탄수화물, 단백질, 지방 등 영양소 데이터 포함

**`lunch`** — 오늘의 점심 선택 기록 테이블
- `foodlist`와 `member`를 연결하는 중간 테이블

---

## ⚙️ 주요 기능

### 1. 회원 인증
- 회원가입 / 로그인 / 로그아웃
- Session 기반 로그인 유지
- ADMIN / USER 역할 구분

### 2. 메뉴 목록 조회
- 전체 음식 목록 및 영양 정보 표시
- 검색 기능으로 메뉴 필터링

### 3. 점심 선택
- 원하는 카테고리를 직접 선택하여 오늘의 점심으로 등록
- 등록된 점심 내역 확인 및 관리

### 4. 🎲 사다리 게임 (랜덤 추천)
- Canvas API로 구현된 애니메이션 사다리 게임
- 서버에서 메뉴 목록을 fetch로 받아 실시간 렌더링
- 사다리 결과에 따라 랜덤으로 점심 메뉴 결정

---

## 🔄 서블릿 처리 흐름

```
브라우저 요청 (HTTP GET/POST)
        │
        ▼
  Servlet (Controller)
  ├── 세션 검증
  ├── 파라미터 파싱
  └── DAO 호출
        │
        ▼
    DAO (Data Access)
    ├── JDBCUtil.getConnection()
    ├── PreparedStatement 실행
    └── VO 객체 반환
        │
        ▼
  Servlet → HTML 응답 (out.println)
  또는 redirect
```

---

## 💡 구현 포인트

- **DAO 패턴**: DB 접근 로직을 서블릿과 분리하여 유지보수성 향상
- **PreparedStatement**: SQL 인젝션 방지 및 파라미터 바인딩
- **Session 관리**: 로그인 상태 유지 및 권한 검증
- **Canvas 사다리 게임**: 순수 JavaScript로 사다리 생성 및 애니메이션 구현, Fetch API를 통해 서버 데이터 비동기 수신
- **애니메이션 UI**: CSS `@keyframes`로 제목 바운스 효과 및 로그인 폼 웨이브 효과 구현

---

## 📸 화면 구성

| 페이지 | 설명 |
|--------|------|
| 로그인 / 회원가입 | 웨이브 애니메이션 배경의 폼 UI |
| 메뉴 목록 | 전체 음식 리스트 + 검색 기능 |
| 점심 선택 | 오늘의 점심 등록 및 확인 |
| 사다리 게임 | Canvas 기반 인터랙티브 랜덤 추천 |

---

## 📝 회고

이 프로젝트를 통해 **Java 웹 애플리케이션의 전체 흐름**을 처음부터 끝까지 직접 구현해보는 경험을 쌓았습니다.

프레임워크 없이 순수 Servlet과 JDBC만으로 개발하면서, Spring이나 JPA 같은 프레임워크가 어떤 문제를 해결해 주는지 체감할 수 있었고, 이를 통해 기반 기술에 대한 이해를 깊이 있게 다질 수 있었습니다.

특히 사다리 게임 기능 구현 시 Canvas API와 Fetch API를 조합하여 서버-클라이언트 간 비동기 통신을 직접 처리해보며, 프론트엔드와 백엔드 연동 구조를 실전으로 익혔습니다.
