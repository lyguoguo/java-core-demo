package com.gly.leetcode.niucode;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int keyValueCount = Integer.valueOf(scanner.nextLine());
        while(scanner.hasNext()){
            SortedMap<Integer,Integer> map = new TreeMap();
            for(int i=0;i<keyValueCount;i++){
                String[] keyValue = scanner.nextLine().split(" ");
                Integer key = Integer.valueOf(keyValue[0]);
                Integer value = Integer.valueOf(keyValue[1]);
                if(null == map.get(key)){
                    map.put(key,value);
                }else{
                    map.put(key,value+map.get(key));
                }
            }
            map.forEach((key,value)->{
                System.out.println(key + " " + value);
            });
        }
    }
}
