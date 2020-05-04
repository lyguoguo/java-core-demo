package com.gly.elastic;

import com.alibaba.fastjson.JSON;
import com.gly.elastic.domain.UserInfo;
import com.gly.elastic.service.AggrBucketMetricService;
import com.gly.elastic.service.DocumentService;
import com.gly.elastic.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class ElasticSearchTest extends ElasticsearchDemoApplicationTests{

    @Autowired
    private  RestHighLevelClient restHighLevelClient;
    @Autowired
    private IndexService indexService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private AggrBucketMetricService aggrBucketMetricService;


    @Test
    public void insertTest() throws IOException {
        // 创建索引请求对象
        IndexRequest indexRequest = new IndexRequest("user-metric", "_doc");
        UserInfo userInfo = new UserInfo().setAddress("浙江省杭州市").setAge(19).setBirthDate("2000-02-12 12:23:23").
                setCreateTime(new Date()).setName("张三").setRemark("张三是个好人").setSalary(17000.00f);
        // 将对象转换为 byte 数组
        byte[] json = JSON.toJSONBytes(userInfo);
        // 设置文档内容
        indexRequest.source(json, XContentType.JSON);
        // 执行增加文档
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("创建状态：{}", response.status());
    }

    @Test
    public void searchTest(){
        try {
            // 获取请求对象
            GetRequest getRequest = new GetRequest("user-metric", "_doc", "jGAX33EBkJTY720wfV7H");
            // 获取文档信息
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            // 将 JSON 转换成对象
            if (getResponse.isExists()) {
                UserInfo userInfo = JSON.parseObject(getResponse.getSourceAsBytes(), UserInfo.class);
                log.info("用户信息：{}", JSON.toJSONString(userInfo));
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Test
    public void deleteIndexTest() throws IOException {
        // 新建删除索引请求对象
        DeleteIndexRequest request = new DeleteIndexRequest("user-metric");
        // 执行删除索引
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        // 判断是否删除成功
        boolean siDeleted = acknowledgedResponse.isAcknowledged();
        log.info("是否删除成功：{}", siDeleted);
    }


    @Test
    public void test() throws IOException {
        //创建索引
//        indexService.indexCreate();
        //删除索引
//        indexService.deleteIndex();

        //批量插入
//        List<Map<String,Object>> list = new ArrayList<>();

//        private String name;
//        private Integer age;
//        private float salary;
//        private String address;
//        private String remark;
//        private Date createTime;
//        private String birthDate;

//        for(int i=0;i<10;i++){
//            Map<String,Object> map = new HashMap<>();
//            map.put("name","gg"+i);
//            map.put("age",18+i);
//            map.put("salary",3500+i*100);
//            map.put("address",i%2==0?"杭州":"宁波");
//            map.put("remark","测试");
//            map.put("createTime",new Date());
//            map.put("birthDate","2000-10-02");
//            list.add(map);
//        }
//        documentService.bulkPutIndex(list);

        //查询明细
//        documentService.getDocument();
        //批量查询
//        documentService.batchSearch();
//        更新文档
//        documentService.updateDocument();
        //删除文档
//        documentService.deleteDocument();

        //聚合
        aggrBucketMetricService.aggregationTopHits();
    }
}
