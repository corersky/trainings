### Setup

git clone https://github.com/datacharmer/test_db
cd test_db
mysql -u root employees < employees_partitioned.sql

https://dev.mysql.com/doc/employee/en/employees-introduction.html

GRANT CONNECT, RESOURCE, DBA TO EMPLOYEES


REST: http://10.0.0.94:8000/?sdcApplicationId=samplerest
create view dfs.tmp.salaries as select EMP_NO,SALARY,FROM_DATE,TO_DATE from dfs.`/demo/employees/json/SALARIES/*/*.json`

select * from dfs.tmp.salaries


create view dfs.tmp.salaries as select EMP_NO,SALARY,FROM_DATE,TO_DATE from dfs.`/demo/employees/json/SALARIES/*/*.json`
create view dfs.tmp.employees as select EMP_NO, BIRTH_DATE, FIRST_NAME, LAST_NAME, GENDER, HIRE_DATE from dfs.`/demo/employees/json/EMPLOYEES/*/*.json`

create view dfs.tmp.departments as select DEPT_NO, DEPT_NAME from dfs.`/demo/employees/json/DEPARTMENTS/*/*.json`
create view dfs.tmp.dept_emp as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_EMP/*/*.json`
create view dfs.tmp.dept_manager as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_MANAGER/*/*.json`

create view dfs.tmp.titles as select EMP_NO, TITLE, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/TITLES/*/*.json`