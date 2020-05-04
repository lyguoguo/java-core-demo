package com.gly.leetcode.niucode;

import java.util.Scanner;

public class Main {
//    public static void main(String[] args){
////        Scanner scanner = new Scanner(System.in);
////        System.out.println("请输入字符串：");
////        String inputWord = scanner.nextLine();
////        int lastWordlength = computeLength(inputWord);
////        System.out.println(lastWordlength);
////
////        List<String> list = new ArrayList<>();
////        list.add("1");
////        list.add("2");
////        list.forEach(System.out::println);
//
//        System.out.println(Long.decode("0xC460"));
//    }
//
//    private static int computeLength(String word){
//        String[] wordArr = word.split(" ");
//        if(null == wordArr || wordArr.length == 0){
//            return 0;
//        }
//        String lastWord = wordArr[wordArr.length-1];
//        return lastWord.length();
//    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String inputWord = scanner.nextLine();
        System.out.println(Long.decode(inputWord));
    }
}

