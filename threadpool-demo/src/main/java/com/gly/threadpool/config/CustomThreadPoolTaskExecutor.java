package com.gly.threadpool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class CustomThreadPoolTaskExecutor {

    @Resource
    private ThreadPoolTaskConfig threadPoolConfig;

    @Bean
    public ThreadPoolTaskExecutor build(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolConfig.getCorePoolSize());//核心池大小
        executor.setMaxPoolSize(threadPoolConfig.getMaxPoolSize());//最大线程数
        executor.setQueueCapacity(threadPoolConfig.getQueueCapacity());//队列程度
        executor.setKeepAliveSeconds(threadPoolConfig.getKeepAliveSeconds());//线程空闲时间
        executor.setThreadNamePrefix("accquery-threadpool");//线程前缀名称
        executor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());//配置拒绝策略
        return executor;
    }

    //自定义拒绝策略  如果队列已满 就堵塞继续等待 直到队列有空闲
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
