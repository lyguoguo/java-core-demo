package com.gly.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 循环打印ABABABABA.....100次
 */
public class ABAB {
    public static <k> void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition conditiona = reentrantLock.newCondition();
        Condition conditionb = reentrantLock.newCondition();
        new Thread(()->{
            for (int i=0;i<50;i++){
                reentrantLock.lock();
                conditiona.signal();
                System.out.println("A");
                try {
                    conditionb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
        }).start();
        new Thread(()->{
            for (int i=0;i<50;i++){
                reentrantLock.lock();
                conditionb.signal();
                System.out.println("B");
                try {
                    conditiona.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
        }).start();

    }
}
