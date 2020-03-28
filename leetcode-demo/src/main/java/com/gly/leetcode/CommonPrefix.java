package com.gly.leetcode;

/**
 * leetcode 14 字符串公共前缀问题
 *
 * 解题思路：
 *      想象数组的末尾有一个非常短的字符串，使用上述方法依旧会进行 S​S​ 次比较。优化这类情况的一种方法就是水平扫描。
 *      我们从前往后枚举字符串的每一列，先比较每个字符串相同列上的字符（即不同字符串相同下标的字符）然后再进行对下一列的比较。
 *
 */
public class CommonPrefix {
    public static void main(String[] args) {
        String[] strs = {"a"};
        System.out.println(longestCommonPrefix(strs));
    }

    public static String longestCommonPrefix(String[] strs) {
        if(null == strs || strs.length == 0){
            return null;
        }
        String rStr = strs[0];
        for(int i=0;i<rStr.length();i++){
            char rAt = rStr.charAt(i);
            for(int j=1;j<strs.length;j++){
                if(strs[j].length() == i || rAt != strs[j].charAt(i)){
                   return rStr.substring(0,i);
                }
            }
        }
        return rStr;
    }
}
