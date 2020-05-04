package com.gly.mom.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class KafkaPartition implements Partitioner {

    /**
     * 默认轮询
     * @param topic
     * @param key
     * @param keyBytes
     * @param value
     * @param valueBytes
     * @param cluster
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        //随机
//        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
//        return ThreadLocalRandom.current().nextInt(partitions.size());

        //按消息键保序
//        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
//        return Math.abs(key.hashCode()) % partitions.size();


        //根据broker所在的ip
//        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
//        return partitions.stream().filter(p -> isSouth(p.leader().host())).map(PartitionInfo::partition).findAny().get();
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
