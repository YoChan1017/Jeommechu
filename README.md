# 퍼블릭 클라우드 DevSecOps 융합 인재 양성 과정 
> Project_01_Jeommechu
## Project Overview
> 프로젝트 명 : Jeommechu (점메추)<br>
> 주제 : 점메추(점심 메뉴 추천)로 인한 음식 메뉴 선정<br>
> 개발 목표 : <br>

<br>
정리 중

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

| 구분       | 내용                           |
| -------- | ---------------------------- |
| Backend  | Java                         |
| DB 연동    | JDBC                         |
| DB 드라이버  | MySQL Connector/J 9.0.0      |
| Web      | Servlet 기반                   |
| Frontend | HTML / CSS                   |
| 배포 구조    | WAR 구조 (WEB-INF, web.xml 존재) |

build/
 └─ classes/
    └─ com/jeommechu/menu/
       ├─ common/
       │  └─ JDBCUtil.class
       ├─ lunch/
       │  ├─ LunchDAO.class
       │  └─ LunchVO.class
       ├─ menulist/
       │  ├─ MenuListDAO.class
       │  └─ MenuListVO.class
       └─ user/
          ├─ MemberDAO.class
          └─ MemberVO.class

역할 요약

VO : 데이터 객체 (DTO)

DAO : DB 접근 및 SQL 처리

JDBCUtil : DB 연결 / 자원 해제 공통 유틸



src/main/java/com/jeommechu/menu/
 ├─ common/
 │  └─ JDBCUtil.java
 ├─ lunch/
 │  ├─ LunchDAO.java
 │  └─ LunchVO.java
 ├─ menulist/
 │  ├─ MenuListDAO.java
 │  └─ MenuListVO.java
 └─ user/
    ├─ MemberDAO.java
    └─ MemberVO.java

패키지별 역할

1) common

JDBCUtil

DB 연결

Connection / PreparedStatement / ResultSet 관리

2) lunch

점심 메뉴 관련 로직

LunchVO : 점심 메뉴 데이터 구조

LunchDAO : 점심 메뉴 조회 / 처리 SQL

3) menulist

전체 메뉴 리스트 관리

MenuListVO : 메뉴 데이터 구조

MenuListDAO : 메뉴 목록 조회 / 관리

4) user

회원 관리

MemberVO : 회원 정보

MemberDAO : 로그인 / 회원 조회 / 회원 관리



webapp/
 ├─ login.html
 ├─ select.html
 ├─ title.html
 ├─ test.html
 ├─ static/
 │  ├─ account.css
 │  ├─ game.css
 │  ├─ main.css
 │  ├─ menulist.css
 │  └─ title.css
 └─ WEB-INF/
    ├─ web.xml
    └─ lib/
       └─ mysql-connector-j-9.0.0.jar

주요 파일 설명

HTML

title.html : 메인 또는 시작 화면

login.html : 로그인 페이지

select.html : 메뉴 선택 화면

test.html : 테스트용 페이지

CSS

화면별 스타일 분리 관리

기능별 CSS 설계는 비교적 깔끔한 편

WEB-INF

web.xml : 서블릿 설정 및 웹 애플리케이션 설정

lib/ : 외부 라이브러리 (MySQL JDBC)
