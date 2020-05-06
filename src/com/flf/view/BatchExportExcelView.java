package com.flf.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.RegionUtil;

/** 
 * 导出Excel公共方法 
 * @version 1.0 
 *  
 * @author yanliye
 * 
 */  
public class BatchExportExcelView {
	
	//显示的导出表的标题  
    private String title;  
    //导出表的列名  
    private String[] rowName ;  
      
    private List<Object[]>  dataList = new ArrayList<Object[]>();  
      
    HttpServletResponse  response;  
      
    //构造方法，传入要导出的数据  
    public BatchExportExcelView(String title,String[] rowName,List<Object[]>  dataList,HttpServletResponse response){  
        this.dataList = dataList;  
        this.rowName = rowName;  
        this.title = title;  
        this.response = response;
    }  
    /* 
     * 导出数据 
     * */  
    public void export(String fileName) throws Exception{  
    	OutputStream out = null;  
        try{  
            HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象  
            HSSFSheet sheet = workbook.createSheet(title);                  // 创建工作表  
              
            // 产生表格标题行  
            HSSFRow rowm = sheet.createRow(0);  
            HSSFCell cellTiltle = rowm.createCell(0);  
              
            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】  
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象  
            HSSFCellStyle style = this.getStyle(workbook);                  //单元格样式对象  
            // 合并单元格  
            CellRangeAddress cra =new CellRangeAddress(0, 1, 0, (rowName.length-1)); // 起始行, 终止行, 起始列, 终止列  
            sheet.addMergedRegion(cra);  
            // 使用RegionUtil类为合并后的单元格添加边框 
            RegionUtil.setBorderBottom(1, cra, sheet,workbook); // 下边框  
            RegionUtil.setBorderLeft(1, cra, sheet,workbook); // 左边框  
            RegionUtil.setBorderRight(1, cra, sheet,workbook); // 有边框  
            RegionUtil.setBorderTop(1, cra, sheet,workbook); // 上边框  
            cellTiltle.setCellStyle(columnTopStyle);  
            cellTiltle.setCellValue(title); 
              
            // 定义所需列数  
            int columnNum = rowName.length;  
            HSSFRow rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)  
            HSSFRow rowRowName2 = sheet.createRow(3);   
            // 将列头设置到sheet的单元格中  
            for(int n=0;n<columnNum;n++){
				if (n==0) {
					sheet.addMergedRegion(new CellRangeAddress(2, 2,0, 7));
					HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); 
					HSSFRichTextString  text = new HSSFRichTextString("样本信息"); //设置列头单元格的数据类型 
					cellRowName.setCellValue(text);                                 //设置列头单元格的值  
					cellRowName.setCellStyle(columnTopStyle); 
				}else if (n==8) {
					sheet.addMergedRegion(new CellRangeAddress(2, 2,8, 18));
					HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); 
					HSSFRichTextString  text = new HSSFRichTextString("临床信息"); //设置列头单元格的数据类型 
					cellRowName.setCellValue(text);                                 //设置列头单元格的值  
					cellRowName.setCellStyle(columnTopStyle); 
				}else if (n==19) {
					sheet.addMergedRegion(new CellRangeAddress(2, 2,19, 30));
					HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); 
					HSSFRichTextString  text = new HSSFRichTextString("质控信息"); //设置列头单元格的数据类型 
					cellRowName.setCellValue(text);                                 //设置列头单元格的值  
					cellRowName.setCellStyle(columnTopStyle); 
				}else if (n==31) {
					sheet.addMergedRegion(new CellRangeAddress(2, 2,31, 36));
					HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); 
					HSSFRichTextString  text = new HSSFRichTextString("目标染色体检测值"); //设置列头单元格的数据类型 
					cellRowName.setCellValue(text);                                 //设置列头单元格的值  
					cellRowName.setCellStyle(columnTopStyle); 
				}else if (n==37) {
					sheet.addMergedRegion(new CellRangeAddress(2, 2,37, 55));
					HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); 
					HSSFRichTextString  text = new HSSFRichTextString("其他染色体检测值"); //设置列头单元格的数据类型 
					cellRowName.setCellValue(text);                                 //设置列头单元格的值  
					cellRowName.setCellStyle(columnTopStyle); 
				}
                HSSFCell  cellRowName = rowRowName2.createCell(n);               //创建列头对应个数的单元格  
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型 
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);  
                cellRowName.setCellValue(text);                                 //设置列头单元格的值  
                cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式  
            }  
              
            //将查询出的数据设置到sheet对应的单元格中  
            for(int i=0;i<dataList.size();i++){  
                  
                Object[] obj = dataList.get(i);//遍历每个对象  
                HSSFRow row = sheet.createRow(i+4);//创建所需的行数  
                  
                for(int j=0; j<obj.length; j++){  
                    HSSFCell  cell = null;   //设置单元格的数据类型  
                    if(j == 0){  
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);  
                        cell.setCellValue(i+1);   
                    }else{  
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);  
                        if(!"".equals(obj[j]) && obj[j] != null){  
                            cell.setCellValue(obj[j].toString());                       //设置单元格的值  
                        }else{
                        	cell.setCellValue("");
                        }
                    }  
                    cell.setCellStyle(style);                                   //设置单元格样式  
                }  
            }  
            //让列宽随着导出的列长自动适应  
            for (int colNum = 0; colNum < columnNum; colNum++) {  

            	int colWidth = sheet.getColumnWidth(colNum)*2;
                if(colWidth<255*256){
                	if(colNum == 0){  
                      sheet.setColumnWidth(colNum, (colWidth-2) * 256< 3000 ? (colWidth-2) * 256: colWidth);  
                  }else{  
                      sheet.setColumnWidth(colNum, (colWidth+4) * 256< 3000 ? (colWidth+4) * 256 :colWidth);  
                  } 
                }else{
                    sheet.setColumnWidth(colNum,6000 );
                }
            }  
              
            if(workbook !=null){  
                try  
                {  
                    //String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                	if(null!=fileName && !"".equals(fileName)){
                		fileName = fileName + ".xls";
                	}else{
                		fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
                	}
                    String headStr = "attachment; filename=\"" + fileName + "\"";  
//                    response = ServletActionContext.getResponse(); 
                    response.setContentType("APPLICATION/OCTET-STREAM");  
                    response.setHeader("Content-Disposition", headStr);  
                    out = response.getOutputStream();
                    workbook.write(out); 
                    out.flush();
            		out.close();
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
  
        }catch(Exception e){  
            e.printStackTrace();  
        } finally {
        	if (out != null) {
        		out.flush();
        		out.close();
			}
		} 
          
    }  
      
    /*  
     * 列头单元格样式 
     */      
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {  
          
          // 设置字体  
          HSSFFont font = workbook.createFont();  
          //设置字体大小  
          font.setFontHeightInPoints((short)11);  
          //字体加粗  
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          HSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            
          return style;  
            
    }  
      
    /*   
     * 列数据信息单元格样式 
     */    
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {  
          // 设置字体  
          HSSFFont font = workbook.createFont();  
          //设置字体大小  
          //font.setFontHeightInPoints((short)10);  
          //字体加粗  
          //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          HSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
           
          return style;  
      
    }  

}
