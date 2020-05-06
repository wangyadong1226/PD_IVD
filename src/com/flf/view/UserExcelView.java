package com.flf.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.flf.entity.User;
import com.flf.util.Tools;

public class UserExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		String filename = Tools.date2Str(date, "yyyyMMddHHmmss");
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
		sheet = workbook.createSheet("用户");
		
		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 0, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(0).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<User> userList = (List<User>) model.get("userList");
		int userCount = userList.size();
		for(int i=0; i<userCount; i++){
			User user = userList.get(i);
			String username = user.getUsername();
			cell = getCell(sheet, i+1, 0);
			cell.setCellStyle(contentStyle);
			setText(cell,username);
			
			String loginname = user.getLoginname();
			cell = getCell(sheet, i+1, 1);
			cell.setCellStyle(contentStyle);
			setText(cell,loginname);
			
			String roleName = user.getRole()!=null ? user.getRole().getRoleName() : "";
			cell = getCell(sheet, i+1, 2);
			cell.setCellStyle(contentStyle);
			setText(cell,roleName);
			
			Date lastLogin = user.getLastLogin()!=null ? user.getLastLogin() : null;
			cell = getCell(sheet, i+1, 3);
			cell.setCellStyle(contentStyle);
			setText(cell,Tools.date2Str(lastLogin));
		}
		
	}

}
