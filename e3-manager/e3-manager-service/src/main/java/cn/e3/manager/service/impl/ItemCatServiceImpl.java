package cn.e3.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3.manager.service.ItemCatService;
import cn.e3.mapper.TbItemCatMapper;
import cn.e3.pojo.TbItemCat;
import cn.e3.pojo.TbItemCatExample;
import cn.e3.pojo.TbItemCatExample.Criteria;
import cn.e3.utils.EasyUITreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	/**
	 * 返回值： treeNode josn 格式数据： List<EasyUITreeNode>
	 * 参数 ：Long parentId
	 */
	public List<EasyUITreeNode> findItemCatList(Long parentId) {

		// 创建一个List集合 存放EasyUITreeNode
		List<EasyUITreeNode> treeList = new ArrayList<EasyUITreeNode>();
		// 创建example对象，类似于hibernate的qbc查询
		TbItemCatExample example = new TbItemCatExample();

		Criteria criteria = example.createCriteria();
		// 设置查询参数
		criteria.andParentIdEqualTo(parentId);
		// 查询数据
		List<TbItemCat> list = itemCatMapper.selectByExample(example);

		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			// 设置节点id
			treeNode.setId(tbItemCat.getId());
			// 设置节点名字
			treeNode.setText(tbItemCat.getName());
			// 设置节点状态
			treeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");

			treeList.add(treeNode);
		}
		return treeList;
	}

}
