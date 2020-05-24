package com.gly.threadpool;

import com.gly.threadpool.config.AppeaseExecutorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池构建工厂对象
 */
@Configuration
public class ThreadPoolTaskExecutorFactory {

    @Autowired
    private AppeaseExecutorConfig appeaseExecutorConfig;

    /**
     * 业务相关线程池，建议业务专用，参数配置跟业务强相关
     *      此线程池为安抚短信下发专用线程池
     * @return
     */
    @Bean
    public AppeaseThreadPoolTaskExecutor build(){
        AppeaseThreadPoolTaskExecutor threadPoolTaskExecutor = new AppeaseThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(appeaseExecutorConfig.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(appeaseExecutorConfig.getMaxPoolSize());
        threadPoolTaskExecutor.setKeepAliveSeconds(appeaseExecutorConfig.getKeepAliveSeconds());
        threadPoolTaskExecutor.setQueueCapacity(appeaseExecutorConfig.getQueueCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix("threadpool-appeasemsg-push");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return threadPoolTaskExecutor;

    }

    public class AppeaseThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
    }
}
