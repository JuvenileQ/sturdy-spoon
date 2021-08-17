package com.itheima.framework.mvc;

import com.itheima.framework.HmException;
import com.itheima.framework.annotation.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itheima.framework.HmConst.HM_XML_COMPONENT_SCAN_TAG;
import static com.itheima.framework.HmConst.HM_XML_SCOMPONENT_SCAN_ATTR;

/**
 * @author ：seanyang
 * @date ：Created in 2019/8/9
 * @description ：应用上下文
 *  存储注解类的实例
 *  存储注解方法的实例
 * @version: 1.0
 */
@Getter
@Slf4j
public class HmApplicationContext {
	// 定义容器，解析类注解，存储bean实例
	public static Map<String, Object> beanMaps = new HashMap<>();

	// 定义容器，解析方法注解，存储映射地址与方法实例
	public static Map<String, Method> methodMaps = new HashMap<>();

	public static void init(String scanPackage) throws HmException {
		//创建容器对象
		beanMaps = new HashMap<>();
		try{
			// 自动扫包，加载类注解，并实例对象
			initBean(scanPackage);
			// 加载类中的成员数据的注解，并初始化注解内容
			initBeanFieldAndMethod();
		}catch(Exception e){
		    e.printStackTrace();
		    throw new HmException("初始化上下文失败,"+e.getMessage());
		}
		log.debug("beanMaps:{}",beanMaps);
		log.debug("methodMaps:{}",methodMaps);

	}

	/**
	 * 自动扫包，加载类注解，并实例对象
	 * @param scanPackage
	 * @throws Exception
	 */
	private static  void  initBean(String scanPackage) throws Exception{
			//获得component-scan标签的基本包名称
			//调用工具ClassScanner获得该包及其子包下的字节码对象
			List<Class<?>> classsFromPackage = HmClassScanner.getClasssFromPackage(scanPackage);
			//遍历看类上是否使用的@Component注解
			if(null!=classsFromPackage&&classsFromPackage.size()>0){
				for (Class<?> aClass : classsFromPackage) {
					//判断是否使用的@HmComponent注解
					if(aClass.isAnnotationPresent(HmComponent.class)){
						//获得该类上的注解对象
						HmComponent component = aClass.getAnnotation(HmComponent.class);
						//判断属性是否赋值 如果Component没有值 就赋值为当前类名
						String beanId = component.value();
						Object instance = aClass.newInstance();
						beanMaps.put(aClass.getName(),instance);
						if(beanId.length() != 0){
							beanMaps.put(beanId,instance);
						}
					}
				}
			}
	}
	/**
	 * 读取类成员属性注解，并初始化注解
	 * @throws Exception
	 */
	private static  void initBeanFieldAndMethod() throws Exception {
		if (beanMaps == null || beanMaps.size() == 0) {
			return;
		}

		for (Map.Entry<String, Object> entry : beanMaps.entrySet()) {
			//获得容器中Bean实例
			Object instance =  entry.getValue();
			//获得容器中Bean实例类的字节码对象
			Class clazz = instance.getClass();
			//通过字节码对象，获取所有字段信息
			Field[] declaredFields = clazz.getDeclaredFields();
			if (declaredFields != null && declaredFields.length > 0) {
				for (Field declaredField : declaredFields) {
					//遍历使用了@HmSetter的字段
					if (declaredField.isAnnotationPresent(HmSetter.class)) {
						//获得@HmSetter注解的属性值
						String injectionBeanId = declaredField.getAnnotation(HmSetter.class).value();
						//获得容器中要被注入的Bean实例
						Object injectionBean = beanMaps.get(injectionBeanId);
						//使用反射给字段复制
						//打开访问权限
						declaredField.setAccessible(true);
						declaredField.set(instance, injectionBean);
					}
				}
			}
			//通过字节码对象，获得所有的方法
			Method[] methods = clazz.getMethods();
			//判断方法上是否有@RequestMapping注解
			for (Method method : methods) {
				if(method.isAnnotationPresent(HmRequestMapping.class)){
					//获得注解属性值，即请求资源名称
					String requestPath = method.getAnnotation(HmRequestMapping.class).value();
					//存储到请求资源容器中
					methodMaps.put(requestPath,method);
				}
			}
		}
	}
}
