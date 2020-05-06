package com.flf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImportCsv {
	private InputStreamReader fr = null;  
    private BufferedReader br = null;  
  
    public ImportCsv(InputStream inputStream) throws IOException {  
        fr = new InputStreamReader(inputStream,"gb2312"); 
    }  
  
    /** 
     * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中 
     */  
    public List<Map<Integer,String>> readCSVFile() throws IOException {  
    	Log log = LogFactory.getLog( this .getClass());
        br = new BufferedReader(fr);  
        String rec = null;// 一行  
        String str;// 一个单元格  
        List<Map<Integer,String>> listFile = new ArrayList<Map<Integer,String>>();  
        try {  
            // 读取一行  
            while ((rec = br.readLine()) != null) {  
            	Map<Integer,String> cells = new HashMap<Integer, String>();// 每行记录一个list 
            	String[] stt = rec.split(",");
            	for(int i =0;i<stt.length;i++){
            		cells.put(i, stt[i]);  
            		log.info("我是每个字段："+stt[i]);
            	}
                listFile.add(cells);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fr != null) {  
                fr.close();  
            }  
            if (br != null) {  
                br.close();  
            }  
        }  
        return listFile;  
}
}
