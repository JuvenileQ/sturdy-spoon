package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author: qinyanjin
 * @date: 2021/8/18
 */
public interface UserDao {
	User findByUsername(@Param("username") String username);
}
