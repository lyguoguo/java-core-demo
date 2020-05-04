package com.gly.mom.kafka;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Component
public class KafkaProducerInterceptor implements ProducerInterceptor<String,String> {

    @Autowired
    private Jedis jedis;

    /**
     * 消息发送之前调用
     * @param producerRecord
     * @return
     */
    @Override
    public ProducerRecord<String,String> onSend(ProducerRecord<String,String> producerRecord) {
        jedis.incr("totalSentMessage");
        return producerRecord;
    }

    /**
     * 消息提交成功或消息发送失败之后调用
     * 早于callback之前调用
     * 和onSend不在同一个线程调用,注意共享变量临界
     * 位于producer发送主路径,不要放太重逻辑,影响tps
     * @param recordMetadata
     * @param e
     */
    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
