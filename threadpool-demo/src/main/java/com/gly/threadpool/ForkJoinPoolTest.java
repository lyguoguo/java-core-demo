package com.gly.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        SumTask sumTask = new SumTask(0,100);
        Future<Integer> forkJoinTask = forkJoinPool.submit(sumTask);
        System.out.println(forkJoinTask.get());
    }


    private static class SumTask extends RecursiveTask<Integer> {
        final int threshold = 20;
        private int start;
        private int end;
        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int sum = 0;
            if(end - start < threshold){
               for(int i=start;i<end;i++){
                   sum+=i;
               }
               return sum;
            }else{
                int middle = (end + start) /2 ;
                 SumTask sumTask1 = new SumTask(start,middle);
                 SumTask sumTask2 = new SumTask(middle,end);
                 sumTask1.fork();
                 sumTask2.fork();
                 return sumTask1.join()+sumTask2.join();
            }
        }
    }
}
