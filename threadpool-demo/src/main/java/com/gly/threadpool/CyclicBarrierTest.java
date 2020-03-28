package com.gly.threadpool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier(同步屏障)
 *
 * 当一组线程到达一个屏障时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续执行。
 *
 * CyclicBarrier的构造方法是CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，每个线程调用await方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
 *
 *  CyclicBarrier还提供了更高级的构造函数，用于在线程到达屏障时，优先执行barrierAction。
 *
 * public CyclicBarrier(int parties, Runnable barrierAction)
 *
 *  CyclicBarrier和CountDownLatch的区别
 *
 * 1）CyclicBarrier的计数器可以使用重复使用，在最初的4个线程越过barrier状态后，又可以用来进行新一轮的使用。
 *
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,new A());
        for(int i=0;i<2;i++){
            new Worker(cyclicBarrier).start();
        }
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CyclicBarrier 重用========");
        for(int i=0;i<2;i++){
            new Worker(cyclicBarrier).start();
        }
    }
    static class Worker extends Thread{
        CyclicBarrier cyclicBarrier;
        public Worker(CyclicBarrier cyclicBarrier){
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程正在写入=====");
            try {
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程是否被中断："+cyclicBarrier.isBroken());
            System.out.println("线程执行完成");
        }
    }


    static class A extends Thread{

        @Override
        public void run() {
            System.out.println("线程写入完成，执行action方法=========================");
        }
    }
}
