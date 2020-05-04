package com.gly.threadpool;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest2 {

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        CompletableFuture<Integer> otherFuture = CompletableFuture
                // 执行异步任务
                .supplyAsync(() -> {
                    int result = new Random().nextInt(100);
                    System.out.println("任务A：" + result);
                    return result;
                }, executor);

        CompletableFuture<Integer> future = CompletableFuture
                // 执行异步任务
                .supplyAsync(() -> {
                    int result = new Random().nextInt(100);
                    System.out.println("任务B：" + result);
                    return result;
                }, executor)
                .thenCombineAsync(otherFuture, (current, other) -> {
                    int result = other + current;
                    System.out.println("组合两个任务的结果：" + result);
                    return result;
                });

        System.out.println(future.get());

        executor.shutdown();
    }

}