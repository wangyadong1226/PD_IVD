package com.flf.util;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tools {
	
	/**
	 * 比较日期大小
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	 public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	public static String formatDate(Date date, String pattern) {
		if(pattern == null || pattern.equals(""))
			pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String time = format.format(date);
		return time;
	}
	
	/**
	 * 获取请求路径
	 * @param request
	 * @return
	 */
	public static String getURL(HttpServletRequest request) {
		String url = request.getScheme()+"://"; //请求协议 http 或 https    
		url += request.getHeader("host");  // 请求服务器    
		url += request.getRequestURI();     // 工程名      
		if(request.getQueryString()!=null) //判断请求参数是否为空  
			url += "?"+request.getQueryString();   // 参数
		return url;
	}
	
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	
	/**
	 * 字符串转换为字符串数组
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @param str	字符串
	 * @return
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date){
		return date2Str(date,"yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * @param date
	 * @return new SimpleDateFormat
	 */
	public static Date str2Date(String date){
		if(notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}
	
	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}
	
	/**
	 * 判断值是否为空或
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isEmptyOrNull(Object... values) {
		if (values == null || values.length == 0)
			return true;
		return false;
	}
	
	/** 
     * 获取链接的后缀名 
     * @return 
     */  
    public static String parseSuffix(String url) {  
    	Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        Matcher matcher = pattern.matcher(url);
        String[] spUrl = url.toString().split("/");  
        int len = spUrl.length;  
        String endUrl = spUrl[len - 1];  
        if(matcher.find()) {  
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];  
        }  
        return endUrl.split("\\.")[1];  
    }
    
    /** 
     * 获取链接的后缀名 
     * @return 
     */  
    public static String parseURLName(String url) {
    	Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        Matcher matcher = pattern.matcher(url);
        String[] spUrl = url.toString().split("/");  
        int len = spUrl.length;  
        String endUrl = spUrl[len - 1];  
        if(matcher.find()) {  
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[0];
        }  
        return endUrl.split("\\.")[0];
    }
    
    /**
     * 公式运算
     * @param expression
     * @param params
     * @return
     * @throws Exception
     */
    public static Object buildExpression(String expression,Map<String,Object> params) throws Exception {
    	ScriptEngineManager manager = new ScriptEngineManager(); 
        ScriptEngine engine = manager.getEngineByName("js");
        if(params != null && params.size() > 0){
        	for(String key : params.keySet()){
        		engine.put(key, params.get(key));
        	}
        }
      return engine.eval(expression);
    }
    
    /**
     * 是否为数字
     * @param str
     * @return
     */
    public static boolean isNum(String str){
    	return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
    
    /**
     * 获取Spring配置文件信息
     * @return
     * @throws Exception
     */
    public static ApplicationContext getApplicationContext() throws Exception {
    	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/spring/spring-*.xml");
    	return context;
    }
    
    public static String getFileKB(long byteFile){  
        if(byteFile==0)  
           return "0KB";  
        long kb=1024;  
        return ""+byteFile/kb+"KB";  
    }
	 
    public static String getFileMB(long byteFile){  
        if(byteFile==0)  
           return "0MB";  
        long mb=1024*1024;  
        return ""+byteFile/mb+"MB";  
    }  
    
    /**
     * 删除文件
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(String path) throws Exception {
    	File file = new File(path);
    	if(file.exists()){
    		return file.delete();
    	}
    	return false;
    }
    
    /**
	 * 获取根目录绝对路径
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
	
	public static void main(String[] args) {
		int i = compareDate("2016-11-12 15:21", "2015-05-11 09:59");
		System.out.println(i);
	}
}
