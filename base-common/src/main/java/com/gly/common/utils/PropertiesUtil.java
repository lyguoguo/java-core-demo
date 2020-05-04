/*
 * Copyright 2011-2015 10jqka.com.cn All right reserved. This software is the confidential and proprietary information
 * of 10jqka.com.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with 10jqka.com.cn.
 */
package com.gly.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 类PropertiesUtil.java的实现描述：读取配置文件Util
 *
 * @author
 */
public class PropertiesUtil {

    /**
     * 日志服务类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Map<String, Properties> cache = new HashMap<String, Properties>();

    /**
     * 加载properties文件， 默认读取缓存
     *
     * @param filePath
     *          文件路径,相对于classpath路径
     * @return {@code Properties}, 如果filePath为空返回null
     */
    public static Properties loadProperties(String filePath) {
        return loadProperties(filePath, true);
    }

    /**
     * 加载properties文件
     *
     * @param filePath
     *          文件路径,相对于classpath路径
     * @param isCache
     *          {@code true} 读取缓存, 否则实时加载
     * @return {@code Properties}, 如果filePath为空返回null
     */
    public static Properties loadProperties(String filePath, boolean isCache) {
        if (isCache) {
            Properties props = cache.get(filePath);
            if (null != props) {
                return props;
            }
        }

        if (!StringUtils.isBlank(filePath)) {
            Properties props = new Properties();
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            try {
                if (inputStream == null) {
                    return null;
                }
                props.load(inputStream);
                cache.put(filePath, props);
                return props;
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return null;
    }
}
