package com.uta.sp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.uta.sp.dto.Professor;
import com.uta.sp.dto.Student;

public class StudentDao extends JdbcConnection<Student> {

	private static final Logger LOG = Logger.getLogger(StudentDao.class);

	@Override
	public int save(Student t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Student t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Student getOne(Student t) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Student student = null;
		try {
			createConnection();
			statement = connection.prepareStatement("select * from student where student_id=?;");
			statement.setInt(1, t.getStudentId());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				student = new Student();
				student.setStudentId(resultSet.getInt("STUDENT_ID"));
				student.setName(resultSet.getString("NAME"));
				student.setEmail(resultSet.getString("EMAIL"));

			}
			closeConnection();

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return student;
	}

	public List<Map<String, Object>> selectAllSem(int sid) {
		String query = "SELECT count(sem.name) as C,sem.NAME,sem.SEMESTER_ID\n" + " FROM SGMS.student_subject ss \n"
				+ "inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id\n"
				+ "inner join subject s on s.subject_id=ps.subject_id\n"
				+ "inner join semester sem on sem.semester_id=ps.semester_id where ss.student_id=" + sid
				+ " group by sem.Name;";
		return getMany(query);
	}

	public List<Map<String, Object>> selectGrade(int sid, int semId) {
		String query = "\n" + "SELECT ss.GRADE,s.NAME,p.NAME as PNAME\n" + " FROM SGMS.student_subject ss \n"
				+ "inner join professor_subject ps on ss.professor_subject_id=ps.professor_subject_id\n"
				+ "inner join subject s on s.subject_id=ps.subject_id\n"
				+ "inner join semester sem on sem.semester_id=ps.semester_id \n"
				+ "inner join professor p on p.professor_id=ps.professor_id where ss.student_id=" + sid + " "
				+ "and sem.semester_id=" + semId;
		return getMany(query);
	}

}
