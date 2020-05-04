package com.gly.mom.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ecarx heng.zhou
 * @create 2017-12-20 15:07
 **/
@Configuration
public class RedisPoolConfig {

    @Autowired
    RedisConfig configuration;

    public JedisPool convertJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        return new JedisPool(config, configuration.getRedisHost (), configuration.getRedisPort (), configuration.getRedisTimeOut ()
                , configuration.getRedisPassword (),configuration.getRedisDatabase ());
    }

    @Bean
    public Jedis build(){
        return convertJedisPool().getResource();
    }

}