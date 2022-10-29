### RDBMS DDL
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

-- OTP 발급 테이블
create table OTP_INFO(
	IDX int primary key AUTO_INCREMENT COMMENT '일련번호',
	USER_NAME VARCHAR(20) COMMENT '유저 아이디',
	OTP_NUMBER VARCHAR(8)  COMMENT 'OTP번호',
	CREATED_AT DATETIME  COMMENT '생성일자',
	key idx_user_info_name(USER_NAME)
);

-- 캐릭터 정보
create table CHARACTER_INFO(
	ID int primary key AUTO_INCREMENT COMMENT '일련번호', 
	IMAGE varchar(1000) COMMENT '캐릭터이미지', 
	CHARACTER_RANK bigint(20) COMMENT '순위', 
	RANK_MOVE varchar(21) COMMENT '순위변동', 
	NAME varchar(255) COMMENT '캐릭터명', 
	JOB1 varchar(255) COMMENT '직업군', 
	JOB2 varchar(255) COMMENT '직업', 
	CHARACTER_LEVEL int(5) COMMENT '레벨', 
	EXPERIENCE varchar(255) COMMENT '경험치', 
	POPULARITY bigint(20) COMMENT '인기도', 
	GUILD_NAME varchar(255) COMMENT '길드명', 
	CREATED_AT DATETIME  COMMENT '생성일자',
	key idx_character_info_name(NAME)
);

-- 댓글 정보
create table COMMENT_INFO(
	ID int primary key AUTO_INCREMENT COMMENT '일련번호', 
	CHARACTER_ID int COMMENT '캐릭터 일련번호',
	USER_ID bigint(20) COMMENT '로그인 유저 일련번호',
	COMMENT VARCHAR(4000) COMMENT '댓글',
	CREATED_AT DATETIME  COMMENT '생성일자',
	FOREIGN KEY(USER_ID) REFERENCES USER_INFO(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(CHARACTER_ID) REFERENCES CHARACTER_INFO(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	key idx_character_id(CHARACTER_ID)
)

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

-- 댓글 정보
create table COMMENT_INFO(
	ID int primary key AUTO_INCREMENT COMMENT '일련번호', 
	CHARACTER_ID int COMMENT '캐릭터 일련번호',
	USER_ID bigint(20) COMMENT '로그인 유저 일련번호',
	COMMENT VARCHAR(4000) COMMENT '댓글',
	CREATED_AT DATETIME  COMMENT '생성일자',
	FOREIGN KEY(USER_ID) REFERENCES USER_INFO(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(CHARACTER_ID) REFERENCES CHARACTER_INFO(ID) ON UPDATE CASCADE ON DELETE CASCADE,
	key idx_character_id(CHARACTER_ID)
)
```