package com.gly.elastic.syncdata;

import com.alibaba.fastjson.JSON;
import com.gly.common.constants.HBaseContance;
import com.gly.common.utils.DateUtil;
import com.gly.elastic.domain.MetricBean;
import com.gly.elastic.utils.HbaseUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 绝缘电阻统计
 */
@Slf4j
@Component
public class MetricStaticsSync {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @PostConstruct
    public void init() {
        List<String> vins = null;
        try {
            vins = FileUtils.readLines(new File("D:\\Document\\WorkDocument\\StaticData\\METRIC_VIN.txt"),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(CollectionUtils.isEmpty(vins)){
            log.error("异常指标查询失败：车辆vin不存在！");
            return;
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(vins.size());
        vins.parallelStream().forEach(vin->{
            try {
                sync2ESFromHBase(vin);
            }  finally {
                log.info("第{}台车执行完成",atomicInteger.getAndIncrement()+1);
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("绝缘电阻原始数据同步完成");
    }

    private void sync2ESFromHBase(String vin) {
        long startTime = DateUtil.stringToDate("2019-12-01", DateUtil.yyyy_MM_dd).getTime() / 1000;
        long endTime = DateUtil.stringToDate("2019-12-31", DateUtil.yyyy_MM_dd).getTime() / 1000;
        ResultScanner resultScanner = HbaseUtils.getInstance().scanNonLimit(HBaseContance.NEW_ENERGY_VEHICLE_DATA_TABLE, vin, startTime, endTime);
        if(null==resultScanner){
            log.error("车辆vin：{} getResult 查询结果为空",vin);
            return;
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                16, 1000,
                200, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                new ThreadFactoryBuilder().setNameFormat("metric-threadpool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for(Result result:resultScanner) {
            threadPoolExecutor.execute(()->{
                //同步hbase数据到es
                syncESData(result);
            });
        }
    }

    /**
     *   //统计结果数组
     *         //0 样本总量
     * //        车速
     * //（国标范围0-220 km/h）
     *         //1、车辆状态=熄火AND车速≠0km/h的数据占比；  vhlSta=2&speed!=0
     *         //2、档位状态=P档AND车速≠0km/h的数据占比；  traMode=15 &speed!=0
     *         //3、车速>220km/h的数据占比；                speed>2200
     *         //总电流
     *         //（国标范围-1000到1000A）
     *         //4、总电流<-1000A或>1000A的数据占比；batCur  <0 || >20000
     *         //5、总电流=65534A（FFFE）或65535A（FFFF）的数据占比； batCur  65534 || 65535
     *         //绝缘电阻
     *         //（国标范围0-60000KΩ）
     *         //6、绝缘电阻>60000KΩ的数据占比； ins>60000
     * @param result
     */
    private void syncESData(Result result) {
        if(null == result){
            log.info("syncESData result为空");
            return;
        }
        MetricBean metricBean = convertMetricBean(result);
        if(null == metricBean){
            log.info("syncESData convertMetricBean");
            return;
        }
        insertES(metricBean);

    }

    private void insertES(MetricBean metricBean) {
        try {
            // 创建索引请求对象
            IndexRequest indexRequest = new IndexRequest("mydlq-metric", "_doc");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(metricBean);
            // 设置文档内容
            indexRequest.source(json, XContentType.JSON);
            // 执行增加文档
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private MetricBean convertMetricBean(Result result) {
        MetricBean metricBean = new MetricBean();
        List<Cell> cells = result.listCells();
        for(Cell cell:cells){
            //取到修饰名
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            //取到值
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            if("vhlSta".equals(qualifier)){
                metricBean.setVhlSta(value);
            }else if("speed".equals(qualifier)){
                metricBean.setSpeed(value);
            }else if("traMode".equals(qualifier)){
                metricBean.setTraMode(value);
            }else if("batCur".equals(qualifier)){
                metricBean.setBatCur(value);
            }else if("ins".equals(qualifier)){
                metricBean.setIns(value);
            }else if("vin".equals(qualifier)){
                metricBean.setVin(value);
            }
        }
        return metricBean;
    }

}
