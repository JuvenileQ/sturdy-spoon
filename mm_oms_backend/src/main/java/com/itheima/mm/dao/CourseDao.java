package com.itheima.mm.dao;

import com.itheima.mm.entity.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * @author: qinyanjin
 * @date: 2021/8/19
 */
public interface CourseDao {
	Integer addCourse(Course course);

	List<Course> getCourseList(QueryPageBean pageBean);

	Long countList(QueryPageBean pageBean);
}
