package com.itheima.mm.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: QinYanJin
 * @date: 2021/9/6
 */
public class DruidDemo {
	public static void main(String[] args) throws SQLException {
		Connection connection = DruidUtils.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from users");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			System.out.println(resultSet.getString("username"));
		}
		DruidUtils.release(connection, statement, resultSet);
	}
}
