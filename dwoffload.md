First work with ingest.md

# Create views

create view dfs.tmp.salaries as select EMP_NO,SALARY,FROM_DATE,TO_DATE from dfs.`/demo/employees/json/SALARIES/*/*.json`
create view dfs.tmp.employees as select EMP_NO, BIRTH_DATE, FIRST_NAME, LAST_NAME, GENDER, HIRE_DATE from dfs.`/demo/employees/json/EMPLOYEES/*/*.json`

create view dfs.tmp.departments as select DEPT_NO, DEPT_NAME from dfs.`/demo/employees/json/DEPARTMENTS/*/*.json`
create view dfs.tmp.dept_emp as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_EMP/*/*.json`
create view dfs.tmp.dept_manager as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_MANAGER/*/*.json`

create view dfs.tmp.titles as select EMP_NO, TITLE, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/TITLES/*/*.json`


ATTENTION:
Streamsets clean:
rm -Rf /mapr/demo.mapr.com/demo/employees/json/
rm -Rf /mapr/demo.mapr.com/demo/employees/parquet/
-> Reset Origin
Streamsets number of imported datasets: 3,919,015

# RazorSQL Demo

- Connect to to Oracle-DB jdbc:oracle:thin:@10.0.0.72:1521:MAPR (employees/employees)
- Show tables:
    - SALARIES
    - TITLES
    - EMPLOYEES

Query ORACLE:

select GENDER, TITLE, round(avg(SALARY), 2) AS AVG_SALARY from SALARIES s
INNER JOIN TITLES t ON t.EMP_NO = s.EMP_NO
INNER JOIN EMPLOYEES e ON s.EMP_NO = e.EMP_NO
group by GENDER, TITLE ORDER BY AVG_SALARY DESC

Query Drill:

select e.GENDER, t.TITLE, round(avg(s.SALARY),2) AS AVG_SALARY from dfs.`/demo/employees/json/SALARIES/*/*.json` s
INNER JOIN dfs.`/demo/employees/json/TITLES/*/*.json` t ON t.EMP_NO = s.EMP_NO
INNER JOIN dfs.`/demo/employees/json/EMPLOYEES/*/*.json` e ON s.EMP_NO = e.EMP_NO
group by e.GENDER, t.TITLE ORDER BY AVG_SALARY DESC

select e.GENDER, t.TITLE, round(avg(s.SALARY),2) AS AVG_SALARY from dfs.demo.`/employees/json/SALARIES/*/*.json` s
INNER JOIN dfs.demo.`/employees/json/TITLES/*/*.json` t ON t.EMP_NO = s.EMP_NO
INNER JOIN dfs.demo.`/employees/json/EMPLOYEES/*/*.json` e ON s.EMP_NO = e.EMP_NO
group by e.GENDER, t.TITLE ORDER BY AVG_SALARY DESC


Query Drill with views:

select GENDER, TITLE, round(avg(SALARY),2) AS AVG_SALARY from dfs.`/tmp/salaries` s
INNER JOIN dfs.`/tmp/titles` t ON t.EMP_NO = s.EMP_NO
INNER JOIN dfs.`/tmp/employees` e ON s.EMP_NO = e.EMP_NO
group by GENDER, TITLE ORDER BY AVG_SALARY DESC

# Create tables with parquet
ATTENTION: create table as from Dril, UI
http://10.0.0.94:8047/query
Create new parquet tables:

CREATE TABLE dfs.demo.`emp_sal_title` as select e.EMP_NO, e.GENDER, t.TITLE, s.SALARY from dfs.demo.`/employees/json/SALARIES/*/*.json` s
INNER JOIN dfs.demo.`/employees/json/TITLES/*/*.json` t ON t.EMP_NO = s.EMP_NO
INNER JOIN dfs.demo.`/employees/json/EMPLOYEES/*/*.json` e ON s.EMP_NO = e.EMP_NO


select * from dfs.demo.`emp_sal_title` LIMIT 20
select count(*) from dfs.demo.`emp_sal_title`
select GENDER, TITLE, round(avg(SALARY),2) AS AVG_SALARY from dfs.demo.`emp_sal_title` group by GENDER, TITLE ORDER BY AVG_SALARY DESC

Go to command line and show files
cd /mapr/demo.mapr.com/demo/emp_sal_title
ll

# Tableau

create view dfs.tmp.salaries as select EMP_NO,SALARY,FROM_DATE,TO_DATE from dfs.`/demo/employees/json/SALARIES/*/*.json`
create view dfs.tmp.employees as select EMP_NO, BIRTH_DATE, FIRST_NAME, LAST_NAME, GENDER, HIRE_DATE from dfs.`/demo/employees/json/EMPLOYEES/*/*.json`
create view dfs.tmp.departments as select DEPT_NO, DEPT_NAME from dfs.`/demo/employees/json/DEPARTMENTS/*/*.json`
create view dfs.tmp.dept_emp as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_EMP/*/*.json`
create view dfs.tmp.dept_manager as select EMP_NO, DEPT_NO, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/DEPT_MANAGER/*/*.json`
create view dfs.tmp.titles as select EMP_NO, TITLE, FROM_DATE, TO_DATE from dfs.`/demo/employees/json/TITLES/*/*.json`

Use SALARIES, EMPLOYES and TITLES join by EMP_NO

Go to sheet
Columns: Title, Gender
Rows AVG(Salary)
Show values from Razor SQL.


# Spark

/opt/mapr/spark/spark-2.1.0/bin/spark-shell

/mapr/demo.mapr.com/demo/employees/json/EMPLOYEES/*/*.json
/mapr/demo.mapr.com/demo/employees/json/SALARIES/*/*.json
/mapr/demo.mapr.com/demo/employees/json/TITLES/*/*.json

val employees = spark.read.json("/demo/employees/json/EMPLOYEES/*/*.json")
employees.show()
val salaries = spark.read.json("/demo/employees/json/SALARIES/*/*.json")
salaries.show()
val titles  = spark.read.json("/demo/employees/json/TITLES/*/*.json")
titles.show()

employees.createOrReplaceTempView("employees")
titles.createOrReplaceTempView("titles")
salaries.createOrReplaceTempView("salaries")

val avgSal = spark.sql("select GENDER, TITLE, round(avg(SALARY),2) AS AVG_SALARY from SALARIES s INNER JOIN TITLES t ON t.EMP_NO = s.EMP_NO INNER JOIN EMPLOYEES e ON s.EMP_NO = e.EMP_NO group by GENDER, TITLE ORDER BY TITLE, GENDER")
avgSal.show()

val emp_title = employees.join(titles, "EMP_NO")
val emp_title_sal = emp_title.join(salaries, "EMP_NO")

emp_title_sal.createOrReplaceTempView("emp_title_sal")
val avgSal = spark.sql("select GENDER, TITLE, round(avg(SALARY),2) AS AVG_SALARY from emp_title_sal group by GENDER, TITLE ORDER BY TITLE, GENDER")
avgSal.show()
val res = emp_title_sal.groupBy("TITLE", "GENDER").agg(avg($"SALARY").as("AVG_SALARY"))
res.show()

store data with spark.
res.write.mode(org.apache.spark.sql.SaveMode.Overwrite).parquet("/demo/spark_avg_salary")