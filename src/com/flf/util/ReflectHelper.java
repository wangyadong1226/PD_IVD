package com.flf.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Administrator
 *	反射工具
 */
public class ReflectHelper {
	
	/**
	 * 根据类字段名称获取值,可使用.的方式寻址(如a.b.c)
	 * @param source
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object source, String fieldName) throws Exception {
		Object result = null;
		if(fieldName != null && !fieldName.equals("")){
			if(fieldName.contains(".")){
				if((fieldName.indexOf(".") + 1) < fieldName.length()){
					String nextFieldName = fieldName.substring(fieldName.indexOf(".") + 1);
					String fn = fieldName.substring(0,fieldName.indexOf("."));
					Object obj = ReflectHelper.getValueByFieldName(source, fn);
					result = getFieldValue(obj,nextFieldName);
				}
			}else{
				result = ReflectHelper.getValueByFieldName(source, fieldName);	
			}
		}
		return result;
	}
	
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if(field!=null){
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = null;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Field[] fields = getAllField(obj.getClass());
			for(Field fi : fields){
				if(fi.getName().equals(fieldName)){
					field = fi;
					break;
				}		
			}
			if(fields == null || fields.length <= 0){
				throw new NoSuchFieldException(obj.getClass().getName() + "，包括其父类中不包含" + fieldName + "属性");
			}
		}
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
	
	/**
     * 获取类clazz的所有Field，包括其父类的Field，如果重名，以子类Field为准。
     * @param clazz
     * @return Field数组
     */
    public static Field[] getAllField(Class<?> clazz) {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        Field[] dFields = clazz.getDeclaredFields();
        if (null != dFields && dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }
 
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            Field[] superFields = getAllField(superClass);
            if (null != superFields && superFields.length > 0) {
                for(Field field:superFields){
                    if(!isContain(fieldList, field)){
                        fieldList.add(field);
                    }
                }
            }
        }
        Field[] result=new Field[fieldList.size()];
        fieldList.toArray(result);
        return result;
    }
     
    /**检测Field List中是否已经包含了目标field
     * @param fieldList
     * @param field 带检测field
     * @return
     */
    public static boolean isContain(ArrayList<Field> fieldList,Field field){
        for(Field temp:fieldList){
            if(temp.getName().equals(field.getName())){
                return true;
            }
        }
        return false;
    }	
}
