package com.uta.sp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.uta.sp.dto.User;

public class UserDao extends JdbcConnection<User> {

	private static final Logger LOG = Logger.getLogger(UserDao.class);

	@Override
	int save(User t) {
		return 0;
	}

	@Override
	public int update(User t) {
		PreparedStatement statement = null;
		int i = 0;
		try {
			createConnection();
			statement = connection.prepareStatement("update user set LOGIN_ATTEMPTS=? where name=?");
			statement.setInt(1, t.getLoginAttepmts());
			statement.setString(2, t.getUserName());
			i = statement.executeUpdate();
			closeConnection();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	@Override
	public User getOne(User t) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			createConnection();
			statement = connection.prepareStatement("select * from user where name=?;");
			statement.setString(1, t.getUserName());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = new User();
				user.setUserID(resultSet.getInt("USER_ID"));
				user.setUserName(resultSet.getString("NAME"));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setLoginAttepmts(resultSet.getInt("LOGIN_ATTEMPTS"));
				user.setRoleId(resultSet.getInt("ROLE_ID"));
				user.setStudentId(resultSet.getInt("STUDENT_ID"));
				user.setProfessorId(resultSet.getInt("PROFESSOR_ID"));
			}
			closeConnection();

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return user;
	}

	public int updateGrade(int sid, int pid, String grade) {
		String query = "UPDATE student_subject INNER JOIN professor_subject \n"
				+ "       ON student_subject.professor_subject_id=professor_subject.professor_subject_id\n"
				+ "SET GRADE = ?  WHERE student_subject.student_subject_id=? and professor_subject.professor_id=?";
		return update(query, new Object[] { grade, sid, pid });
	}

}
