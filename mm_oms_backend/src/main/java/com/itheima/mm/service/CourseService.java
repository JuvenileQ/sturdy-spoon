package com.itheima.mm.service;

import com.itheima.mm.entity.PageResult;
import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

/**
 * @author: qinyanjin
 * @date: 2021/8/19
 */
public interface CourseService {
	void addCourse(Course course);

	PageResult getCourseList(QueryPageBean pageBean);

	void updateCourse(Course course);

}
