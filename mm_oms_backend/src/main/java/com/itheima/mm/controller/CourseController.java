package com.itheima.mm.controller;

import com.itheima.framework.annotation.Component;
import com.itheima.framework.annotation.RequestMapping;
import com.itheima.framework.annotation.Setter;
import com.itheima.mm.base.BaseController;
import com.itheima.mm.common.GlobalConst;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.entity.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author: QinYanJin
 * @date: 2021/8/19
 */
@Slf4j
@Component
public class CourseController extends BaseController {
	@Setter("courseService")
	private CourseService courseService;

	@RequestMapping("/course/add")
	public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			Course course = parseJSON2Object(request, Course.class);
			course.setCreateDate(DateUtils.parseDate2String(new Date()));

			final User user = getSessionUser(request, GlobalConst.SESSION_KEY_USER);
			if (user != null) {
				course.setUserId(user.getId());
			} else {
				printResult(response, new Result(false, "请登录"));
				return;
			}
			courseService.addCourse(course);
			printResult(response, new Result(true, "添加学科成功"));
		} catch (RuntimeException e) {
			e.printStackTrace();
			printResult(response, new Result(false, "添加学科失败，" + e.getMessage()));
		}
	}

	@RequestMapping("/course/getCourseList")
	public void getCourseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		QueryPageBean pageBean = parseJSON2Object(request, QueryPageBean.class);
		if (pageBean==null) {
			pageBean = new QueryPageBean();
			pageBean.setCurrentPage(1);
			pageBean.setPageSize(10);
		}

		PageResult pageResult = courseService.getCourseList(pageBean);
		printResult(response, new Result(true, "获取学科列表成功", pageResult));
	}
}
