package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.Component;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

/**
 * @author: QinYanJin
 * @date: 2021/8/18
 */
@Component("userService")
@Slf4j
public class UserServiceImpl extends BaseService implements UserService {
	@Override
	public User findByUsername(String username) {
		SqlSession session = getSession();
		UserDao userDao = getDao(session, UserDao.class);
		User user = userDao.findByUsername(username);
		closeSession(session);

		return user;
	}
}
