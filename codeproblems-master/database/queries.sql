
String
1
SELECT person_id, name
FROM person NATURAL JOIN position NATURAL JOIN works
WHERE comp_id = ? AND (end_date > CURRENT_DATE OR end_date IS NULL)
~
String
2
SELECT name, pay_rate
FROM person NATURAL JOIN works NATURAL JOIN position
WHERE comp_id = ? AND (end_date > CURRENT_DATE OR end_date IS NULL) AND pay_type = 'salary'
ORDER BY pay_rate DESC
~

3
SELECT comp_id, comp_name, SUM(pay_rate) AS total_pay
FROM company NATURAL JOIN position NATURAL JOIN works
WHERE pay_type = 'salary'
GROUP BY comp_id, comp_name
ORDER BY total_pay DESC
~
int
4
SELECT pos_code, pos_name
FROM position NATURAL JOIN works
WHERE person_id = ?
~
int
5
SELECT ks_code, title
FROM knowledge_skill NATURAL JOIN has_skill
WHERE person_id = ?
~
int int
6
SELECT ks_code, title
FROM knowledge_skill NATURAL JOIN requires NATURAL JOIN works
WHERE person_id = ?
MINUS
SELECT ks_code, title
FROM knowledge_skill NATURAL JOIN has_skill
WHERE person_id = ?
~
String int
6b
SELECT ks_code, title
FROM knowledge_skill NATURAL JOIN requires
WHERE pos_code = ?
MINUS
SELECT ks_code, title
FROM knowledge_skill NATURAL JOIN has_skill
WHERE person_id = ?
~
String
7a
SELECT ks_code, title
FROM requires NATURAL JOIN knowledge_skill
WHERE pos_code = ?
~
String
7b
SELECT ks_code, title
FROM skill_cate NATURAL JOIN knowledge_skill
WHERE nwcet_id = ?
~
String int
8
SELECT ks_code
FROM requires
WHERE pos_code = ?
MINUS
SELECT ks_code
FROM has_skill
WHERE person_id = ?
~
String int
9
WITH skill_gap (ks_code) AS(	
	(SELECT ks_code
	FROM requires
	WHERE pos_code = ?)
	MINUS 
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?))
SELECT DISTINCT T.c_code, title
FROM course T
WHERE NOT EXISTS((
	SELECT ks_code
	FROM skill_gap)
	MINUS
	(SELECT ks_code
	FROM teaches TS
	WHERE T.c_code = TS.c_code))
~
String int
10
WITH skill_gap (ks_code) AS(	
	(SELECT ks_code
	FROM requires
	WHERE pos_code = ?)
	MINUS
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?)),
useful_course (c_code, title) AS(
	SELECT DISTINCT c_code, title
	FROM course T
	WHERE NOT EXISTS (
	(SELECT ks_code
	FROM skill_gap)
	MINUS
	(SELECT ks_code
	FROM teaches TS
	WHERE T.c_code = TS.c_code))),
useful_section (c_code, section_id, title, completion_date) AS
	(SELECT section_id, c_code, title, completion_date
	FROM (useful_course) JOIN section USING (c_code)),
min_completion_date (min_date) AS
	(SELECT MIN(completion_date)
	FROM useful_section)
SELECT c_code, section_id, title, completion_date
FROM useful_section, min_completion_date
WHERE useful_section.completion_date = min_completion_date.min_date
~
String int
11
WITH skill_gap (ks_code) AS(	
	(SELECT ks_code
	FROM requires
	WHERE pos_code = ?)
	MINUS
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?)),
useful_course (c_code, title) AS(
	SELECT DISTINCT c_code, title
	FROM course T
	WHERE NOT EXISTS (
	(SELECT ks_code
	FROM skill_gap)
	MINUS
	(SELECT ks_code
	FROM teaches TS
	WHERE T.c_code = TS.c_code))),
useful_section (c_code, section_id, title, price) AS
	(SELECT section_id, c_code, title, price
	FROM useful_course JOIN section USING (c_code)),
min_price (minprice) AS
	(SELECT MIN(price)
	FROM useful_section)
SELECT useful_section.c_code, useful_section.section_id, useful_section.title, useful_section.price
FROM useful_section, min_price
WHERE useful_section.price = min_price.minprice
~

12p
DROP SEQUENCE CourseSet_seq;
CREATE SEQUENCE CourseSet_seq
	START WITH 1
	INCREMENT BY 1
	MAXVALUE 999999
	NOCYCLE;

DROP TABLE CourseSet;
CREATE TABLE CourseSet(
	csetID NUMERIC(8,0) PRIMARY KEY,
	c_code1 VARCHAR(10),
	c_code2 VARCHAR(10),
	c_code3 VARCHAR(10),
	csetsize NUMERIC(2,0));
INSERT INTO CourseSet
	SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, null, 2
	FROM course C1, course C2
	WHERE C1.c_code < C2.c_code;
INSERT INTO CourseSet
	SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, C3.c_code, 3
	FROM course C1, course C2, course C3
	WHERE C1.c_code < C2.c_code AND C2.c_code < C3.c_code;
DROP TABLE CourseSet_Skill;
CREATE TABLE CourseSet_Skill(
	csetID NUMERIC(8,0),
	ks_code NUMERIC(4,0),
	PRIMARY KEY (cSetID, ks_code))

INSERT INTO CourseSet_Skill(csetID, ks_code)
	(SELECT csetID, ks_code
	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code1 = CS.c_code)
	UNION
	(SELECT csetID, ks_code
	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code2 = CS.c_code)
	UNION
	(SELECT csetID, ks_code
	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code3 = CS.c_code)
~	
String int
12
WITH skill_gap (ks_code) AS(	
	(SELECT ks_code
	FROM requires
	WHERE pos_code = ?)
	MINUS 
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?)),
Cover_Cset(csetID, csize) AS (
	SELECT csetID, csetsize
	FROM CourseSet cSet
	WHERE NOT EXISTS(
	(SELECT ks_code
	FROM skill_gap)
	MINUS
	(SELECT ks_code
	FROM CourseSet_Skill CSSk
	WHERE CSSk.csetID = Cset.csetID)))
SELECT c_code1, c_code2, c_code3
FROM Cover_CSet NATURAL JOIN CourseSet
WHERE csetsize = (SELECT MIN(csize) FROM Cover_CSet)
~
int
13
WITH subtable (nwcet_id) AS(
	(SELECT nwcet_id
	FROM knowledge_skill NATURAL JOIN (
		SELECT * 
		FROM has_skill
		WHERE person_id = ?)))
SELECT cate_code
FROM job_category J 
WHERE NOT EXISTS(
	(SELECT nwcet_id
	FROM subtable)
	MINUS
	(SELECT nwcet_id
	FROM core_skill C
	WHERE J.cate_code = C.cate_code))
~
int
14
WITH positions_qualified (pos_code, pos_name, comp_id, emp_mode, standard_pay) AS (	
	SELECT pos_code, pos_name, comp_id, emp_mode, standard_pay
	FROM position P
	WHERE NOT EXISTS (
	(SELECT ks_code
	FROM requires R
	WHERE R.pos_code = P.pos_code)
	MINUS
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?))),
max_pay(value) AS (
	SELECT MAX(standard_pay)
	FROM positions_qualified
)
SELECT positions_qualified.pos_code, pos_name, comp_id, emp_mode, standard_pay
FROM max_pay, positions_qualified 
WHERE standard_pay = max_pay.value
~
int
14b
SELECT pos_code, pos_name, comp_name, emp_mode, standard_pay 
FROM position P NATURAL JOIN company
WHERE NOT EXISTS (
	(SELECT ks_code
	FROM requires R
	WHERE R.pos_code = P.pos_code)
	MINUS
	(SELECT ks_code
	FROM has_skill
	WHERE person_id = ?))
~
String
15
SELECT person_id, name, email
FROM person S
WHERE NOT EXISTS (
	(SELECT ks_code
	FROM requires 
	WHERE pos_code = ?)
	MINUS
	(SELECT ks_code
	FROM has_skill T
	WHERE S.person_id = T.person_id))
~
String String
16
WITH number_of_skills_required (num_skills_count) AS (
	SELECT COUNT (ks_code)
	FROM requires
	WHERE pos_code = ?),
people_skill (person_id, count_skills) AS (
	SELECT person_id, COUNT (ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id)
SELECT person_id
FROM people_skill P, number_of_skills_required N
WHERE P.count_skills = N.num_skills_count - 1
~
String String String
17
WITH PersonRequiredSkillCnt(person_id, skillCnt) AS(
	SELECT person_id, COUNT(ks_code)
	FROM has_skill NATURAL JOIN requires
	WHERE pos_code = ?
	GROUP BY person_id),
requiredSkillCnt(cnt) AS (
	SELECT COUNT(ks_code) 
	FROM requires
	WHERE pos_code = ?),
missing_one (person_id) AS(
	SELECT person_id
	FROM PersonRequiredSkillCnt, requiredSkillCnt
	WHERE skillCnt = cnt - 1),
skills_req (ks_code) AS (
	SELECT ks_code
	FROM requires
	WHERE pos_code = ?),
poss_people(person_id) AS (
	SELECT person_id
	FROM person NATURAL JOIN missing_one),
people_missing_skill (ks_code, person_id) AS (
	(SELECT ks_code, person_id
	FROM skills_req S, poss_people P)
	MINUS
	(SELECT ks_code, person_id
	FROM has_skill))
SELECT ks_code, COUNT(DISTINCT person_id) AS num_people
FROM people_missing_skill
GROUP BY ks_code
ORDER BY num_people ASC
~
String
18
WITH skill_count (person_id, num_skills) AS
	(SELECT person_id, COUNT(ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id),
least_missing (value) AS 
	(SELECT MAX(num_skills)
	FROM skill_count)
SELECT person_id, value
FROM skill_count, least_missing
WHERE num_skills = least_missing.value
~
String
18b
WITH skill_count (person_id, num_skills) AS
	(SELECT person_id, COUNT(ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id),
least_missing (value) AS 
	(SELECT MAX(num_skills)
	FROM skill_count)
SELECT person_id, name, email, value
FROM (skill_count NATURAL JOIN person), least_missing
WHERE num_skills = least_missing.value
~
String String int
19
WITH skill_count (person_id, num_skills) AS
	(SELECT person_id, COUNT(ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id),
skills_req(num_req) AS
	(SELECT COUNT(ks_code)
	FROM requires
	WHERE pos_code = ?)
SELECT person_id, skills_req.num_req - num_skills AS missing_k
FROM skill_count, skills_req
WHERE  skills_req.num_req - num_skills <= ?
ORDER BY missing_k ASC
~
String String int
19b
WITH skill_count (person_id, num_skills) AS
	(SELECT person_id, COUNT(ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id),
skills_req(num_req) AS
	(SELECT COUNT(ks_code)
	FROM requires
	WHERE pos_code = ?)
SELECT person_id, name, email, skills_req.num_req - num_skills AS missing_k
FROM (skill_count NATURAL JOIN person), skills_req 
WHERE  skills_req.num_req - num_skills <= ?
ORDER BY missing_k ASC
~
String String String
20
WITH skill_count (person_id, num_skills) AS
	(SELECT person_id, COUNT(ks_code)
	FROM requires NATURAL JOIN has_skill
	WHERE pos_code = ?
	GROUP BY person_id),
num_skills_req(num_req) AS
	(SELECT COUNT(ks_code)
	FROM requires
	WHERE pos_code = ?),
missing_k_list(person_id, number_missing) AS
	(SELECT person_id, num_req - num_skills 
	FROM skill_count, num_skills_req
	WHERE num_req - num_skills <= 3),
skills_req (ks_code) AS (
	SELECT ks_code
	FROM requires
	WHERE pos_code = ?),
poss_people(person_id) AS (
	SELECT person_id
	FROM person NATURAL JOIN missing_k_list),
people_missing_skill (ks_code, person_id) AS (
	(SELECT ks_code, person_id
	FROM skills_req S, poss_people P)
	MINUS
	(SELECT ks_code, person_id
	FROM has_skill))
SELECT ks_code, COUNT(person_id) AS num_people
FROM people_missing_skill
GROUP BY ks_code
ORDER BY num_people DESC;
~
String
21
SELECT person_id, name, EXTRACT(year FROM start_date)\"Start Year\", EXTRACT(year FROM end_date) \"End year\"
FROM person NATURAL JOIN works NATURAL JOIN position
WHERE cate_code = ?
~
String
22
(SELECT DISTINCT person_id
FROM person NATURAL JOIN works
WHERE pos_code = ?)
MINUS
(SELECT person_id
FROM works
WHERE end_date > CURRENT_DATE OR end_date IS NULL)
~

23a
WITH comp_emp(company_id, end_date2, num_employees) AS
	(SELECT comp_id, end_date, COUNT(DISTINCT person_id)
	FROM position NATURAL JOIN works
	GROUP BY comp_id, end_date
	HAVING end_date > CURRENT_DATE OR end_date IS NULL),
max_emp(value) AS
	(SELECT MAX(num_employees)
	FROM comp_emp)
SELECT company_id
FROM comp_emp, max_emp
WHERE comp_emp.num_employees = max_emp.value
~

23b
WITH comp_pay(company_id, end_date2, pay) AS
	(SELECT comp_id, end_date, SUM(pay_rate)
	FROM position NATURAL JOIN works
	GROUP BY comp_id, end_date
	HAVING end_date > CURRENT_DATE OR end_date IS NULL),
max_pay(value) AS
	(SELECT MAX(pay)
	FROM comp_pay)
SELECT company_id
FROM comp_pay, max_pay
WHERE comp_pay.pay = max_pay.value
~

24a
WITH comp_emp(company_id, end_date2, num_employees) AS
	(SELECT comp_id, end_date, COUNT(DISTINCT person_id)
	FROM position NATURAL JOIN works
	GROUP BY comp_id, end_date
	HAVING end_date > CURRENT_DATE OR end_date IS NULL),
sector_emp(gics_id, sec_num_employees) AS
	(SELECT gics_id, SUM(num_employees)
	FROM comp_emp NATURAL JOIN industry
	GROUP BY gics_id),
max_employees (value) AS
	(SELECT MAX(sec_num_employees)
	FROM sector_emp)
SELECT gics_id
FROM sector_emp, max_employees
WHERE sector_emp.sec_num_employees = max_employees.value
~

24b
WITH comp_pay(company_id, end_date2, pay) AS
	(SELECT comp_id, end_date, SUM(pay_rate)
	FROM position NATURAL JOIN works
	GROUP BY comp_id, end_date
	HAVING end_date > CURRENT_DATE OR end_date IS NULL),
sector_pay(gics_id, sec_pay) AS
	(SELECT gics_id, SUM(pay)
	FROM comp_pay NATURAL JOIN industry
	GROUP BY gics_id),
max_pay(value) AS
	(SELECT MAX(sec_pay)
	FROM sector_pay)
SELECT gics_id
FROM sector_pay, max_pay
WHERE sector_pay.sec_pay = max_pay.value
~

25a
WITH old_salary(person_id, old_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works
	WHERE end_date < CURRENT_DATE
	),
current_salary(person_id, new_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works
	WHERE end_date > CURRENT_DATE OR end_date IS NULL
	)
SELECT DISTINCT COUNT(person_id)
FROM old_salary O NATURAL JOIN current_salary N
WHERE N.new_salary_val > O.old_salary_val
~

25b
WITH old_salary(person_id, old_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works
	WHERE end_date < CURRENT_DATE
	),
current_salary(person_id, new_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works
	WHERE end_date > CURRENT_DATE OR end_date IS NULL
	)
SELECT DISTINCT COUNT(person_id)
FROM old_salary O NATURAL JOIN current_salary N
WHERE N.new_salary_val < O.old_salary_val
~

25c
WITH old_salary(person_id, old_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works
	WHERE end_date < CURRENT_DATE
	),
current_salary(person_id, new_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works 
	WHERE end_date > CURRENT_DATE OR end_date IS NULL
	),
number_increase (total_increase) AS 
	(SELECT DISTINCT COUNT(person_id)
	FROM old_salary O NATURAL JOIN current_salary N
	WHERE N.new_salary_val > O.old_salary_val),
number_decrease (total_decrease) AS 
	(SELECT DISTINCT COUNT(person_id)
	FROM old_salary O NATURAL JOIN current_salary N
	WHERE N.new_salary_val < O.old_salary_val)
SELECT total_increase, total_decrease , total_increase / total_decrease AS ratio
FROM number_increase, number_decrease
~

25d
WITH old_salary(person_id, old_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works NATURAL JOIN position NATURAL JOIN company
	WHERE end_date < CURRENT_DATE AND primary_sector = 'Software'
	),
current_salary(person_id, new_salary_val) AS
	(SELECT person_id, pay_rate
	FROM works works NATURAL JOIN position NATURAL JOIN company
	WHERE end_date > CURRENT_DATE OR end_date IS NULL AND primary_sector = 'Software'
	),
earning_change (person_id, earning_change) AS
	(SELECT person_id, (current_salary.new_salary_val- old_salary.old_salary_val) AS earning_change 
	FROM old_salary NATURAL JOIN current_salary
	)
SELECT AVG(earning_change.earning_change) AS avg_earning_change_rate
FROM earning_change
~

26
WITH cate_pos (cate_code, num_pos) AS (
	SELECT cate_code, COUNT(DISTINCT pos_code)
	FROM position 
	GROUP BY cate_code),
open_pos (cate_code, num_pos_open) AS (
	SELECT cate_code, COUNT(DISTINCT pos_code)
	FROM position NATURAL LEFT OUTER JOIN works
	WHERE (end_date > CURRENT_DATE OR end_date IS NULL) OR (person_id IS NULL)
	GROUP BY cate_code),
pos_skills_req (pos_code, cate_code, num_skills) AS (
	SELECT pos_code, cate_code, COUNT(ks_code)
	FROM requires NATURAL JOIN position
	GROUP BY pos_code, cate_code),
most_skills_cate (cate_code, max_skills) AS (
	SELECT cate_code, MAX(num_skills)
	FROM pos_skills_req
	GROUP BY cate_code),
cate_reps (pos_code, cate_code, num_skill) AS(
	SELECT P.pos_code, P.cate_code, num_skills
	FROM pos_skills_req P, most_skills_cate M
	WHERE P.num_skills = M.max_skills AND P.cate_code = M.cate_code),
number_of_skills_required (pos_code, num_skills_count) AS (
	SELECT pos_code, COUNT(ks_code)
	FROM requires
	GROUP BY pos_code),
jobless (person_id) AS (
	SELECT person_id
	FROM person
	MINUS
	SELECT person_id
	FROM works
	WHERE end_date > CURRENT_DATE OR end_date IS NULL),
people_skill (person_id, pos_code, count_skills) AS (
	SELECT person_id, pos_code ,COUNT (ks_code)
	FROM requires NATURAL JOIN has_skill NATURAL JOIN jobless
	GROUP BY person_id, pos_code),
num_qualified_position(pos_code, num_people) AS(
	SELECT pos_code, COUNT(person_id)
	FROM people_skill P JOIN number_of_skills_required N USING (pos_code)
	WHERE P.count_skills = N.num_skills_count 
	GROUP BY pos_code),
num_qualified_cate(cate_code, num_people_cate) AS(
	SELECT cate_code, num_people
	FROM num_qualified_position NATURAL JOIN cate_reps),
fillable_vacancies (cate_code, num_qual_people) AS(
	SELECT cate_code, num_pos_open - num_people_cate
	FROM num_qualified_cate NATURAL JOIN open_pos),
max_vacancies (value) AS(
	SELECT MAX(num_qual_people)
	FROM fillable_vacancies)
SELECT cate_code, num_qual_people
FROM fillable_vacancies, max_vacancies
WHERE fillable_vacancies.num_qual_people = max_vacancies.value
~

27
WITH cate_pos (cate_code, num_pos) AS (
	SELECT cate_code, COUNT(DISTINCT pos_code)
	FROM position 
	GROUP BY cate_code),
open_pos (cate_code, num_pos_open) AS (
	SELECT cate_code, COUNT(DISTINCT pos_code)
	FROM position NATURAL LEFT OUTER JOIN works
	WHERE (end_date > CURRENT_DATE OR end_date IS NULL) OR (person_id IS NULL)
	GROUP BY cate_code),
pos_skills_req (pos_code, cate_code, num_skills) AS (
	SELECT pos_code, cate_code, COUNT(ks_code)
	FROM requires NATURAL JOIN position
	GROUP BY pos_code, cate_code),
most_skills_cate (cate_code, max_skills) AS (
	SELECT cate_code, MAX(num_skills)
	FROM pos_skills_req
	GROUP BY cate_code),
cate_reps (pos_code, cate_code, num_skill) AS(
	SELECT P.pos_code, P.cate_code, num_skills
	FROM pos_skills_req P, most_skills_cate M
	WHERE P.num_skills = M.max_skills AND P.cate_code = M.cate_code),
number_of_skills_required (pos_code, num_skills_count) AS (
	SELECT pos_code, COUNT(ks_code)
	FROM requires
	GROUP BY pos_code),
jobless (person_id) AS (
	SELECT person_id
	FROM person
	MINUS
	SELECT person_id
	FROM works
	WHERE end_date > CURRENT_DATE OR end_date IS NULL),
people_skill (person_id, pos_code, count_skills) AS (
	SELECT person_id, pos_code ,COUNT (ks_code)
	FROM requires NATURAL JOIN has_skill NATURAL JOIN jobless
	GROUP BY person_id, pos_code),
num_qualified_position(pos_code, num_people) AS(
	SELECT pos_code, COUNT(person_id)
	FROM people_skill P JOIN number_of_skills_required N USING (pos_code)
	WHERE P.count_skills = N.num_skills_count 
	GROUP BY pos_code),
num_qualified_cate(cate_code, num_people_cate) AS(
	SELECT cate_code, num_people
	FROM num_qualified_position NATURAL JOIN cate_reps),
fillable_vacancies (cate_code, num_qual_people) AS(
	SELECT cate_code, num_pos_open - num_people_cate
	FROM num_qualified_cate NATURAL JOIN open_pos),
max_vacancies (value) AS(
	SELECT MAX(num_qual_people)
	FROM fillable_vacancies),
desired_cate (cate_code) AS(
	SELECT cate_code
	FROM fillable_vacancies, max_vacancies
	WHERE fillable_vacancies.num_qual_people = max_vacancies.value),
desired_pos (pos_code) AS(
	SELECT pos_code
	FROM desired_cate NATURAL JOIN cate_reps),
req_skills (ks_code) AS(	
	SELECT ks_code
	FROM requires, desired_pos
	WHERE requires.pos_code = desired_pos.pos_code)
SELECT DISTINCT T.c_code, title
FROM course T
WHERE NOT EXISTS((
	SELECT ks_code
	FROM req_skills)
	MINUS
	(SELECT ks_code
	FROM teaches TS
	WHERE T.c_code = TS.c_code))
~