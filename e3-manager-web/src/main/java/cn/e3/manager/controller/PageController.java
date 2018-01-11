package cn.e3.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 需求:页面跳转
	 * 请求:
	 * 首页:localhost:8081/index
	 * 商品添加:localhost:8081/item-add
	 * 商品列表:localhost:8081/item-list
	 * 商品编辑:localhost:8081/item-edit
	 * 特点:
	 * 请求名称和页面名称相同
	 * 设计:
	 * 可以把请求作为参数,返回参数即可是否页面名称逻辑视图返回,即可实现一个请求映射匹配所有的请求
	 */
	@RequestMapping("{page}")
	public String showPage(@PathVariable String page){
		return page;
	}

}
