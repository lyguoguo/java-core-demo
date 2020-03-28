package com.gly.threadpool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoreThreadTest {
    public static void main(String[] args) {
        int num = Runtime.getRuntime().availableProcessors();
        log.info("当前可用线程数：{}",num);
    }
}
