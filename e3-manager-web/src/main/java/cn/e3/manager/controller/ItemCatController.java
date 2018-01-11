package cn.e3.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3.manager.service.ItemCatService;
import cn.e3.utils.EasyUITreeNode;

@Controller()
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("item/cat/list")
	@ResponseBody
	/**
	 * 返回值： treeNode josn 格式数据： List<EasyUITreeNode> 从前台传来的是id 参数 ：Long parentId
	 */
	public List<EasyUITreeNode> findItemCatList(
			@RequestParam(defaultValue = "0", value = "id") Long parentId) {
		// 调用service方法
		List<EasyUITreeNode> itemCatList = itemCatService.findItemCatList(parentId);
		return itemCatList;
	}

}
