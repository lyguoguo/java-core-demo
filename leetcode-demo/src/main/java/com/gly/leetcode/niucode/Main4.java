package com.gly.leetcode.niucode;

import java.util.Scanner;

public class Main4 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int num = sc.nextInt();
            int count = 0;
            while(num>3){
                count+=num/3;
                num=num/3+num%3;
            }
            if(num == 2){
                count++;
            }
            System.out.println(count);
        }
    }
}
