//package com.gly.mom.kafka;
//
//import javafx.concurrent.Worker;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//
//import java.time.Duration;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//public class KafkaConsumer2 implements Runnable {
//
//    private final KafkaConsumer<String, String> consumer;
//    private ExecutorService executors;
//
//
//    private int workerNum = ...;
//    executors = new ThreadPoolExecutor(
//            workerNum, workerNum, 0L, TimeUnit.MILLISECONDS,
//  new ArrayBlockingQueue<>(1000),
//  new ThreadPoolExecutor.CallerRunsPolicy());
//
//
//        while (true)  {
//        ConsumerRecords<String, String> records =
//                consumer.poll(Duration.ofSeconds(1));
//        for (final ConsumerRecord record : records) {
//            executors.submit(new Worker(record));
//        }
//    }
//..
//}