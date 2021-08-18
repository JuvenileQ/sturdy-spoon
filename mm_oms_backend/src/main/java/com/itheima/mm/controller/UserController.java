package com.itheima.mm.controller;

import com.itheima.framework.annotation.Component;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.framework.annotation.Setter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.common.GlobalConst;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: QinYanJin
 * @date: 2021/8/18
 */
@Component
@Slf4j
public class UserController extends BaseController {
	@Setter("userService")
	private UserService userService;

	@RequestMapping("/user/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User loginForm = parseJSON2Object(request, User.class);
		log.debug("request数据：" + request.getInputStream());

		if (loginForm == null) {
			printResult(response, new Result(false,"请输入用户信息"));
			return;
		}

		User user = userService.findByUsername(loginForm.getUsername());
		if (user == null) {
			printResult(response, new Result(false, "用户名不正确"));
			return;
		}

		if (user.getPassword().equals(loginForm.getPassword())) {
			request.getSession(true).setAttribute(GlobalConst.SESSION_KEY_USER, user);
			printResult(response, new Result(true, "登录成功"));
		} else {
			printResult(response, new Result(false, "密码错误"));
		}
	}
}
