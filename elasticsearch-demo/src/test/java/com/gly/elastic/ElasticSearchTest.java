package com.gly.elastic;

import com.alibaba.fastjson.JSON;
import com.gly.elastic.domain.VehicleLoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class ElasticSearchTest extends ElasticsearchDemoApplicationTests{

    @Autowired
    private  RestHighLevelClient restHighLevelClient;

    @Test
    public void elasticTest(){
        try {
            // 获取请求对象
            GetRequest getRequest = new GetRequest("vehicle_rvs_req_v1", "rvs_req", "NEU57949080104182");
            // 获取文档信息
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            // 将 JSON 转换成对象
            if (getResponse.isExists()) {
                VehicleLoginInfo vehicleLoginInfo = JSON.parseObject(getResponse.getSourceAsBytes(), VehicleLoginInfo.class);
                log.info("车辆登录信息：{}", JSON.toJSONString(vehicleLoginInfo));
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
