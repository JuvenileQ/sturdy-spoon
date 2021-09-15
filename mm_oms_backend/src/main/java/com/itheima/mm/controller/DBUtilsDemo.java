package com.itheima.mm.controller;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: QinYanJin
 * @date: 2021/9/6
 */
public class DBUtilsDemo {
	@Test
	public void demo01() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?);";
		int count = queryRunner.update(sql, null, "康师傅", 5, "c005");
		System.out.println("count = " + count);
	}


	@Test
	public void demo02() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "update product set price=? where pid=?";
		int count = queryRunner.update(sql, 10, 14);
		System.out.println("count = " + count);
	}

	@Test
	public void demo03() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "delete from product where pid=?";
		int count = queryRunner.update(sql, 14);
		System.out.println("count = " + count);
	}

	@Test
	public void demo04() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product query = queryRunner.query(sql, new BeanHandler<>(Product.class), 3);

		System.out.println(query);
	}

	@Test
	public void demo05() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "select * from product";
		List<Product> productList = queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
		for (Product product : productList) {
			System.out.println("product = " + product);
		}
	}

	@Test
	public void demo06() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "select count(0) from product";
		Long count = queryRunner.query(sql, new ScalarHandler<Long>());
		System.out.println("count = " + count);
	}

	@Test
	public void demo07() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
		String sql = "select * from product";
		List<String> nameList = queryRunner.query(sql, new ColumnListHandler<String>("pname"));
		System.out.println(nameList);
	}

	@Test
	public void demo08() {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// 获取链接
			connection = DruidUtils.getConnection();
			// 开启事务
			connection.setAutoCommit(false);
			String sql1 = "update account set money=money-100 where name='a'";
			PreparedStatement statement1 = connection.prepareStatement(sql1);
			statement1.executeUpdate();
			// 报错
			// int i = 1 / 0;
			String sql2 = "update account set money=money+100 where name='b'";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.executeUpdate();

			connection.commit();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			DruidUtils.release(connection,statement);
		}
	}

	@Test
	public void demo09() {
		Connection connection = null;

		try {
			connection = DruidUtils.getConnection();
			connection.setAutoCommit(false);
			QueryRunner queryRunner = new QueryRunner();
			String sql = "update account set money=money-? where name=?";
			queryRunner.update(connection, sql, "100", "a");
			int i = 1 / 0;
			String sql1 = "update account set money=money+? where name=?";
			queryRunner.update(connection, sql1, "100", "b");

			DbUtils.commitAndCloseQuietly(connection);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(connection);
		}
	}
}
