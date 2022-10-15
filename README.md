##메이플 스토리 프로젝트

### 설정
- [x] mysql 설치 
  - develop 최신 버전
  - database, user생성 및 모든 권한 부여
    - maple_db : maple/maplepass
- [x] mybatis
  - mysql 연결
  - 테스트 작성
- [x] jsp 설정
  - index.jsp 

### 회원가입
- [x] 회원가입 DDL 작성
- [x] 회원가입 로직 작성
  - [x] otp기능 추가
  - [x] 비밀번호 암호화 추가
- [x] 약관동의 추가
  - [x] 약관테이블, 약관동의 히스토리 추가 

### 로그인 구현 
- [x] 로그인 로직 구현
  - [ ] 로그인 인증 방식 추가 (OAuth 2.0 + jwt)
  - [ ] https 적용

### 캐릭터 조회 기능 추가
- [ ] 캐릭터 조회화면 추가
  - [ ] maple.gg 캐릭터 조회 화면 벤치마킹 
  - [ ] 댓글 기능추가
  - [ ] 인증된 회원인지 추가

### 회원 DDL 로직
``` SQL
-- 회원등급 테이블
create table USER_GRADE(
	CODE int primary key COMMENT '등급코드',
	CONTENT VARCHAR(100) not null COMMENT '등급내용'
);

insert into user_grade(code, content) values(0, '운영자');
insert into user_grade(code, content) values(1, '기본사용자');
insert into user_grade(code, content) values(2, '차단된사용자');
commit;

-- 회원정보 테이블
create table USER_INFO(
	ID bigint(20) primary key AUTO_INCREMENT COMMENT '아이디', -- int의 효율이 더좋을것으로 예상
	PASSWORD VARCHAR(255) not null COMMENT '비밀번호',
	NAME VARCHAR(20) not null UNIQUE COMMENT '이름', -- index 
	GRADE_CODE int not null COMMENT '등급코드',
	CREATED_AT DATETIME not null COMMENT '가입일자',
	key idx_user_info_name(NAME), -- 이름에 index를 걸어줌 
	FOREIGN KEY(GRADE_CODE) REFERENCES USER_GRADE(CODE) ON UPDATE cascade on delete cascade
);

-- 약관 테이블
create table TERMS_INFO(
	CODE int primary key auto_increment COMMENT '약관코드',
	TYPE VARCHAR(20) COMMENT '타입',
	NAME VARCHAR(100) COMMENT '약관명칭',
	CONTENT VARCHAR(1000)  COMMENT '약관내용',
	REQUIRED_TERMS VARCHAR(1) COMMENT '필수 약관'
);

insert into TERMS_INFO(TYPE, NAME, CONTENT, REQUIRED_TERMS) VALUES('로그인', '개인정보수집 및 이용동의', '1. 업무처리에 필요한 개인정보입니다.
캐릭터 명, 패스워드
2. 개인정보의 보유기간입니다.
회원 유지 시 계속 보관되며 회원 탈퇴 시 바로 파기됩니다.
3. 제3자 제공 여부입니다.
    3-1 개인정보를 받는 자
    홈페이지 제작 개발자
    3-2 개인정보를 받는 자의 개인정보 이용 목적
    홈페이지 기능 개선
    3-3 제공하는 개인정보 항목
    이용약관에 동의한 회원 정보
    3-4 개인정보를 받는 자의 개인정보 보유 및 이용 기간
    [2]항목과 같습니다.
4. 개인정보 비 동의 시 회원가입이 진행되지 않으며 홈페이지의 서비스 이용이 불가능합니다.
5. 개인정보는 위탁 처리되지 않으며 향후 변경 시 안내해드립니다.', 'Y');

-- 약관 동의 테이블 
create table TERMS_AGREE_INFO(
	IDX int primary key AUTO_INCREMENT COMMENT '일련번호',
	USER_ID bigint(20) COMMENT '유저 아이디(FK)',
	TERMS_CODE int COMMENT '약관 코드(FK)',
	AGREE_YN VARCHAR(1) COMMENT '동의여부',
	CREATED_AT DATETIME COMMENT '변경일자',
	DELETED_AT DATETIME COMMENT '삭제일자',
	FOREIGN KEY(USER_ID) REFERENCES USER_INFO(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TERMS_CODE) REFERENCES TERMS_INFO(CODE) ON UPDATE CASCADE ON DELETE CASCADE 
);

-- OTP 발급 테이블
create table OTP_INFO(
	IDX int primary key AUTO_INCREMENT COMMENT '일련번호',
	USER_NAME VARCHAR(20) COMMENT '유저 아이디',
	OTP_NUMBER VARCHAR(8)  COMMENT 'OTP번호',
	CREATED_AT DATETIME  COMMENT '생성일자',
	key idx_user_info_name(USER_NAME)
);
```