package com.itheima.mm.service;

import com.itheima.mm.pojo.User;

/**
 * @author: qinyanjin
 * @date: 2021/8/18
 */
public interface UserService {
	/**
	 * 根据用户名获取用户信息
	 *
	 * @param username 用户名
	 * @return 用户对象
	 */
	User findByUsername(String username);
}
