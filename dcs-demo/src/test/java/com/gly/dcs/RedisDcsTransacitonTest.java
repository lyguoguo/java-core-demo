package com.gly.dcs;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * redis 分布式事务
 */
public class RedisDcsTransacitonTest extends DcsDemoApplicationTests{

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTrans() throws InterruptedException, ExecutionException {
        String key = "test-trans-1";
        ValueOperations<String, String> strOps = redisTemplate.opsForValue();
        strOps.set(key, "hello");
        ExecutorService pool = Executors.newCachedThreadPool();
        List<Callable<Object>> tasks = new ArrayList<>();
        for(int i=0;i<5;i++){
            final int idx = i;
            tasks.add(new Callable() {
                @Override
                public Object call() throws Exception {
                    return redisTemplate.execute(new SessionCallback() {
                        @Override
                        public Object execute(RedisOperations operations) throws DataAccessException {
                            //watch某个key，当该key被其他客户端打断时，则会中断当前的操作
                            operations.watch(key);
                            String origin = (String) operations.opsForValue().get(key);
                            //开始事务
                            operations.multi();
                            operations.opsForValue().set(key, origin + idx);
                            Object result = null;
                            try{
                                //提交事务
                                result = operations.exec();
                            }catch (Exception e){
                                //取消或回滚事务
//                                System.out.println(idx+" 事务回滚了");
                                operations.discard();
                            }
                            System.out.println("set value:"+origin + idx+",result:"+ result);
                            return result;
                        }
                    });
                }
            });
        }
        List<Future<Object>> futures = pool.invokeAll(tasks);
        for(Future<Object> f:futures){
            System.out.println(f.get());
        }
        pool.shutdown();
        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }

}
