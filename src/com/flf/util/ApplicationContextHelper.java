package com.flf.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于在非controller里调用service
 * @author 	youxingyang
 * @Date	2017-6-1 上午11:06:51
 */
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext appCtx;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        appCtx = applicationContext;

    }

    public static Object getBean(String beanName) {

        return appCtx.getBean(beanName);

    }

    public static <T> T getBean(Class<T> clz) {

        return (T)appCtx.getBean(clz);

    }
}

