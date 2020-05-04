package com.gly.mom.kafka;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Component
public class KafkaConsumerHandle {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        //自动提交
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "2000");

        //手动提交
        props.put("enable.auto.commit", "false");

        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("foo", "bar"));

//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records)
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//        }



        while (true) {
            ConsumerRecords<String, String> records =  consumer.poll(Duration.ofSeconds(1));
//            todo
//            process(records); // 处理消息
            try {
                consumer.commitSync();//同步 阻塞

                consumer.commitAsync();//异步 非阻塞

                //异步  非阻塞 回调
                consumer.commitAsync((offsets,exception)->{
                    //todo
                });
            } catch (CommitFailedException e) {
//                todo
//                handle(e); // 处理提交失败异常
            }
        }


//        组合
//        try {
//            while(true) {
//                ConsumerRecords<String, String> records =
//                        consumer.poll(Duration.ofSeconds(1));
//                process(records); // 处理消息
//                commitAysnc(); // 使用异步提交规避阻塞
//            }
//        } catch(Exception e) {
//            handle(e); // 处理异常
//        } finally {
//            try {
//                consumer.commitSync(); // 最后一次提交使用同步阻塞式提交
//            } finally {
//                consumer.close();
//            }
//        }



//        private Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
//        int count = 0;
//        while (true) {
//            ConsumerRecords<String, String> records =
//                    consumer.poll(Duration.ofSeconds(1));
//            for (ConsumerRecord<String, String> record: records) {
//                process(record);  // 处理消息
//                offsets.put(new TopicPartition(record.topic(), record.partition()),
//                        new OffsetAndMetadata(record.offset() + 1)；
//                if（count % 100 == 0）
//                consumer.commitAsync(offsets, null); // 回调处理逻辑是null
//                count++;
//            }
//        }
    }


    /**
     * lag监控 剩余消费消息数
     * @param groupID
     * @param bootstrapServers
     * @return
     * @throws TimeoutException
     */
    public static Map<TopicPartition, Long> lagOf(String groupID, String bootstrapServers) throws TimeoutException {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        try (AdminClient client = AdminClient.create(props)) {
            //获取指定消费者组的最新消费位移
            ListConsumerGroupOffsetsResult result = client.listConsumerGroupOffsets(groupID);
            try {
                //获取订阅分区的最新消息位移
                Map<TopicPartition, OffsetAndMetadata> consumedOffsets = result.partitionsToOffsetAndMetadata().get(10, TimeUnit.SECONDS);
                props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 禁止自动提交位移
                props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                try (final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                    Map<TopicPartition, Long> endOffsets = consumer.endOffsets(consumedOffsets.keySet());
                    //相减
                    return endOffsets.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(),
                            entry -> entry.getValue() - consumedOffsets.get(entry.getKey()).offset()));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // 处理中断异常
                // ...
                return Collections.emptyMap();
            } catch (ExecutionException e) {
                // 处理ExecutionException
                // ...
                return Collections.emptyMap();
            } catch (TimeoutException e) {
                throw new TimeoutException("Timed out when getting lag for consumer group " + groupID);
            }
        }
    }
}
