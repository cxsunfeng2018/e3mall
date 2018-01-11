package cn.e3.manager.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.e3.mapper.TbItemMapper;
import cn.e3.pojo.TbItem;
import cn.e3.pojo.TbItemExample;
import cn.e3.pojo.TbItemExample.Criteria;

@RunWith(SpringJUnit4ClassRunner.class) // junit中加载spring容器的类
@ContextConfiguration("classpath:spring/applicationContext-*.xml") // 设置spring容器位置
public class TestPOIWriter {

	@Autowired
	private TbItemMapper tbItemMapper;

	@Test
	public void HSSF() throws IOException {
		TbItemExample example = new TbItemExample();

		Criteria criteria = example.createCriteria();
		criteria.andCidIsNotNull();
		List<TbItem> ibItemList = tbItemMapper.selectByExample(example);
		// List<OutProductVO> dataList = outProductService.find(inputDate);
		// 从数据库查询数据
		System.out.println(ibItemList.size());
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row nRow = null;
		Cell nCell = null;

		// 行号
		int rowNo = 0;
		// 列号
		int colNo = 1;

		// 声明样式对象和字体对象
		CellStyle nStyle = wb.createCellStyle();
		Font font = wb.createFont();

		// 列宽
		sheet.setColumnWidth(0, 2 * 300);
		sheet.setColumnWidth(1, 26 * 300);
		sheet.setColumnWidth(2, 12 * 300);
		sheet.setColumnWidth(3, 29 * 300);
		sheet.setColumnWidth(4, 10 * 300);
		sheet.setColumnWidth(5, 12 * 300);
		sheet.setColumnWidth(6, 8 * 300);
		sheet.setColumnWidth(7, 10 * 300);
		sheet.setColumnWidth(8, 10 * 300);
		sheet.setColumnWidth(9, 9 * 300);

		// 大标题，合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 9)); // 开始行，结束行，开始列，结束列
		// 合并单元格的内容写在合并前第一个单元格中
		nRow = sheet.createRow(rowNo++);

		nRow.setHeightInPoints(36);

		nCell = nRow.createCell(1);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String inputDate = format.format(date);
		nCell.setCellValue(inputDate.replace("-0", "年").replaceFirst("-", "年") + "月份出货表");
		nCell.setCellStyle(this.bigTitle(wb, nStyle, font));

		String[] title = new String[] { "ID", "标题", "货号", "数量", "工厂", "附件", "工厂交期", "船期", "贸易条款", "贸易条款" };

		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(26.25f);

		// 初始化
		nStyle = wb.createCellStyle();
		font = wb.createFont();

		for (int i = 0; i < title.length; i++) {
			nCell = nRow.createCell(i + 1);
			nCell.setCellValue(title[i]);
			nCell.setCellStyle(this.Title(wb, nStyle, font));

		}
		// 初始化
		nStyle = wb.createCellStyle();
		font = wb.createFont();

		// 换行
		for (int j = 0; j < ibItemList.size(); j++) {
			TbItem item = ibItemList.get(j);
			colNo = 1;

			nRow = sheet.createRow(rowNo++);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getId());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getTitle());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getSellPoint());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getPrice());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getNum());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getImage());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getCid());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getStatus());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getCreated());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(item.getUpdated());
			nCell.setCellStyle(this.Text(wb, nStyle, font));

		}
		nStyle = wb.createCellStyle();
		font = wb.createFont();
		// ibItemList
		OutputStream os = new FileOutputStream(new File("D:\\outProduct1.xls"));

		wb.write(os);
		os.flush();
		os.close();

	}

	// 大标题的样式
	public CellStyle bigTitle(Workbook wb, CellStyle nStyle, Font font) {
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 横向居中
		nStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 纵向居中
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 单元格垂直居中

		nStyle.setFont(font);
		return nStyle;
	}

	// 标题样式
	public CellStyle Title(Workbook wb, CellStyle nStyle, Font font) {
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);

		// 横向居中
		nStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 纵向居中
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 单元格垂直居中

		// 表格线
		nStyle.setBorderTop(CellStyle.BORDER_THICK); // 粗实线
		nStyle.setBorderBottom(CellStyle.BORDER_THIN); // 实线
		nStyle.setBorderLeft(CellStyle.BORDER_THIN); // 比较粗实线
		nStyle.setBorderRight(CellStyle.BORDER_THIN); // 实线

		nStyle.setFont(font);
		return nStyle;
	}

	// 文字样式
	public CellStyle Text(Workbook wb, CellStyle nStyle, Font font) {
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 10);

		// 横向居中
		nStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 纵向居中
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER); // 单元格垂直居中

		// 表格线

		nStyle.setBorderBottom(CellStyle.BORDER_THIN); // 实线
		nStyle.setBorderLeft(CellStyle.BORDER_THIN); // 比较粗实线
		nStyle.setBorderRight(CellStyle.BORDER_THIN); // 实线

		nStyle.setFont(font);
		return nStyle;
	}
}
