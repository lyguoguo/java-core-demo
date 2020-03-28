package com.gly.threadpool;

import java.util.concurrent.Semaphore;

/**
 * Semaphone 信号量
 * Semaphone是用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源。信号量可以理解为对锁的扩展，
 *      无论是内部锁synchronized还是重入锁ReentrantLock，一次只允许一个线程访问一个资源，
 *      而信号量却可以指定多个线程，同时访问同一个资源。
 *
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<8;i++){
            new Worker(i,semaphore).start();

        }
    }

     static class Worker extends Thread{
        int num;
        Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

         @Override
         public void run() {
             try {
                 semaphore.acquire();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println("工人"+num+"获得了锁============");
             try {
                 Thread.sleep(500);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             System.out.println("工人"+num+"准备释放锁===========");
             semaphore.release();
         }

    }
}
