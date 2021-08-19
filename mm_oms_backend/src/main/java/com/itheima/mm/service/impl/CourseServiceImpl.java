package com.itheima.mm.service.impl;

import com.itheima.framework.annotation.Component;
import com.itheima.mm.base.BaseService;
import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.database.MmDaoException;
import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author: QinYanJin
 * @date: 2021/8/19
 */
@Component("courseService")
@Slf4j
public class CourseServiceImpl extends BaseService implements CourseService  {
	@Override
	public void addCourse(Course course) {
		SqlSession session = getSession();
		CourseDao courseDao = getDao(session, CourseDao.class);
		Integer result = courseDao.addCourse(course);

		if (result == 0) {
			throw new MmDaoException("添加学科失败");
		}
		commitAndCloseSession(session);
	}

	@Override
	public PageResult getCourseList(QueryPageBean pageBean) {
		SqlSession sqlSession = getSession();
		CourseDao courseDao = getDao(sqlSession, CourseDao.class);

		List<Course> courses = courseDao.getCourseList(pageBean);
		Long totalCount = courseDao.countList(pageBean);

		closeSession(sqlSession);
		return new PageResult(totalCount, courses);
	}

	@Override
	public void updateCourse(Course course) {
		SqlSession sqlSession = getSession();
		CourseDao courseDao = getDao(sqlSession, CourseDao.class);

		Integer result = courseDao.updateCourse(course);
		if (result == 0) {
			throw new MmDaoException("更新失败");
		}
		commitAndCloseSession(sqlSession);
	}
}
