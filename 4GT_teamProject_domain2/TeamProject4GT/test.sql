CREATE TABLE userInfo(
	id varchar(200) primary key,
	pw varchar(30) not null,
	name varchar(30),
	profile varchar(300) default 'userProfile/defaultImage.jpg'
);

CREATE TABLE post(
	pnum int primary key,
	views int default 0,
	plike int default 0,
	category varchar(30),
	title varchar(100),
	content varchar(4000),
	writer varchar(30),
	pdate date default sysdate,
	p_user varchar(200),
	path varchar(1000),
	comCnt int default 0,
	foreign key (p_user) references userInfo(id) on delete cascade
);

CREATE TABLE comments(
	cnum int primary key,
	cment varchar(300),
	cdate date default sysdate,
	cwriter varchar(30),
	replyCnt int default 0,
	clikeCnt int default 0,
	c_user varchar(30),
	c_post int,
	secretNum int constraint test_secretNum_CK check(secretNum = 0 or secretNum = 1),
	foreign key (c_user) references userInfo(id),
	foreign key (c_post) references post(pnum) on delete cascade
);

CREATE TABLE likeInfo(
	l_user varchar(30),
	l_post int,
	ldate date default sysdate,
	foreign key (l_user) references userInfo(id) on delete cascade,
	foreign key (l_post) references post(pnum) on delete cascade
);

CREATE TABLE reply(
	rnum int primary key,
	rment varchar(300),
	rdate date default sysdate,
	rwriter varchar(30),
	rlikeCnt int default 0,
	r_user varchar(30),
	r_post int,
	r_comments int,
	foreign key (r_user) references userInfo(id),
	foreign key (r_post) references post(pnum) on delete cascade,
	foreign key (r_comments) references comments(cnum) on delete cascade
);

UPDATE POST SET comCnt = 0;

/* SELECT ALL */
select * from all_tables;
select * from userInfo;
select * from post;
select * from comments;
select * from likeInfo;
select * from reply;
SELECT NVL(MAX(pnum),0 + 1) FROM post;
/* 테이블 삭제 */
drop table userInfo CASCADE CONSTRAINTS;
drop table post CASCADE CONSTRAINTS;
drop table comments CASCADE CONSTRAINTS;
drop table likeInfo;
drop table reply;

DELETE FROM post WHERE pnum=5;
/* 테스트용 예시 데이터 */
insert into userInfo (id, pw, name) values('admin','123','관리자');
insert into post (pnum, views, plike, category, title, content, writer, p_user, path)
values(1,0,0, '치킨', '푸라닭', '?', '?', 'admin', '??');

insert into comments (cnum, cwriter, cment, c_user, c_post)
values(1, '?', '1111','1111', 1);

insert into likeInfo (l_user,l_post)values ('1111', 1);

SELECT * from post WHERE title like '%1%';
SELECT * FROM post WHERE title LIKE '%1%' ORDER BY pnum DESC;

CREATE TABLE test(
	name varchar(30),
	secretNum int constraint test_secretNum_CK check(secretNum = 0 or secretNum = 1)
);

select * from test;
drop table test;

insert into test (name,secretNum) values ('kim', 0);
insert into test (name,secretNum) values ('lee', 4);

