package com.itheima.framework.mvc;

import com.itheima.framework.HmConst;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author ：seanyang
 * @date ：Created in 2019/8/9
 * @description ：请求转发控制器
 *  负责把所有客户端的请求路径，转发调用对应的控制器类实例的具体方法
 * @version: 1.0
 */
@Slf4j
public class HmDispatherServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 截取我们需要的web资源的名称  即：去掉扩展名 和 web应用名称
		String mappingPath = req.getServletPath();
		if(mappingPath.endsWith(".do")){
			mappingPath = mappingPath.replaceAll(".do","");
		}
		log.debug("mappingPath:{}",mappingPath);
		// 根据路径，找到HmMethod对象，并调用控制器的方法
		Method method = HmApplicationContext.methodMaps.get(mappingPath);
		if(method != null){
			// 获取method定义的类的全名称
			String className = method.getDeclaringClass().getName();
			// 根据类的全名称，获取这个类的实例
			Object instance = HmApplicationContext.beanMaps.get(className);
			try{
				// 调用这个类的方法
				method.invoke(instance,req,resp);
			}catch(Exception e){
			    e.printStackTrace();
			    throw new ServletException(e.getMessage());
			}
		}else {
			throw new ServletException("访问路径错误或路径资源未实例化，mappingPath:"+mappingPath);
		}
	}
}
