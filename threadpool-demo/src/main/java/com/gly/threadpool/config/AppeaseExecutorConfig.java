package com.gly.threadpool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 安抚短信下发业务线程池参数配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "cons.executor.appease")
public class AppeaseExecutorConfig {

    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 队列容量
     */
    private Integer queueCapacity;

    /**
     * 空闲线程存活时间
     */
    private Integer keepAliveSeconds;

}
