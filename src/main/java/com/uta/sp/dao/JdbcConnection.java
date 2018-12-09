package com.uta.sp.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class JdbcConnection<T> {

	private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class);

	protected Connection connection;

	protected void createConnection() {
		try {
			Properties properties = new Properties();
			InputStream inputStream = JdbcConnection.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(inputStream);
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(properties.getProperty("server_url"),
					properties.getProperty("user"), properties.getProperty("password"));
			System.out.println("Connection Successful");
		} catch (ClassNotFoundException e) {
			LOGGER.error(e);
		} catch (SQLException e) {
			LOGGER.error(e);
		} catch (FileNotFoundException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}

	}

	protected void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error(e);
			}
		}
	}

	abstract int save(T t);

	abstract int update(T t);

	abstract T getOne(T t);

	protected int update(String query, Object[] data) {
		createConnection();
		PreparedStatement preparedStatement;
		int i = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			for (int j = 0; j < data.length; j++) {
				Object object = data[j];
				if (object instanceof Integer) {
					preparedStatement.setInt(j + 1, ((Integer) object));
				} else if (object instanceof String) {
					preparedStatement.setString(j + 1, object.toString());
				}

			}
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		closeConnection();
		return i;
	}

	protected List<Map<String, Object>> getMany(String query) {

		createConnection();
		PreparedStatement statement;
		ResultSet resultSet = null;
		List<Map<String, Object>> resultSetToArrayList=null;
		try {

			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			resultSetToArrayList = resultSetToArrayList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e);
		}
		closeConnection();
		return resultSetToArrayList;
	}

	private List<Map<String, Object>> resultSetToArrayList(ResultSet rs) {
		ResultSetMetaData md = null;
		ArrayList list = new ArrayList();
		try {
			md = rs.getMetaData();
			int columns = md.getColumnCount();
			while (rs.next()) {
				HashMap row = new HashMap(columns);
				for (int i = 1; i <= columns; ++i) {
					row.put(md.getColumnLabel(i), rs.getObject(i));
				}
				list.add(row);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return list;
	}

}
