package com.itheima.framework.mvc;

import com.itheima.framework.HmConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author ：seanyang
 * @date ：Created in 2019/8/9
 * @description ：容器上下文监听器
 * 负责加载配置文件
 * 根据配置文件加载资源
 * @version: 1.0
 */
@Slf4j
public class HmContextLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.debug("HmContextLoaderListener contextInitialized......");
		//获得当前ServletContext
		ServletContext servletContext = servletContextEvent.getServletContext();
		//获得初始化参数
		String scanPackage = servletContext.getInitParameter(HmConst.HM_SCAN_PACKAGE);
		//创建容器对象
		try {
			HmApplicationContext.init(scanPackage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.debug("HmContextLoaderListener contextDestroyed......");
	}
}
