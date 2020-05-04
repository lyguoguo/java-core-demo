package com.gly.bean.generacity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneracityDemo {

    public static  <E> void printArr(E[] inputArr){
        for(E element:inputArr){
           log.info("输入元素：{}",element);
        }
    }


    public static void main(String[] args) {
        String[] inputArr = new String[4];
        inputArr[0] = "a";
        inputArr[1] = "b";
        inputArr[2] = "c";
        inputArr[3] = "d";
        printArr(inputArr);
    }
}
