package Project;
import java.sql.*;
public class Queries{

	private Connection conn;
	public Queries(Connection conn) throws SQLException{
		this.conn = conn;
	}
	public ResultSet query1(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT person_id, name \n");
		query.append("FROM person NATURAL JOIN position NATURAL JOIN works \n");
		query.append("WHERE comp_id = ? AND (end_date > CURRENT_DATE OR end_date IS NULL) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query2(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT name, pay_rate \n");
		query.append("FROM person NATURAL JOIN works NATURAL JOIN position \n");
		query.append("WHERE comp_id = ? AND (end_date > CURRENT_DATE OR end_date IS NULL) AND pay_type = 'salary' \n");
		query.append("ORDER BY pay_rate DESC \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query3() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT comp_id, comp_name, SUM(pay_rate) AS total_pay \n");
		query.append("FROM company NATURAL JOIN position NATURAL JOIN works \n");
		query.append("WHERE pay_type = 'salary' \n");
		query.append("GROUP BY comp_id, comp_name \n");
		query.append("ORDER BY total_pay DESC \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query4(int param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT pos_code, pos_name \n");
		query.append("FROM position NATURAL JOIN works \n");
		query.append("WHERE person_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query5(int param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code, title \n");
		query.append("FROM knowledge_skill NATURAL JOIN has_skill \n");
		query.append("WHERE person_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query6(int param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code, title \n");
		query.append("FROM knowledge_skill NATURAL JOIN requires NATURAL JOIN works \n");
		query.append("WHERE person_id = ? \n");
		query.append("MINUS \n");
		query.append("SELECT ks_code, title \n");
		query.append("FROM knowledge_skill NATURAL JOIN has_skill \n");
		query.append("WHERE person_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query6b(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code, title \n");
		query.append("FROM knowledge_skill NATURAL JOIN requires \n");
		query.append("WHERE pos_code = ? \n");
		query.append("MINUS \n");
		query.append("SELECT ks_code, title \n");
		query.append("FROM knowledge_skill NATURAL JOIN has_skill \n");
		query.append("WHERE person_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query7a(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code, title \n");
		query.append("FROM requires NATURAL JOIN knowledge_skill \n");
		query.append("WHERE pos_code = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query7b(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code, title \n");
		query.append("FROM skill_cate NATURAL JOIN knowledge_skill \n");
		query.append("WHERE nwcet_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query8(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT ks_code \n");
		query.append("FROM requires \n");
		query.append("WHERE pos_code = ? \n");
		query.append("MINUS \n");
		query.append("SELECT ks_code \n");
		query.append("FROM has_skill \n");
		query.append("WHERE person_id = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query9(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_gap (ks_code) AS(	 \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("	MINUS  \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?)) \n");
		query.append("SELECT DISTINCT T.c_code, title \n");
		query.append("FROM course T \n");
		query.append("WHERE NOT EXISTS(( \n");
		query.append("	SELECT ks_code \n");
		query.append("	FROM skill_gap) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM teaches TS \n");
		query.append("	WHERE T.c_code = TS.c_code)) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query10(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_gap (ks_code) AS(	 \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?)), \n");
		query.append("useful_course (c_code, title) AS( \n");
		query.append("	SELECT DISTINCT c_code, title \n");
		query.append("	FROM course T \n");
		query.append("	WHERE NOT EXISTS ( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM skill_gap) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM teaches TS \n");
		query.append("	WHERE T.c_code = TS.c_code))), \n");
		query.append("useful_section (c_code, section_id, title, completion_date) AS \n");
		query.append("	(SELECT section_id, c_code, title, completion_date \n");
		query.append("	FROM (useful_course) JOIN section USING (c_code)), \n");
		query.append("min_completion_date (min_date) AS \n");
		query.append("	(SELECT MIN(completion_date) \n");
		query.append("	FROM useful_section) \n");
		query.append("SELECT c_code, section_id, title, completion_date \n");
		query.append("FROM useful_section, min_completion_date \n");
		query.append("WHERE useful_section.completion_date = min_completion_date.min_date \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query11(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_gap (ks_code) AS(	 \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?)), \n");
		query.append("useful_course (c_code, title) AS( \n");
		query.append("	SELECT DISTINCT c_code, title \n");
		query.append("	FROM course T \n");
		query.append("	WHERE NOT EXISTS ( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM skill_gap) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM teaches TS \n");
		query.append("	WHERE T.c_code = TS.c_code))), \n");
		query.append("useful_section (c_code, section_id, title, price) AS \n");
		query.append("	(SELECT section_id, c_code, title, price \n");
		query.append("	FROM useful_course JOIN section USING (c_code)), \n");
		query.append("min_price (minprice) AS \n");
		query.append("	(SELECT MIN(price) \n");
		query.append("	FROM useful_section) \n");
		query.append("SELECT useful_section.c_code, useful_section.section_id, useful_section.title, useful_section.price \n");
		query.append("FROM useful_section, min_price \n");
		query.append("WHERE useful_section.price = min_price.minprice \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query12p() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("DROP SEQUENCE CourseSet_seq; \n");
		query.append("CREATE SEQUENCE CourseSet_seq \n");
		query.append("	START WITH 1 \n");
		query.append("	INCREMENT BY 1 \n");
		query.append("	MAXVALUE 999999 \n");
		query.append("	NOCYCLE; \n");
		query.append(" \n");
		query.append("DROP TABLE CourseSet; \n");
		query.append("CREATE TABLE CourseSet( \n");
		query.append("	csetID NUMERIC(8,0) PRIMARY KEY, \n");
		query.append("	c_code1 VARCHAR(10), \n");
		query.append("	c_code2 VARCHAR(10), \n");
		query.append("	c_code3 VARCHAR(10), \n");
		query.append("	csetsize NUMERIC(2,0)); \n");
		query.append("INSERT INTO CourseSet \n");
		query.append("	SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, null, 2 \n");
		query.append("	FROM course C1, course C2 \n");
		query.append("	WHERE C1.c_code < C2.c_code; \n");
		query.append("INSERT INTO CourseSet \n");
		query.append("	SELECT CourseSet_seq.NEXTVAL, C1.c_code, C2.c_code, C3.c_code, 3 \n");
		query.append("	FROM course C1, course C2, course C3 \n");
		query.append("	WHERE C1.c_code < C2.c_code AND C2.c_code < C3.c_code; \n");
		query.append("DROP TABLE CourseSet_Skill; \n");
		query.append("CREATE TABLE CourseSet_Skill( \n");
		query.append("	csetID NUMERIC(8,0), \n");
		query.append("	ks_code NUMERIC(4,0), \n");
		query.append("	PRIMARY KEY (cSetID, ks_code)) \n");
		query.append(" \n");
		query.append("INSERT INTO CourseSet_Skill(csetID, ks_code) \n");
		query.append("	(SELECT csetID, ks_code \n");
		query.append("	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code1 = CS.c_code) \n");
		query.append("	UNION \n");
		query.append("	(SELECT csetID, ks_code \n");
		query.append("	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code2 = CS.c_code) \n");
		query.append("	UNION \n");
		query.append("	(SELECT csetID, ks_code \n");
		query.append("	FROM CourseSet CSet JOIN teaches CS ON CSet.c_code3 = CS.c_code) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query12(String param1, int param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_gap (ks_code) AS(	 \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("	MINUS  \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?)), \n");
		query.append("Cover_Cset(csetID, csize) AS ( \n");
		query.append("	SELECT csetID, csetsize \n");
		query.append("	FROM CourseSet cSet \n");
		query.append("	WHERE NOT EXISTS( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM skill_gap) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM CourseSet_Skill CSSk \n");
		query.append("	WHERE CSSk.csetID = Cset.csetID))) \n");
		query.append("SELECT c_code1, c_code2, c_code3 \n");
		query.append("FROM Cover_CSet NATURAL JOIN CourseSet \n");
		query.append("WHERE csetsize = (SELECT MIN(csize) FROM Cover_CSet) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setInt(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query13(int param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH subtable (nwcet_id) AS( \n");
		query.append("	(SELECT nwcet_id \n");
		query.append("	FROM knowledge_skill NATURAL JOIN ( \n");
		query.append("		SELECT *  \n");
		query.append("		FROM has_skill \n");
		query.append("		WHERE person_id = ?))) \n");
		query.append("SELECT cate_code \n");
		query.append("FROM job_category J  \n");
		query.append("WHERE NOT EXISTS( \n");
		query.append("	(SELECT nwcet_id \n");
		query.append("	FROM subtable) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT nwcet_id \n");
		query.append("	FROM core_skill C \n");
		query.append("	WHERE J.cate_code = C.cate_code)) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query14(int param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH positions_qualified (pos_code, pos_name, comp_id, emp_mode, standard_pay) AS (	 \n");
		query.append("	SELECT pos_code, pos_name, comp_id, emp_mode, standard_pay \n");
		query.append("	FROM position P \n");
		query.append("	WHERE NOT EXISTS ( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires R \n");
		query.append("	WHERE R.pos_code = P.pos_code) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?))), \n");
		query.append("max_pay(value) AS ( \n");
		query.append("	SELECT MAX(standard_pay) \n");
		query.append("	FROM positions_qualified \n");
		query.append(") \n");
		query.append("SELECT positions_qualified.pos_code, pos_name, comp_id, emp_mode, standard_pay \n");
		query.append("FROM max_pay, positions_qualified  \n");
		query.append("WHERE standard_pay = max_pay.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query14b(int param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT pos_code, pos_name, comp_name, emp_mode, standard_pay  \n");
		query.append("FROM position P NATURAL JOIN company \n");
		query.append("WHERE NOT EXISTS ( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires R \n");
		query.append("	WHERE R.pos_code = P.pos_code) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill \n");
		query.append("	WHERE person_id = ?)) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setInt(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query15(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT person_id, name, email \n");
		query.append("FROM person S \n");
		query.append("WHERE NOT EXISTS ( \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM requires  \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM has_skill T \n");
		query.append("	WHERE S.person_id = T.person_id)) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query16(String param1, String param2) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH number_of_skills_required (num_skills_count) AS ( \n");
		query.append("	SELECT COUNT (ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?), \n");
		query.append("people_skill (person_id, count_skills) AS ( \n");
		query.append("	SELECT person_id, COUNT (ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id) \n");
		query.append("SELECT person_id \n");
		query.append("FROM people_skill P, number_of_skills_required N \n");
		query.append("WHERE P.count_skills = N.num_skills_count - 1 \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setString(2, param2);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query17(String param1, String param2, String param3) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH PersonRequiredSkillCnt(person_id, skillCnt) AS( \n");
		query.append("	SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM has_skill NATURAL JOIN requires \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("requiredSkillCnt(cnt) AS ( \n");
		query.append("	SELECT COUNT(ks_code)  \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?), \n");
		query.append("missing_one (person_id) AS( \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM PersonRequiredSkillCnt, requiredSkillCnt \n");
		query.append("	WHERE skillCnt = cnt - 1), \n");
		query.append("skills_req (ks_code) AS ( \n");
		query.append("	SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?), \n");
		query.append("poss_people(person_id) AS ( \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM person NATURAL JOIN missing_one), \n");
		query.append("people_missing_skill (ks_code, person_id) AS ( \n");
		query.append("	(SELECT ks_code, person_id \n");
		query.append("	FROM skills_req S, poss_people P) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code, person_id \n");
		query.append("	FROM has_skill)) \n");
		query.append("SELECT ks_code, COUNT(DISTINCT person_id) AS num_people \n");
		query.append("FROM people_missing_skill \n");
		query.append("GROUP BY ks_code \n");
		query.append("ORDER BY num_people ASC \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setString(2, param2);
		ps.setString(3, param3);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query18(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_count (person_id, num_skills) AS \n");
		query.append("	(SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("least_missing (value) AS  \n");
		query.append("	(SELECT MAX(num_skills) \n");
		query.append("	FROM skill_count) \n");
		query.append("SELECT person_id, value \n");
		query.append("FROM skill_count, least_missing \n");
		query.append("WHERE num_skills = least_missing.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query18b(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_count (person_id, num_skills) AS \n");
		query.append("	(SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("least_missing (value) AS  \n");
		query.append("	(SELECT MAX(num_skills) \n");
		query.append("	FROM skill_count) \n");
		query.append("SELECT person_id, name, email, value \n");
		query.append("FROM (skill_count NATURAL JOIN person), least_missing \n");
		query.append("WHERE num_skills = least_missing.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query19(String param1, String param2, int param3) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_count (person_id, num_skills) AS \n");
		query.append("	(SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("skills_req(num_req) AS \n");
		query.append("	(SELECT COUNT(ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("SELECT person_id, skills_req.num_req - num_skills AS missing_k \n");
		query.append("FROM skill_count, skills_req \n");
		query.append("WHERE  skills_req.num_req - num_skills <= ? \n");
		query.append("ORDER BY missing_k ASC \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setString(2, param2);
		ps.setInt(3, param3);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query19b(String param1, String param2, int param3) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_count (person_id, num_skills) AS \n");
		query.append("	(SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("skills_req(num_req) AS \n");
		query.append("	(SELECT COUNT(ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?) \n");
		query.append("SELECT person_id, name, email, skills_req.num_req - num_skills AS missing_k \n");
		query.append("FROM (skill_count NATURAL JOIN person), skills_req  \n");
		query.append("WHERE  skills_req.num_req - num_skills <= ? \n");
		query.append("ORDER BY missing_k ASC \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setString(2, param2);
		ps.setInt(3, param3);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query20(String param1, String param2, String param3) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH skill_count (person_id, num_skills) AS \n");
		query.append("	(SELECT person_id, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill \n");
		query.append("	WHERE pos_code = ? \n");
		query.append("	GROUP BY person_id), \n");
		query.append("num_skills_req(num_req) AS \n");
		query.append("	(SELECT COUNT(ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?), \n");
		query.append("missing_k_list(person_id, number_missing) AS \n");
		query.append("	(SELECT person_id, num_req - num_skills  \n");
		query.append("	FROM skill_count, num_skills_req \n");
		query.append("	WHERE num_req - num_skills <= 3), \n");
		query.append("skills_req (ks_code) AS ( \n");
		query.append("	SELECT ks_code \n");
		query.append("	FROM requires \n");
		query.append("	WHERE pos_code = ?), \n");
		query.append("poss_people(person_id) AS ( \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM person NATURAL JOIN missing_k_list), \n");
		query.append("people_missing_skill (ks_code, person_id) AS ( \n");
		query.append("	(SELECT ks_code, person_id \n");
		query.append("	FROM skills_req S, poss_people P) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code, person_id \n");
		query.append("	FROM has_skill)) \n");
		query.append("SELECT ks_code, COUNT(person_id) AS num_people \n");
		query.append("FROM people_missing_skill \n");
		query.append("GROUP BY ks_code \n");
		query.append("ORDER BY num_people DESC; \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ps.setString(2, param2);
		ps.setString(3, param3);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query21(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT person_id, name, EXTRACT(year FROM start_date)\"Start Year\", EXTRACT(year FROM end_date) \"End year\" \n");
		query.append("FROM person NATURAL JOIN works NATURAL JOIN position \n");
		query.append("WHERE cate_code = ? \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query22(String param1) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("(SELECT DISTINCT person_id \n");
		query.append("FROM person NATURAL JOIN works \n");
		query.append("WHERE pos_code = ?) \n");
		query.append("MINUS \n");
		query.append("(SELECT person_id \n");
		query.append("FROM works \n");
		query.append("WHERE end_date > CURRENT_DATE OR end_date IS NULL) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ps.setString(1, param1);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query23a() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH comp_emp(company_id, end_date2, num_employees) AS \n");
		query.append("	(SELECT comp_id, end_date, COUNT(DISTINCT person_id) \n");
		query.append("	FROM position NATURAL JOIN works \n");
		query.append("	GROUP BY comp_id, end_date \n");
		query.append("	HAVING end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("max_emp(value) AS \n");
		query.append("	(SELECT MAX(num_employees) \n");
		query.append("	FROM comp_emp) \n");
		query.append("SELECT company_id \n");
		query.append("FROM comp_emp, max_emp \n");
		query.append("WHERE comp_emp.num_employees = max_emp.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query23b() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH comp_pay(company_id, end_date2, pay) AS \n");
		query.append("	(SELECT comp_id, end_date, SUM(pay_rate) \n");
		query.append("	FROM position NATURAL JOIN works \n");
		query.append("	GROUP BY comp_id, end_date \n");
		query.append("	HAVING end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("max_pay(value) AS \n");
		query.append("	(SELECT MAX(pay) \n");
		query.append("	FROM comp_pay) \n");
		query.append("SELECT company_id \n");
		query.append("FROM comp_pay, max_pay \n");
		query.append("WHERE comp_pay.pay = max_pay.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query24a() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH comp_emp(company_id, end_date2, num_employees) AS \n");
		query.append("	(SELECT comp_id, end_date, COUNT(DISTINCT person_id) \n");
		query.append("	FROM position NATURAL JOIN works \n");
		query.append("	GROUP BY comp_id, end_date \n");
		query.append("	HAVING end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("sector_emp(gics_id, sec_num_employees) AS \n");
		query.append("	(SELECT gics_id, SUM(num_employees) \n");
		query.append("	FROM comp_emp NATURAL JOIN industry \n");
		query.append("	GROUP BY gics_id), \n");
		query.append("max_employees (value) AS \n");
		query.append("	(SELECT MAX(sec_num_employees) \n");
		query.append("	FROM sector_emp) \n");
		query.append("SELECT gics_id \n");
		query.append("FROM sector_emp, max_employees \n");
		query.append("WHERE sector_emp.sec_num_employees = max_employees.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query24b() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH comp_pay(company_id, end_date2, pay) AS \n");
		query.append("	(SELECT comp_id, end_date, SUM(pay_rate) \n");
		query.append("	FROM position NATURAL JOIN works \n");
		query.append("	GROUP BY comp_id, end_date \n");
		query.append("	HAVING end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("sector_pay(gics_id, sec_pay) AS \n");
		query.append("	(SELECT gics_id, SUM(pay) \n");
		query.append("	FROM comp_pay NATURAL JOIN industry \n");
		query.append("	GROUP BY gics_id), \n");
		query.append("max_pay(value) AS \n");
		query.append("	(SELECT MAX(sec_pay) \n");
		query.append("	FROM sector_pay) \n");
		query.append("SELECT gics_id \n");
		query.append("FROM sector_pay, max_pay \n");
		query.append("WHERE sector_pay.sec_pay = max_pay.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query25a() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH old_salary(person_id, old_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date < CURRENT_DATE \n");
		query.append("	), \n");
		query.append("current_salary(person_id, new_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL \n");
		query.append("	) \n");
		query.append("SELECT DISTINCT COUNT(person_id) \n");
		query.append("FROM old_salary O NATURAL JOIN current_salary N \n");
		query.append("WHERE N.new_salary_val > O.old_salary_val \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query25b() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH old_salary(person_id, old_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date < CURRENT_DATE \n");
		query.append("	), \n");
		query.append("current_salary(person_id, new_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL \n");
		query.append("	) \n");
		query.append("SELECT DISTINCT COUNT(person_id) \n");
		query.append("FROM old_salary O NATURAL JOIN current_salary N \n");
		query.append("WHERE N.new_salary_val < O.old_salary_val \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query25c() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH old_salary(person_id, old_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date < CURRENT_DATE \n");
		query.append("	), \n");
		query.append("current_salary(person_id, new_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works  \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL \n");
		query.append("	), \n");
		query.append("number_increase (total_increase) AS  \n");
		query.append("	(SELECT DISTINCT COUNT(person_id) \n");
		query.append("	FROM old_salary O NATURAL JOIN current_salary N \n");
		query.append("	WHERE N.new_salary_val > O.old_salary_val), \n");
		query.append("number_decrease (total_decrease) AS  \n");
		query.append("	(SELECT DISTINCT COUNT(person_id) \n");
		query.append("	FROM old_salary O NATURAL JOIN current_salary N \n");
		query.append("	WHERE N.new_salary_val < O.old_salary_val) \n");
		query.append("SELECT total_increase, total_decrease , total_increase / total_decrease AS ratio \n");
		query.append("FROM number_increase, number_decrease \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query25d() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH old_salary(person_id, old_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works NATURAL JOIN position NATURAL JOIN company \n");
		query.append("	WHERE end_date < CURRENT_DATE AND primary_sector = 'Software' \n");
		query.append("	), \n");
		query.append("current_salary(person_id, new_salary_val) AS \n");
		query.append("	(SELECT person_id, pay_rate \n");
		query.append("	FROM works works NATURAL JOIN position NATURAL JOIN company \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL AND primary_sector = 'Software' \n");
		query.append("	), \n");
		query.append("earning_change (person_id, earning_change) AS \n");
		query.append("	(SELECT person_id, (current_salary.new_salary_val- old_salary.old_salary_val) AS earning_change  \n");
		query.append("	FROM old_salary NATURAL JOIN current_salary \n");
		query.append("	) \n");
		query.append("SELECT AVG(earning_change.earning_change) AS avg_earning_change_rate \n");
		query.append("FROM earning_change \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query26() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH cate_pos (cate_code, num_pos) AS ( \n");
		query.append("	SELECT cate_code, COUNT(DISTINCT pos_code) \n");
		query.append("	FROM position  \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("open_pos (cate_code, num_pos_open) AS ( \n");
		query.append("	SELECT cate_code, COUNT(DISTINCT pos_code) \n");
		query.append("	FROM position NATURAL LEFT OUTER JOIN works \n");
		query.append("	WHERE (end_date > CURRENT_DATE OR end_date IS NULL) OR (person_id IS NULL) \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("pos_skills_req (pos_code, cate_code, num_skills) AS ( \n");
		query.append("	SELECT pos_code, cate_code, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN position \n");
		query.append("	GROUP BY pos_code, cate_code), \n");
		query.append("most_skills_cate (cate_code, max_skills) AS ( \n");
		query.append("	SELECT cate_code, MAX(num_skills) \n");
		query.append("	FROM pos_skills_req \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("cate_reps (pos_code, cate_code, num_skill) AS( \n");
		query.append("	SELECT P.pos_code, P.cate_code, num_skills \n");
		query.append("	FROM pos_skills_req P, most_skills_cate M \n");
		query.append("	WHERE P.num_skills = M.max_skills AND P.cate_code = M.cate_code), \n");
		query.append("number_of_skills_required (pos_code, num_skills_count) AS ( \n");
		query.append("	SELECT pos_code, COUNT(ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	GROUP BY pos_code), \n");
		query.append("jobless (person_id) AS ( \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM person \n");
		query.append("	MINUS \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("people_skill (person_id, pos_code, count_skills) AS ( \n");
		query.append("	SELECT person_id, pos_code ,COUNT (ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill NATURAL JOIN jobless \n");
		query.append("	GROUP BY person_id, pos_code), \n");
		query.append("num_qualified_position(pos_code, num_people) AS( \n");
		query.append("	SELECT pos_code, COUNT(person_id) \n");
		query.append("	FROM people_skill P JOIN number_of_skills_required N USING (pos_code) \n");
		query.append("	WHERE P.count_skills = N.num_skills_count  \n");
		query.append("	GROUP BY pos_code), \n");
		query.append("num_qualified_cate(cate_code, num_people_cate) AS( \n");
		query.append("	SELECT cate_code, num_people \n");
		query.append("	FROM num_qualified_position NATURAL JOIN cate_reps), \n");
		query.append("fillable_vacancies (cate_code, num_qual_people) AS( \n");
		query.append("	SELECT cate_code, num_pos_open - num_people_cate \n");
		query.append("	FROM num_qualified_cate NATURAL JOIN open_pos), \n");
		query.append("max_vacancies (value) AS( \n");
		query.append("	SELECT MAX(num_qual_people) \n");
		query.append("	FROM fillable_vacancies) \n");
		query.append("SELECT cate_code, num_qual_people \n");
		query.append("FROM fillable_vacancies, max_vacancies \n");
		query.append("WHERE fillable_vacancies.num_qual_people = max_vacancies.value \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet query27() throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("WITH cate_pos (cate_code, num_pos) AS ( \n");
		query.append("	SELECT cate_code, COUNT(DISTINCT pos_code) \n");
		query.append("	FROM position  \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("open_pos (cate_code, num_pos_open) AS ( \n");
		query.append("	SELECT cate_code, COUNT(DISTINCT pos_code) \n");
		query.append("	FROM position NATURAL LEFT OUTER JOIN works \n");
		query.append("	WHERE (end_date > CURRENT_DATE OR end_date IS NULL) OR (person_id IS NULL) \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("pos_skills_req (pos_code, cate_code, num_skills) AS ( \n");
		query.append("	SELECT pos_code, cate_code, COUNT(ks_code) \n");
		query.append("	FROM requires NATURAL JOIN position \n");
		query.append("	GROUP BY pos_code, cate_code), \n");
		query.append("most_skills_cate (cate_code, max_skills) AS ( \n");
		query.append("	SELECT cate_code, MAX(num_skills) \n");
		query.append("	FROM pos_skills_req \n");
		query.append("	GROUP BY cate_code), \n");
		query.append("cate_reps (pos_code, cate_code, num_skill) AS( \n");
		query.append("	SELECT P.pos_code, P.cate_code, num_skills \n");
		query.append("	FROM pos_skills_req P, most_skills_cate M \n");
		query.append("	WHERE P.num_skills = M.max_skills AND P.cate_code = M.cate_code), \n");
		query.append("number_of_skills_required (pos_code, num_skills_count) AS ( \n");
		query.append("	SELECT pos_code, COUNT(ks_code) \n");
		query.append("	FROM requires \n");
		query.append("	GROUP BY pos_code), \n");
		query.append("jobless (person_id) AS ( \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM person \n");
		query.append("	MINUS \n");
		query.append("	SELECT person_id \n");
		query.append("	FROM works \n");
		query.append("	WHERE end_date > CURRENT_DATE OR end_date IS NULL), \n");
		query.append("people_skill (person_id, pos_code, count_skills) AS ( \n");
		query.append("	SELECT person_id, pos_code ,COUNT (ks_code) \n");
		query.append("	FROM requires NATURAL JOIN has_skill NATURAL JOIN jobless \n");
		query.append("	GROUP BY person_id, pos_code), \n");
		query.append("num_qualified_position(pos_code, num_people) AS( \n");
		query.append("	SELECT pos_code, COUNT(person_id) \n");
		query.append("	FROM people_skill P JOIN number_of_skills_required N USING (pos_code) \n");
		query.append("	WHERE P.count_skills = N.num_skills_count  \n");
		query.append("	GROUP BY pos_code), \n");
		query.append("num_qualified_cate(cate_code, num_people_cate) AS( \n");
		query.append("	SELECT cate_code, num_people \n");
		query.append("	FROM num_qualified_position NATURAL JOIN cate_reps), \n");
		query.append("fillable_vacancies (cate_code, num_qual_people) AS( \n");
		query.append("	SELECT cate_code, num_pos_open - num_people_cate \n");
		query.append("	FROM num_qualified_cate NATURAL JOIN open_pos), \n");
		query.append("max_vacancies (value) AS( \n");
		query.append("	SELECT MAX(num_qual_people) \n");
		query.append("	FROM fillable_vacancies), \n");
		query.append("desired_cate (cate_code) AS( \n");
		query.append("	SELECT cate_code \n");
		query.append("	FROM fillable_vacancies, max_vacancies \n");
		query.append("	WHERE fillable_vacancies.num_qual_people = max_vacancies.value), \n");
		query.append("desired_pos (pos_code) AS( \n");
		query.append("	SELECT pos_code \n");
		query.append("	FROM desired_cate NATURAL JOIN cate_reps), \n");
		query.append("req_skills (ks_code) AS(	 \n");
		query.append("	SELECT ks_code \n");
		query.append("	FROM requires, desired_pos \n");
		query.append("	WHERE requires.pos_code = desired_pos.pos_code) \n");
		query.append("SELECT DISTINCT T.c_code, title \n");
		query.append("FROM course T \n");
		query.append("WHERE NOT EXISTS(( \n");
		query.append("	SELECT ks_code \n");
		query.append("	FROM req_skills) \n");
		query.append("	MINUS \n");
		query.append("	(SELECT ks_code \n");
		query.append("	FROM teaches TS \n");
		query.append("	WHERE T.c_code = TS.c_code)) \n");
		PreparedStatement ps = conn.prepareStatement(query.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

}