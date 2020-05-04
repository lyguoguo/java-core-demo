package com.gly.mom.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@Configuration
public class KafkaProducerConfig {

    private KafkaProducer producer;

    public KafkaProducerConfig() {
        Properties properties = init();
        producer = new KafkaProducer(properties);
    }

    public static Properties init() {
        Properties properties = new Properties();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", "127.0.0.1:2181");
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put("retries",3);
        //配置拦截器
        List<String> interceptors = new ArrayList<>();
        interceptors.add("com.gly.mom.kafka.KafkaProducerInterceptor"); // 拦截器1
        interceptors.add("com.gly.mom.kafka.KafkaProducerInterceptor2"); // 拦截器2
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        //自定义分区策略
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaPartition.class.getName());
        //消息幂等(单分区&单会话)
        properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
        //事务ID
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"kafka-transation");
        return properties;
    }

    /**
     * 启用事务性producer consumer端需调整配置read_committed
     * consumer端默认read_uncommitted
     * @param topic
     * @param msg
     */
    public void pushWithTransaction(String topic,String msg){
        producer.initTransactions();
        try{
            producer.beginTransaction();
            ProducerRecord producerRecord = new ProducerRecord(topic,msg);
            producer.send(producerRecord, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null == e){
                        log.info("消息发送成功");
                    }else{
                        log.info("消息发送失败");
                    }
                }
            });
            producer.commitTransaction();
        }catch (Exception e){
            log.info("消息发送失败");
            producer.abortTransaction();
        }
    }

}
