package com.gly.threadpool.moudles;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadFactory {

    public static void main(String[] args) {
        //设置线程池中线程名称前缀
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("CUSTORM_THREAD");

        //自定义线程工厂
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,200,200, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),
                new CustomThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    }

    private static class CustomThreadFactory implements java.util.concurrent.ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread("CUSTORM_THREAD");
            return thread;
        }
    }
}
