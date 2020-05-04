package com.gly.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture completableFuture = CompletableFuture.supplyAsync(()->{
//            return "Hello World";
//        }).thenApply(s->s+" qq").thenApply(String::toUpperCase);
//        completableFuture.join();
//        System.out.println(completableFuture.get());



//        CompletableFuture<String> f1 =
//                CompletableFuture.supplyAsync(()->{
//                    int t = 8;
//                    try {
//                        Thread.sleep(900);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return String.valueOf(t);
//                });
//
//        CompletableFuture<String> f2 =
//                CompletableFuture.supplyAsync(()->{
//                    int t = 7;
//                    try {
//                        Thread.sleep(700);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return String.valueOf(t);
//                });
//
//        CompletableFuture<String> f3 =
//                f1.applyToEither(f2,s -> s);
//
//        System.out.println(f3.join());


        CompletableFuture<String>
                f0 = CompletableFuture
                .supplyAsync(()->"app").thenApply(r->r + " happy")
                .exceptionally(e->e.getMessage());
        System.out.println(f0.join());
    }
}
