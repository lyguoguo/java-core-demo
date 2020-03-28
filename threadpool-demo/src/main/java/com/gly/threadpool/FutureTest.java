package com.gly.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class FutureTest {
    public static void main(String[] args) {
        try {
            testFutrue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testFutrue() throws Exception{
        List<Future<String>> futures = new ArrayList<Future<String>>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("已经提交资源申请");
        for (int i = 0; i < 10; i++) {
            log.info("正在添加future任务：{}",i);
            futures.add(threadPoolExecutor.submit(new Task()));
        }
        for (Future<String> future : futures) {
//            if (!future.isDone()) {
//                System.out.println("资源还没有准备好");
//            }
            System.out.println(future.get());
        }
        System.out.println("程序执行结束");
    }

    private static class Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            // 模拟真实事务的处理过程，这个过程是非常耗时的。
            Thread.sleep(5000);
            return "call return " + Thread.currentThread().getName();
        }
    }

}
