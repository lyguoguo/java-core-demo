package com.gly.leetcode.niucode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main2 {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String inputStr = scanner.nextLine();
//        StringBuffer sb = new StringBuffer();
//        for(int i=inputStr.length()-1;i>=0;i--){
//            String curStr = Character.toString(inputStr.charAt(i));
//            if(!sb.toString().contains(curStr)){
//                sb.append(curStr);
//            }
//        }
//        System.out.println(sb.toString());
        StringBuffer sb = new StringBuffer("424241000");
        System.out.println(sb.reverse().toString());
        String inputStr = "uqic^g`(s&jnl(m#vt!onwdj(ru+os&wx";
        int count = getLen(inputStr);
        System.out.println(count);
    }

    private static int count(String inputStr){
        System.out.println(inputStr.length());
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<inputStr.length();i++){
            String str = Character.toString(inputStr.charAt(i));
            long acsii = Long.valueOf(inputStr.charAt(i));
            System.out.println("字符串:"+Character.toString(inputStr.charAt(i))+" acsii:"+acsii);
            if(0 <= acsii && acsii <= 127){
                if(!sb.toString().contains(str)){
                    sb.append(str);
                }
            }
        }
        return sb.toString().length();
    }


    public static int  getLen(String s) {
        int[] arr=new int[128];
        for(int i=0;i<s.length();i++){
            arr[s.charAt(i)]=1;
        }
        int len=0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]==1){
                len++;
            }
        }
        return len;
    }

}
