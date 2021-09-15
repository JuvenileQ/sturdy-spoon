package com.itheima.mm.controller;

import lombok.Data;

/**
 * @author: QinYanJin
 * @date: 2021/9/7
 */
@Data
public class Product {
	private int pid;
	private String pname;
	private Double price;
	private String category_id;

	//省略 getter和setter方法
}
