package com.gly.common.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Spring容器工具类，用于获取Spring的bean
 * Created by zhouheng
 * 2017/8/25.
 */
public class SpringContextUtils implements ApplicationContextAware {

    /**
     * Spring 容器
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext对象
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     *
     * @param id bean的ID
     * @return Bean实例
     */
    public static Object getBeanById(String id) {
        try {
            return applicationContext.getBean(id);
        } catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    /**
     * 根据bean的class来查找对象
     *
     * @param c   bean 类型
     * @param <T> 类型泛型
     * @return Bean实例
     */
    public static <T> T getBeanByClass(Class<T> c) {
        return applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @param c bean 类型
     * @return Bean实例集合
     */
    public static Map getBeansByClass(Class c) {
        return applicationContext.getBeansOfType(c);
    }

}
