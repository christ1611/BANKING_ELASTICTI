DROP TABLE IF EXISTS CUST_BASE;
CREATE TABLE CUST_BASE (
ACCT_NO VARCHAR(50) NOT NULL  PRIMARY KEY,
KAKAO_ID VARCHAR(50) NOT NULL,
UUID VARCHAR(50) NOT NULL,
CUST_NM VARCHAR(50) NOT NULL,
STS VARCHAR(1) NOT NULL,
PHONE NUMBER NOT NULL
);

DROP TABLE IF EXISTS CUST_BAL;
CREATE TABLE CUST_BAL (
ACCT_NO VARCHAR(50) NOT NULL  PRIMARY KEY,
CUST_NM VARCHAR(50) NOT NULL,
BAL NUMBER NOT NULL,
LAST_TRSC_DATE DATE
);