package com.gly.threadpool;

import java.util.ArrayList;

public class ThreadJoinTest {
    public static void main(String[] args) {
        System.out.println("开始执行主线程===============");
        Thread thread = new Thread(()->{
            System.out.println("开始执行子线程======");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程执行结束=======");
        });
        //设为守护线程
        thread.setDaemon(true);
        thread.start();
        //控制主线程等子线程结束再返回
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("主线程执行结束=========");
    }
}
