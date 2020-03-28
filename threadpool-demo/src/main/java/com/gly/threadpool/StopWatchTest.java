package com.gly.threadpool;

import org.springframework.util.StopWatch;

/**
 * 倒计时工具类
 */
public class StopWatchTest {

    public static void main(String[] args) throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task1");
        Thread.sleep(2000);
        stopWatch.stop();
        //task1执行时间
        System.out.println("task1执行时间："+stopWatch.getTotalTimeMillis());
        stopWatch.start("task2");
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println("当前线程执行明细："+stopWatch.prettyPrint());
        System.out.println("当前线程执行总时间："+stopWatch.shortSummary());
        System.out.println("当前线程执行总毫秒数："+stopWatch.getTotalTimeMillis());
    }
}
