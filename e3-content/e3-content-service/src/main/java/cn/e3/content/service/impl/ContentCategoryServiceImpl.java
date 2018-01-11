package cn.e3.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.e3.content.service.ContentCategoryService;
import cn.e3.mapper.TbContentCategoryMapper;
import cn.e3.pojo.TbContentCategory;
import cn.e3.pojo.TbContentCategoryExample;
import cn.e3.pojo.TbContentCategoryExample.Criteria;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.TreeNode;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 需求:根据父id查询子节点 参数:Long parentId 返回值:List<TreeNode> 发布服务
	 */
	public List<TreeNode> findContentCategoryByParentId(Long parentId) {

		// 创建List<TreeNode>树形节点集合,封装树形节点数据
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

		// 创建example对象
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 创建criteria对象,设置查询参数
		Criteria createCriteria = example.createCriteria();
		// 根据父id查询子节点
		createCriteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

		// 循环广告分类集合数据
		for (TbContentCategory tbContentCategory : list) {
			// 创建广告节点对象TreeNode
			TreeNode node = new TreeNode();
			// 设置id
			node.setId(tbContentCategory.getId());
			// 设置text
			node.setText(tbContentCategory.getName());
			// 设置状态
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

			// 把树形节点放入集合对象
			treeNodeList.add(node);
		}

		return treeNodeList;
	}

	// 需求:创建广告分类节点 请求:
	// 参数:Long parentId,String name
	// 返回值:json格式E3mallResult.ok(tbContentCategory)

	public E3mallResult createNode(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setId(null);
		category.setParentId(parentId);
		category.setName(name);
		category.setStatus(1);
		category.setSortOrder(1);
		category.setIsParent(false);
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		contentCategoryMapper.insertSelective(category);
		// 判断新建节点父节点是否是子节点
		// 新建节点parentId是父节点的id,可以根据主键id查询父节点对象
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!tbContentCategory.getIsParent()) {
			tbContentCategory.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(tbContentCategory);
		}
		return E3mallResult.ok(tbContentCategory);
	}

}
