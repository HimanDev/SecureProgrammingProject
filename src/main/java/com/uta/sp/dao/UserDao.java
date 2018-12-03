package com.uta.sp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.uta.sp.dto.User;

public class UserDao extends JdbcConnection<User> {

	@Override
	int save(User t) {
		return 0;
	}

	@Override
	public int update(User t) {
		PreparedStatement statement=null;
		int i=0;
		try {
			createConnection();
			statement=connection.prepareStatement("update user set LOGIN_ATTEMPTS=? where name=?");
			statement.setInt(1, t.getLoginAttepmts());
			statement.setString(2, t.getUserName());
			i=statement.executeUpdate();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
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
			}
			closeConnection();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

}
