package com.gly.mom.kafka;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.Map;

public class KafkaConsumerInterceptor implements ConsumerInterceptor<String,String> {

    @Autowired
    private Jedis jedis;

    /**
     * 消息消费之前
     * @param consumerRecords
     * @return
     */
    @Override
    public ConsumerRecords<String,String> onConsume(ConsumerRecords<String,String> consumerRecords) {
        long lantency = 0L;
        for(ConsumerRecord<String,String> consumerRecord:consumerRecords){
            lantency += (System.currentTimeMillis() - consumerRecord.timestamp());
        }
        jedis.incrBy("totalLatency", lantency);
        long totalLatency = Long.parseLong(jedis.get("totalLatency"));
        long totalSentMsgs = Long.parseLong(jedis.get("totalSentMessage"));
        jedis.set("avgLatency", String.valueOf(totalLatency / totalSentMsgs));
        return consumerRecords;
    }

    @Override
    public void close() {

    }

    /**
     * 提交位移之后
     * @param map
     */
    @Override
    public void onCommit(Map map) {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
