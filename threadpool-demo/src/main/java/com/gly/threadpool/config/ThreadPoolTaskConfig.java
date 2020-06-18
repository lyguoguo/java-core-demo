package com.gly.threadpool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "thread.pool.task")
public class ThreadPoolTaskConfig {
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
