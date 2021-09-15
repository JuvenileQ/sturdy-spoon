package com.itheima.mm.controller;

import org.junit.Test;

import java.sql.*;

/**
 * @author: QinYanJin
 * @date: 2021/8/31
 */
public class JDBCDemo {
	public static void main(String[] args) throws Exception {
		// 1.注册驱动，把驱动文件加载到内存
		Class.forName("com.mysql.cj.jdbc.Driver");
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

		// 2.获得连接
		String url = "jdbc:mysql://43.128.46.143:3306/study";
		Connection connection = DriverManager.getConnection(url, "root", "root");

		// 3.获取执行sql的对象
		String sql = "select * from users";
		PreparedStatement statement = connection.prepareStatement(sql);

		// 4.执行sql语句
		ResultSet result = statement.executeQuery();

		// 5.处理结果，获取每列数据
		while (result.next()) {
			int uid = result.getInt("uid");
			String username = result.getString("username");
			String password = result.getString("password");
			String nickname = result.getString("nickname");

			System.out.println("uid:" + uid + "\t nickname:" + nickname + "\t username:" + username + "\t password:" + password);
		}

		result.close();
		statement.close();
		connection.close();
	}

	@Test
	public void insert() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://43.128.46.143:3306/study", "root", "root");
		String sql = "insert into users values(null,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, "liubei");
		preparedStatement.setString(2, "123");
		preparedStatement.setString(3, "刘备");

		int count = preparedStatement.executeUpdate();
		System.out.println(count);

		preparedStatement.close();
		connection.close();
	}

	@Test
	public void del() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://43.128.46.143:3306/study", "root", "root");

		String sql = "delete from users where uid=?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, 4);

		int count = preparedStatement.executeUpdate();
		System.out.println("count = " + count);

		preparedStatement.close();
		connection.close();
	}

	@Test
	public void update() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://43.128.46.143:3306/study", "root", "root");
		PreparedStatement preparedStatement = connection.prepareStatement("update users set nickname='qinyanjin' where uid=?");
		preparedStatement.setInt(1, 3);
		int i = preparedStatement.executeUpdate();
		System.out.println("i = " + i);

		preparedStatement.close();
		connection.close();
	}

	@Test
	public void query() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://43.128.46.143:3306/study", "root", "root");

		PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			String nickname = resultSet.getString("nickname");
			System.out.println("nickname = " + nickname);
		}

		preparedStatement.close();
		connection.close();
	}

	@Test
	public void test() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			preparedStatement = connection.prepareStatement("select * from users;");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int uid = resultSet.getInt("uid");
				String nickname = resultSet.getString("nickname");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				System.out.println(uid + "\t" + nickname + "\t\t" + username + "\t" + password);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			JDBCUtils.release(resultSet, preparedStatement, connection);
		}
	}
}
