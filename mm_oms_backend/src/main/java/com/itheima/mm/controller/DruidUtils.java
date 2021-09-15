package com.itheima.mm.controller;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author: QinYanJin
 * @date: 2021/9/6
 */
public class DruidUtils {
	public static DataSource dataSource = null;

	static {
		try {
			InputStream in = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
			Properties properties = new Properties();
			properties.load(in);
			dataSource = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void release(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public static void release(Connection connection, Statement statement) {
		release(connection, statement, null);
	}
}
