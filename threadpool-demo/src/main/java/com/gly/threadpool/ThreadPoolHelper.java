package com.gly.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池初始化
 *     ！！！根据业务设置线程数，主要区别IO还是CPU
 */
public class ThreadPoolHelper {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10, 50,
            2, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build());

    public static ThreadPoolExecutor getRightThreadPool() {
        return threadPoolExecutor;
    }

}
