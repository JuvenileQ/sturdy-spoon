package com.itheima.mm.controller;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        try {
            // 1 创建对象
            Properties props = new Properties();
            // 2 加载文件
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            props.load(in);
            // 3 读取内容 且 打印
            driver = props.getProperty("jdbc.driver");
            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");

            // 1 注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1 获取连接
    public static Connection getConnection() throws SQLException {
        // 2 获取连接
        return DriverManager.getConnection(url, username, password);
    }

	// 2 释放资源
	public static void release(ResultSet rs, PreparedStatement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public static void release(Connection conn, PreparedStatement stmt) {
	    release(null, stmt, conn);
    }
}