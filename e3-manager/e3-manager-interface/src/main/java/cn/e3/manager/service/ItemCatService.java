package cn.e3.manager.service;

import java.util.List;

import cn.e3.utils.EasyUITreeNode;

public interface ItemCatService {
	
	/**
	 * 返回值： treeNode josn 格式数据： List<EasyUITreeNode>
	 * 参数 ：Long parentId
	 */
	public  List<EasyUITreeNode> findItemCatList(Long parentId);
}
