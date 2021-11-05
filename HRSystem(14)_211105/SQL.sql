CREATE TABLE member(
	mnum int primary key, 	/*사번*/
	mName varchar(100), 	/*성명*/
	path varchar(4000), 	/*증명사진 경로*/
	startDate date, 		/*입사일*/
	endDate date, 			/*퇴사일*/
	birthDate date, 		/*생년월일*/
	teamName varchar(30),	/*부서*/
	duty varchar(50), 		/*직무*/
	position varchar(30), 	/*직위*/
	work varchar(50), 		/*재직여부*/
	mtype varchar(50),      /*입사구분*/
	mrank int 				/*직급*/
);

CREATE TABLE post(
	pnum int primary key,	/*PK*/
	pmem int,				/*MNUM*/
	wdate date,				/*작성일*/
	category varchar(30),	/*카테고리*/
	content varchar(4000),	/*내용*/
	password varchar(20),	/*암호*/
	result varchar(20) default 'Inactive' /*처리여부*/
); 

CREATE TABLE HRAdmin(
	hnum int primary key,	/*PK*/
	hmem int,				/*MNUM*/
	pw varchar(30),			/*PW*/
	foreign key (hmem) references member(mnum) on delete cascade
);

CREATE TABLE SchoolInfo(
	snum int primary key,	/*PK*/
	stype varchar(20),		/*학력*/
	sname varchar(100),		/*학교명*/
	startDate date,			/*입학일*/
	endDate date,			/*졸업일*/
	field varchar(50),		/*전공계열*/
	major varchar(1000),	/*전공명*/
	finish varchar(1),		/*최종학력여부*/
	sMem int,				/*MNUM*/
	foreign key (sMem) references member(mnum) on delete cascade
);

CREATE TABLE CareerInfo(
	cnum int primary key,	/*PK*/
	startDate date,			/*입사일*/
	endDate date,			/*퇴직일*/
	compName varchar(200),	/*회사명*/
	position varchar(30),	/*직위*/
	rank int,		    /*직급*/
	duty varchar(50),		/*직무*/
	cmem int,				/*MNUM*/
	foreign key (cmem) references member(mnum) on delete cascade
);

CREATE TABLE licenseInfo(	
	lnum int primary key,	/*PK*/
	getDate date,			/*취득일*/
	expireDate date,		/*만료일*/
	lname varchar(50),		/*자격증명*/
	grade varchar(50),		/*등급*/
	lmem int,				/*MNUM*/
	foreign key (lmem) references member(mnum) on delete cascade
);
SELECT * FROM SCHOOLINFO ORDER BY SMEM ASC, STARTDATE ASC;
SELECT * FROM HRADMIN WHERE hmem=1000 AND PW='1000';
/*table*/
select * from all_tables;
SELECT * FROM HRADMIN WHERE hmem='1111' AND PW='1111';
select * from member;
select * from post;
select * from HRAdmin;
select * from SchoolInfo;
select * from CareerInfo;
select * from licenseInfo;
SELECT NVL(MAX(MNUM),0) +1 AS MNUM FROM MEMBER;

SELECT * FROM MEMBER ORDER BY MNUM ASC
SELECT * FROM HRADMIN WHERE ID=1111 AND PW='1111';
SELECT * FROM CAREERINFO WHERE CMEM=1;
delete from member where MNUM=1009;

drop table member;
drop table member CASCADE CONSTRAINTS;
drop table post;
drop table HRAdmin CASCADE CONSTRAINTS;
drop table SchoolInfo;
drop table CareerInfo;
drop table licenseInfo;
SELECT * FROM POST ORDER BY WDATE DESC;
SELECT * FROM POST WHERE (PMEM LIKE '%1001%' OR CATEGORY LIKE '%%' OR CONTENT LIKE '%%') ORDER BY WDATE DESC;

/*레코드*/
insert into post (pnum, pmem, category, content, password) 
values((select nvl(max(pnum),0)+1 from post),1, '업무태도', '업무 태도 불량합니다.','1234');
SELECT NVL(MAX(MNUM),0) +1 AS MNUM FROM MEMBER;

insert into member (mnum, mName, startDate, endDate, birthDate, teamName, duty, position, work,mrank)
values(1000,'루피', '2021-10-01', '9999-12-31', '1988-01-01', 'HR팀', '인사관리', '사원','재직', 2);

insert into hradmin (hnum, hmem, pw) values ((select nvl(max(hnum),0)+1 from hradmin), 1000, '1111');
delete from hradmin where hnum=1;
insert into schoolInfo (snum, stype, sname, startDate, endDate, field, major, finish, smem)
values((select nvl(max(snum),0)+1 from schoolInfo),'고등학교', '서울고등학교', '2015-01-01','2018-01-01', '인문계열','인문', 'Y', 1);

insert into careerInfo (cnum, startDate, endDate, compName, position, rank, duty, cMem)
values((select nvl(max(cnum),0)+1 from careerInfo),'2018-01-01', '2021-01-01', '가나주식회사','사원', '2','사무업무', 1);

SELECT * FROM HRADMIN WHERE hmem=1000 AND PW='1000'
UPDATE HRADMIN SET PW='1000' WHERE HNUM=1000;

