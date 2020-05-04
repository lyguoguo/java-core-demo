package com.gly.leetcode.niucode;

import org.springframework.util.StringUtils;

import java.util.Scanner;

public class WordLength {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入字符串：");
        String inputWord = scanner.nextLine();
        if(StringUtils.isEmpty(inputWord)){
            System.out.println("请输入有效字符串：");
        }
        int lastWordlength = computeLength(inputWord);
        System.out.println(lastWordlength);
    }

    private static int computeLength(String word){
        if(StringUtils.isEmpty(word)){
            return 0;
        }
        String[] wordArr = word.split(" ");
        if(null == wordArr || wordArr.length == 0){
            return 0;
        }
        String lastWord = wordArr[wordArr.length-1];
        if(StringUtils.isEmpty(lastWord)){
            return 0;
        }
        return lastWord.length();
    }
}
