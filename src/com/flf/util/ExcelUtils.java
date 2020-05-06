package com.flf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel工具类(POI实现)
 * 2015-12-08
 *
 */
public class ExcelUtils {

	private HSSFWorkbook workbook = null;//Excel工作类
	
	private InputStream is = null;//输入流
	
	private List<HSSFSheet> sheetList = new ArrayList<HSSFSheet>();

	public ExcelUtils() throws Exception {
		workbook = new HSSFWorkbook();
	}
	
	private HSSFCellStyle cellStyle2 = null;
	
	/**
	 * 构造函数
	 * @param filename excel文件名
	 * @throws IOException
	 */
	public ExcelUtils(String filename) throws IOException {
		is = new FileInputStream(filename);
		workbook = new HSSFWorkbook(is);
		for(int i=0;i<workbook.getNumberOfSheets();i++){
			sheetList.add(workbook.getSheetAt(i));
		}
		
		cellStyle2 = workbook.createCellStyle();	
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)11);//设置字体大小		
		cellStyle2.setFont(font);
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
	}
	
	public void createSheet(String sheetName) throws Exception {
		sheetList.add(workbook.createSheet(sheetName));
	}
	
	public void setSheetName(int sheetnum,String name) throws Exception {
		workbook.setSheetName(sheetnum, name);
	}
	
	public void frozen(int sheetnum, int colSplit, int rowSplit) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		if(sheet != null){
			sheet.createFreezePane(colSplit,rowSplit);
		}
	}
	
	/**
	 * 冻结列
	 * @param colSplit
	 * @param rowSplit
	 * @param leftmostColumn
	 * @param topRow
	 * @throws Exception
	 */
	public void frozen(int sheetnum, int colSplit, int rowSplit, int leftmostColumn, int topRow) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		if(sheet != null){
			sheet.createFreezePane(colSplit,rowSplit,leftmostColumn,topRow);
		}
	}
	
	/**
	 * 合并单元格
	 * @param rowFrom 起始行
	 * @param colFrom 起始列
	 * @param rowTo 终止行
	 * @param colTo 终止列
	 * @throws Exception
	 */
	public void mergedCell(int sheetnum,int rowFrom, int colFrom, int rowTo, int colTo) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		if(sheet != null){
			// 四个参数分别是：起始行，起始列，结束行，结束列     
            sheet.addMergedRegion(new Region(rowFrom, (short)colFrom, rowTo,     
                    (short)colTo)); 
		}
	}
	
	public void autoSizeColumn(int sheetnum, int colnum){
		HSSFSheet sheet = sheetList.get(sheetnum);
		if(sheet != null){
			sheet.autoSizeColumn((short)colnum);
		}
	}
	
	public void insertValue(int sheetnum,int rownum,int colnum,Object value, boolean border) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
				
		if(value != null){
			if(value instanceof String){
				HSSFRichTextString htr = new HSSFRichTextString(value.toString());
				cell.setCellValue(htr);	
			}else if(Tools.isNum(value.toString())){
				cell.setCellValue(Double.parseDouble(value.toString()));
			}else if(value instanceof Boolean){
				cell.setCellValue(Boolean.parseBoolean(value.toString()));
			}else if(value instanceof Date){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
				cell.setCellValue(sdf.parse(value.toString()));
			}else if(value instanceof Calendar){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =sdf.parse(value.toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				cell.setCellValue(calendar);
			}
			sheet.createFreezePane(2,3,2,3);
		}
//		
//		if(border){
//			HSSFCellStyle cellStyle = workbook.createCellStyle();
//			
//			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
//			
//			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框					
//			
//			cell.setCellStyle(cellStyle);
//		}
	}
	
	
	
	public void insertValue(int sheetnum,int rownum,int colnum,Object value, ExcelStyle style) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
				
		if(value != null){
			if(value instanceof String){
				HSSFRichTextString htr = new HSSFRichTextString(value.toString());
				cell.setCellValue(htr);	
			}else if(Tools.isNum(value.toString())){
				cell.setCellValue(Double.parseDouble(value.toString()));
			}else if(value instanceof Boolean){
				cell.setCellValue(Boolean.parseBoolean(value.toString()));
			}else if(value instanceof Date){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
				cell.setCellValue(sdf.parse(value.toString()));
			}else if(value instanceof Calendar){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =sdf.parse(value.toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				cell.setCellValue(calendar);
			}
		}
		
		if(style != null){
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
			
			if(style.isBorder()){
				cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
				cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
				cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
				cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框					
				
				HSSFFont font = workbook.createFont();
				font.setFontName("宋体");
				font.setFontHeightInPoints((short)11);//设置字体大小
				
				cellStyle.setFont(font);
			}
			
			cell.setCellStyle(cellStyle);
		}		
	}
	
	public void setFillForegroundColor(int sheetnum,int rowFrom,int rowTo,int colFrom,int colTo,short color) throws Exception {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(color);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFSheet sheet = sheetList.get(sheetnum);
		for(int rownum=rowFrom;rownum<rowTo;rownum++){
			for(int colnum=colFrom;colnum<colTo;colnum++){
				HSSFRow row = sheet.getRow(rownum);
				HSSFCell cell = null;
				if(row != null){
					cell = row.getCell((short)colnum);
					if(cell == null)
						cell = row.createCell((short)colnum);
				}else{
					row = sheet.createRow(rownum);
					cell = row.createCell((short)colnum);
				}
				cell.setCellStyle(cellStyle);
			}
		}
	}

	public void setBorder(int sheetnum,int rowFrom,int rowTo,int colFrom,int colTo) throws Exception {
		HSSFCellStyle cellStyle = getBorderStyle();	
		HSSFSheet sheet = sheetList.get(sheetnum);
		for(int rownum=rowFrom;rownum<rowTo;rownum++){
			for(int colnum=colFrom;colnum<colTo;colnum++){
				HSSFRow row = sheet.getRow(rownum);
				HSSFCell cell = null;
				if(row != null){
					cell = row.getCell((short)colnum);
					if(cell == null)
						cell = row.createCell((short)colnum);
				}else{
					row = sheet.createRow(rownum);
					cell = row.createCell((short)colnum);
				}
				cell.setCellStyle(cellStyle);
			}
		}		
	}
	
	/**
	 * 获取边框样式
	 * @return
	 * @throws Exception
	 */
	protected HSSFCellStyle getBorderStyle() throws Exception {		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		return cellStyle;
	}
	
	/**
	 * 获取总行数
	 * @param sheetnum 工作薄索引
	 * @return
	 * @throws Exception
	 */
	public int getRowNum(int sheetnum) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		return sheet.getLastRowNum();
	}
	
	/**
	 * 设置边框(重载)
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @throws Exception
	 */
	public void setBorder(int sheetnum,int rownum,int colnum) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		cell.setCellStyle(cellStyle);
	}
	
	/**
	 * 设置字体
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @param fontName 字体名
	 * @param fontSize 字体大小
	 * @param bold 粗体
	 * @throws Exception
	 */
	public void setFont(int sheetnum,int rownum,int colnum, String fontName, int fontSize, boolean bold) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);		
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		
		HSSFFont font = workbook.createFont();
		font.setFontName(fontName);
		if(bold)
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		font.setFontHeightInPoints((short)fontSize);//设置字体大小
		
		cellStyle.setFont(font);
		
		cell.setCellStyle(cellStyle);
	}
	
	/**
	 * 根据列号拼列名字符串
	 * @param str 字符串
	 * @param col 列号
	 */
	private void setExcelColumnName(StringBuilder str, int col){
	    int tmp = col / 26;
	    if(tmp > 26){
	        setExcelColumnName(str, tmp - 1);
	    }else if(tmp > 0){
	        str.append((char)(tmp + 64));
	    }
	    str.append((char)(col % 26 + 65));
	}
	 
	/**
	 * 根据列号返回名称框(左上角 如:A1,A2等)
	 * @param col
	 * @return
	 */
	public String getExcelColumnName(int col) {
	    StringBuilder str = new StringBuilder(2);
	    setExcelColumnName(str, col);
	    return str.toString();
	}
	
	public static boolean isRowEmpty(Row row) {
	   for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	       Cell cell = row.getCell(c);
	       if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	           return false;
	   }
	   return true;
	} 
	
	/**
	 * 设置函数
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @param value 函数表达式
	 * @throws Exception
	 */
	public void setFunction(int sheetnum,int rownum,int colnum,String value) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
		cell.setCellFormula(value);
	}
	
	/**
	 * 获取excel单元格值
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @return
	 * @throws Exception
	 */
	public String getValue(int sheetnum, int rownum,int colnum) throws Exception {
		String value = null;
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		if(row != null){
			HSSFCell cell = row.getCell((short)colnum);
			value = getCellFormatValue(cell);
		}
		return value;
	}
	
	/**
     * 根据HSSFCell类型设置数据(供内部使用)
     * @param cell 单元格对象
     * @return
     */
    protected String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
	
	/**
	 * 设置单元格值
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @param value 值
	 * @param border 边框
	 * @throws Exception
	 */
	public void setValue(int sheetnum,int rownum,int colnum,Object value, boolean border) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
				
		if(value != null){
			if(value instanceof String){
				HSSFRichTextString htr = new HSSFRichTextString(value.toString());
				cell.setCellValue(htr);	
			}else if(Tools.isNum(value.toString())){
				cell.setCellValue(Double.parseDouble(value.toString()));
			}else if(value instanceof Boolean){
				cell.setCellValue(Boolean.parseBoolean(value.toString()));
			}else if(value instanceof Date){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
				cell.setCellValue(sdf.parse(value.toString()));
			}else if(value instanceof Calendar){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =sdf.parse(value.toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				cell.setCellValue(calendar);
			}
		}
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		if(border){
			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框					
			
			HSSFFont font = workbook.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short)11);//设置字体大小
			
			cellStyle.setFont(font);
		}
		cell.setCellStyle(cellStyle);
	}
	
	
	
	/**
	 * 设置单元格值(重载)
	 * @param sheetnum 工作薄索引
	 * @param rownum 行号
	 * @param colnum 列号
	 * @param value 值
	 * @throws Exception
	 */
	public void setValue(int sheetnum,int rownum,int colnum,Object value) throws Exception {
		HSSFSheet sheet = sheetList.get(sheetnum);		
		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if(row != null){
			cell = row.getCell((short)colnum);
			if(cell == null)
				cell = row.createCell((short)colnum);
		}else{
			row = sheet.createRow(rownum);
			cell = row.createCell((short)colnum);
		}
		
		if(value != null){
			if(value instanceof String){
				HSSFRichTextString htr = new HSSFRichTextString(value.toString());
				cell.setCellValue(htr);	
			}else if(Tools.isNum(value.toString())){
				cell.setCellValue(Double.parseDouble(value.toString()));
			}else if(value instanceof Boolean){
				cell.setCellValue(Boolean.parseBoolean(value.toString()));
			}else if(value instanceof Date){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
				cell.setCellValue(sdf.parse(value.toString()));
			}else if(value instanceof Calendar){
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date =sdf.parse(value.toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				cell.setCellValue(calendar);
			}
		}
		
//		HSSFCellStyle cellStyle = workbook.createCellStyle();	
//		HSSFFont font = workbook.createFont();
//		font.setFontName("宋体");
//		font.setFontHeightInPoints((short)11);//设置字体大小		
//		cellStyle.setFont(font);
//		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
//		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		
		cell.setCellStyle(cellStyle2);
	}
	
	/**
	 * 写入流
	 * @param out
	 * @throws Exception
	 */
	public void writer(OutputStream out) throws Exception {
		try{
			workbook.write(out);
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			if(out != null){
				out.flush();   
			    out.close();
			}
		}
	}
		
	public static class ExcelColor {
		public static short AQUA = HSSFColor.AQUA.index;
		public static short AUTOMATIC = HSSFColor.AUTOMATIC.index;
		public static short BLUE = HSSFColor.BLUE.index;
		public static short BLUE_GREY = HSSFColor.BLUE_GREY.index;
		public static short BRIGHT_GREEN = HSSFColor.BRIGHT_GREEN.index;
		public static short BROWN = HSSFColor.BROWN.index;
		public static short CORAL = HSSFColor.CORAL.index;
		public static short CORNFLOWER_BLUE = HSSFColor.CORNFLOWER_BLUE.index;
		public static short BLACK = HSSFColor.BLACK.index;
		public static short DARK_BLUE = HSSFColor.DARK_BLUE.index;
		public static short DARK_GREEN = HSSFColor.DARK_GREEN.index;
		public static short DARK_RED = HSSFColor.DARK_RED.index;
		public static short DARK_TEAL = HSSFColor.DARK_TEAL.index;
		public static short DARK_YELLOW = HSSFColor.DARK_YELLOW.index;
		public static short GOLD = HSSFColor.GOLD.index;
		public static short RED = HSSFColor.RED.index;
		public static short GREEN = HSSFColor.GREEN.index;
		public static short GREY_25_PERCENT = HSSFColor.GREY_25_PERCENT.index;
		public static short GREY_40_PERCENT = HSSFColor.GREY_40_PERCENT.index;
		public static short GREY_50_PERCENT = HSSFColor.GREY_50_PERCENT.index;
		public static short GREY_80_PERCENT = HSSFColor.GREY_80_PERCENT.index;
		public static short INDIGO = HSSFColor.INDIGO.index;
		public static short LEMON_CHIFFON = HSSFColor.LEMON_CHIFFON.index;
		public static short LIGHT_BLUE = HSSFColor.LIGHT_BLUE.index;
		public static short LIGHT_CORNFLOWER_BLUE = HSSFColor.LIGHT_CORNFLOWER_BLUE.index;
		public static short LAVENDER = HSSFColor.LAVENDER.index;
		public static short LIGHT_GREEN = HSSFColor.LIGHT_GREEN.index;
		public static short LIGHT_ORANGE = HSSFColor.LIGHT_ORANGE.index;
		public static short LIGHT_YELLOW = HSSFColor.LIGHT_YELLOW.index;
	}
	
	public static void main(String[] args) {
		
	}
}
