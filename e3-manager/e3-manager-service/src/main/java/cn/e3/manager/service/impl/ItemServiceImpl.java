package cn.e3.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3.manager.service.ItemService;
import cn.e3.mapper.TbItemDescMapper;
import cn.e3.mapper.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemDesc;
import cn.e3.pojo.TbItemDescExample;
import cn.e3.pojo.TbItemExample;
import cn.e3.utils.E3mallResult;
import cn.e3.utils.EasyUIPageBean;
import cn.e3.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	// 注入商品mapper接口代理对象
	@Autowired
	private TbItemMapper itemMapper;

	/**
	 * 需求:根据商品id查询商品数据 参数:Long itemId 返回值:TbItem
	 */
	public TbItem findItemById(Long itemId) {
		// 根据商品id查询商品数据
		TbItem item = itemMapper.selectByPrimaryKey(itemId);

		return item;
	}

	/**
	 * 需求:查询商品数据,进行 分页展示 参数:Integer page,Integer rows 返回值:EasyUIPageBean
	 */
	public EasyUIPageBean findItemListByPage(Integer page, Integer rows) {
		// 创建example对象
		TbItemExample example = new TbItemExample();

		// 在查询之前,进行分页设置 用这个对象获取分页信息
		PageHelper.startPage(page, rows);

		// 设置完分页查询后,pageHeler距离最近一条sql语句将会被拦截器拦截,自动被分页
		// list(page{total:2222},List<item>)
		// 执行查询
		List<TbItem> list = itemMapper.selectByExample(example);

		// 创建pageInfo对象,获取分页信息 pageInfo里面有分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);

		// 创建分页包装类对象,封装分页数据
		EasyUIPageBean pageBean = new EasyUIPageBean();
		// 设置总记录
		pageBean.setRows(list);
		// 设置总记录数
		pageBean.setTotal(pageInfo.getTotal());

		return pageBean;
	}

	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public E3mallResult saveItem(TbItem item, TbItemDesc desc) {
		// 补全属性
		// 设置Id
		// 使用工具类生成ID
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 补全时间类型
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 保存商品
		itemMapper.insert(item);
		// 补全商品描述信息属性
		desc.setItemId(itemId);
		desc.setCreated(date);
		desc.setUpdated(date);
		itemDescMapper.insert(desc);
		return E3mallResult.ok();
	}

}
