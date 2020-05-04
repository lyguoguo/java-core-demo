package com.gly.leetcode.niucode;

import java.util.Scanner;

public class Main3 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int inputStr = sc.nextInt();
        String tNum =Integer.toBinaryString(inputStr);
        System.out.println(getNumCount(tNum));
    }

    public static int getNumCount(String tNum){
        int count = 0;
        char[] charArr = tNum.toCharArray();
        for(char cha:charArr){
            if(Character.toString(cha).equals("1")){
                count++;
            }
        }
        return count;
    }
}
