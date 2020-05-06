package com.flf.view;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportMultiExcelView {

	/** 
     * 导出无表头excel 多个工作表sheet 
     *  
     * @param fileName 
     * @param titleList 
     * @param list 
     * @param response 
     */  
    public void exportNoHeadExcel(List<Map<String,Object>> sheetList,String fileName, HttpServletResponse response) {  
        SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd");  
        String todayStr = df.format(new Date());  
        OutputStream os = null;  
        try {  
            os = response.getOutputStream();  
            WritableWorkbook wbook = Workbook.createWorkbook(os);  
            for(int i=0;i<sheetList.size();i++){  
                Map<String,Object> map=sheetList.get(i);  
                String sheetName=(String) map.get("fileName");  
                String[] titleList=(String[]) map.get("titleList");  
                List list=(List) map.get("list");  
                String localFileName = sheetName;  
                sheetName = java.net.URLEncoder.encode(sheetName, "UTF-8");// 处理中文文件名的问题  
                sheetName = new String(sheetName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题  
                response.setContentType("application/vnd.ms-excel;");  
                 
                // 开始写入excel  
                // 字段字体  
                jxl.write.WritableFont wfc1 = new jxl.write.WritableFont(  
                        WritableFont.COURIER, 10, WritableFont.NO_BOLD, true);  
                jxl.write.WritableCellFormat wcfFC1 = new jxl.write.WritableCellFormat(  
                        wfc1);  
                wcfFC1.setAlignment(jxl.format.Alignment.CENTRE);  
                wcfFC1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);  
                // 结果字体  
                jxl.write.WritableCellFormat wcfFC2 = new jxl.write.WritableCellFormat();  
                wcfFC2.setAlignment(jxl.format.Alignment.CENTRE);  
                wcfFC2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);  
                // 写sheet名称  
                WritableSheet wsheet = wbook.createSheet(localFileName,i);  
                for (int m = 0; m < titleList.length; m++) {  
                    wsheet.setColumnView(m, 30);  
                }  
                // 加入字段名  
                for (int n = 0; n < titleList.length; n++) {  
                    wsheet.addCell(new jxl.write.Label(n, 0, titleList[n], wcfFC1));  
                }  
                // 写入流中  
                int row = 0;  
                for (int r = 0; r < list.size(); r++) {  
                    Object[] obj = (Object[]) list.get(r);  
                    for (int x = 0; x < titleList.length; x++) {  
                        wsheet.addCell(new jxl.write.Label(x, row + 1,  
                                obj[x] == null ? " " : obj[x].toString(), wcfFC1));  
                    }  
                    row++;  
                    if (row % 60000 == 0) {  
                        row = 0;  
                        // 写sheet名称  
                        wsheet = wbook.createSheet(localFileName, 0);  
                        for (int m = 0; m < titleList.length; m++) {  
                            wsheet.setColumnView(m, 30);  
                        }  
                        // 加入字段名  
                        for (int n = 0; n < titleList.length; n++) {  
                            wsheet.addCell(new jxl.write.Label(n, 0, titleList[n],  
                                    wcfFC1));  
                        }  
                    }  
                }  
            }
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题  
            fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            response.setHeader("Content-disposition", "attachment; filename=\""  
                    + fileName + "_" + todayStr + ".xls\""); 
            wbook.write();  
            wbook.close();  
            os.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (os == null) {  
                System.out.println("os is null");
            } else {  
                try {  
                    os.close();  
                    os = null;  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}
