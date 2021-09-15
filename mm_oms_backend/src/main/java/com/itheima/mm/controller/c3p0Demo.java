package com.itheima.mm.controller;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: QinYanJin
 * @date: 2021/9/3
 */
public class c3p0Demo {
	public static DataSource dataSources = new ComboPooledDataSource();

	public static Connection getConnection() throws SQLException {
		return dataSources.getConnection();
	}

	public static void main(String[] args) throws SQLException {
		PreparedStatement preparedStatement = getConnection().prepareStatement("select * from users;");

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			String username = resultSet.getString("username");
			System.out.println(username);

		}
		resultSet.close();
		preparedStatement.close();
	}
}
