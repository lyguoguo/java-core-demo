package com.gly.elastic;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchDemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticsearchDemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
