package com.uta.sp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.uta.sp.dto.Professor;

public class ProfessorDao extends JdbcConnection<Professor> {

	private static final Logger LOG = Logger.getLogger(ProfessorDao.class);

	@Override
	int save(Professor t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int update(Professor t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Professor getOne(Professor t) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Professor professor = null;
		try {
			createConnection();
			statement = connection.prepareStatement("select * from professor where professor_id=?;");
			statement.setInt(1, t.getProfessorId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				professor = new Professor();
				professor.setProfessorId(resultSet.getInt("PROFESSOR_ID"));
				professor.setName(resultSet.getString("NAME"));
				professor.setEmail(resultSet.getString("EMAIL"));

			}

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					LOG.error(e.getMessage());
				}
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
			closeConnection();
		}

		return professor;
	}

	public List<Map<String, Object>> getProfessorSemester(int professorId) {
		String query = "select  sub.name as SUB_NAME,sub.SUBJECT_ID,S.NAME,ps.PROFESSOR_SUBJECT_ID as PSID,s.SEMESTER_ID as SEM_ID\n"
				+ "from  professor_subject ps inner join semester s on ps.semester_id=s.semester_id \n"
				+ "inner join subject sub on sub.subject_id=ps.subject_id\n" + "where ps.professor_id=" + professorId
				+ " ;";
		return getMany(query);
	}

	public List<Map<String, Object>> getStudentAndGrade(int psid, int professorId) {
		String query = "select ss.STUDENT_SUBJECT_ID,ss.GRADE,s.*\n"
				+ " FROM SGMS.student_subject ss inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id\n"
				+ "inner join student s on s.student_id=ss.student_id where ps.professor_subject_id=" + psid
				+ " and ps.professor_id=" + professorId;
		return getMany(query);
	}

}
