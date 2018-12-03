package com.uta.sp.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class JdbcConnection<T> {
	
	protected Connection connection;
	
	protected void createConnection() {
		try {
			Properties properties=new Properties();
			InputStream inputStream=JdbcConnection.class.getClassLoader().getResourceAsStream("config.properties");
			properties.load(inputStream);
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.
					getConnection(properties.getProperty("server_url"), 
							properties.getProperty("user"),
							properties.getProperty("password"));
			System.out.println("Connection Successful");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void closeConnection() {
		if (connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	abstract int save(T t);
	abstract int update(T t);
	abstract T getOne(T t);
	

}
