package com.shortenurl.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SingletonConnection {
	
	private static Connection connection;
	
	static {
		try {
			Properties props = new Properties();
			// Get context of the class, i.e. the path of "/src".
			ClassLoader classLoader = SingletonConnection.class.getClassLoader();
			InputStream stream = classLoader.getResourceAsStream("/resources/dbconnection.properties");
			props.load(stream);
			
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String dburl = props.getProperty("dburl");
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dburl, user, password);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}

}
