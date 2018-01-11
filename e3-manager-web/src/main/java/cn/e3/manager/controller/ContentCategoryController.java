package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.content.service.ContentCategoryService;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.EasyUITreeNode;
import cn.e3.utils.TreeNode;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	// 根据内容分类父Id，查询内容分类树形菜单 给id设置默认值
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> findContentCategoryByParentId(
			@RequestParam(defaultValue = "0", value = "id") Long parentId) {
		List<TreeNode> list = contentCategoryService.findContentCategoryByParentId(parentId);
		return list;
	}
	
	// 需求:创建广告分类节点
	// 请求:/content/category/create
	// 参数:Long parentId,String name
	// 返回值:json格式E3mallResult.ok(tbContentCategory)
	 
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3mallResult createNode(Long parentId,String name){
		//调用远程service服务方法,创建节点
		E3mallResult result = contentCategoryService.createNode(parentId, name);
		return result;
	}
}
