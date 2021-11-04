CREATE TABLE member(
	mnum int primary key, 	/*���*/
	mName varchar(100), 	/*����*/
	path varchar(4000), 	/*������� ���*/
	startDate date, 		/*�Ի���*/
	endDate date, 			/*�����*/
	birthDate date, 		/*�������*/
	teamName varchar(30),	/*�μ�*/
	duty varchar(50), 		/*����*/
	position varchar(30), 	/*����*/
	work varchar(50), 		/*��������*/
	mtype varchar(50),      /*�Ի籸��*/
	mrank int 				/*����*/
);

CREATE TABLE post(
	pnum int primary key,	/*PK*/
	pmem int,				/*MNUM*/
	wdate date,				/*�ۼ���*/
	category varchar(30),	/*ī�װ�*/
	content varchar(4000),	/*����*/
	password varchar(20),	/*��ȣ*/
	result varchar(20) default 'Inactive' /*ó������*/
); 

CREATE TABLE HRAdmin(
	hnum int primary key,	/*PK*/
	hmem int,				/*MNUM*/
	pw varchar(30),			/*PW*/
	foreign key (hmem) references member(mnum) on delete cascade
);

CREATE TABLE SchoolInfo(
	snum int primary key,	/*PK*/
	stype varchar(20),		/*�з�*/
	sname varchar(100),		/*�б���*/
	startDate date,			/*������*/
	endDate date,			/*������*/
	field varchar(50),		/*�����迭*/
	major varchar(1000),	/*������*/
	finish varchar(1),		/*�����з¿���*/
	sMem int,				/*MNUM*/
	foreign key (sMem) references member(mnum) on delete cascade
);

CREATE TABLE CareerInfo(
	cnum int primary key,	/*PK*/
	startDate date,			/*�Ի���*/
	endDate date,			/*������*/
	compName varchar(200),	/*ȸ���*/
	position varchar(30),	/*����*/
	rank int,				/*����*/
	duty varchar(50),		/*����*/
	cmem int,				/*MNUM*/
	foreign key (cmem) references member(mnum) on delete cascade
);

CREATE TABLE licenseInfo(	
	lnum int primary key,	/*PK*/
	getDate date,			/*�����*/
	expireDate date,		/*������*/
	lname varchar(50),		/*�ڰ�����*/
	grade varchar(50),		/*���*/
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

/*���ڵ�*/
insert into post (pnum, pmem, category, content, password) 
values((select nvl(max(pnum),0)+1 from post),1, '�����µ�', '���� �µ� �ҷ��մϴ�.','1234');
SELECT NVL(MAX(MNUM),0) +1 AS MNUM FROM MEMBER;

insert into member (mnum, mName, startDate, endDate, birthDate, teamName, duty, position, work,mrank)
values(1000,'����', '2021-10-01', '9999-12-31', '1988-01-01', 'HR��', '�λ����', '���','����', 2);

insert into hradmin (hnum, hmem, pw) values ((select nvl(max(hnum),0)+1 from hradmin), 1000, '1111');
delete from hradmin where hnum=1;
insert into schoolInfo (snum, stype, sname, startDate, endDate, field, major, finish, smem)
values((select nvl(max(snum),0)+1 from schoolInfo),'����б�', '�������б�', '2015-01-01','2018-01-01', '�ι��迭','�ι�', 'Y', 1);

insert into careerInfo (cnum, startDate, endDate, compName, position, rank, duty, cMem)
values((select nvl(max(cnum),0)+1 from careerInfo),'2018-01-01', '2021-01-01', '�����ֽ�ȸ��','���', '2','�繫����', 1);

SELECT * FROM HRADMIN WHERE hmem=1000 AND PW='1000'
UPDATE HRADMIN SET PW='1000' WHERE HNUM=1000;
