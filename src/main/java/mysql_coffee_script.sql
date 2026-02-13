-- coffee 데이터 베이스 삭제
drop database coffee ;

-- coffee 데이터 베이스 생성
create database coffee ;

-- 데이터 베이스 목록 확인
show databases;

-- coffee 데이터 베이스를 사용하겠습니다.
use coffee ;

-- 테이블 목록을 보여 주세요.
show tables ;

-- auto_increment : 숫자가 1씩 자동으로 커짐, 오라클의 시퀀스와 유사
create table coffee(
	id int auto_increment primary key,
	name varchar(50) not null,
	type varchar(30),
	price decimal(5, 2) not null
);

insert into coffee (name, type, price) values
('Espresso', 'Espresso', 3.50),
('Latte', 'Milk Coffee', 4.50),
('Cappuccino', 'Milk Coffee', 4.00),
('Americano', 'Black Coffee', 3.00);

commit;

select * from coffee;
