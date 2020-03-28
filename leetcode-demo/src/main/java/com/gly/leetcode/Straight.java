package com.gly.leetcode;

import java.util.Arrays;

/**
 * leetcode 面试题61
 * 扑克牌中的顺子
 *
 * 解题思路：
 *  先对数组排序
 *  判断当前数值是否为0
 *      1）是，则跳过且对标识为0变量+1
 *      2）否，判断该数和后面一位数之差是否为0，若是表示两数相同，因皆为非0，因此该数组必不连续
 *             若该数之差为1,则忽略
 *             若该数只差不为1，则记录差值并-1，此数极为中间需补全的数字个数
 *       若补全个数为0，则返回true
 *      判断0个数是否大于或等于补全数字个数，若是则为true，否则false
 *
 */
public class Straight {
    public static void main(String[] args) {
//        int[] nums = {0,0,0,2,3,6};
//        int[] nums = {1,2,3,4,5};
//        int[] nums = {9,10,4,0,9};
//        int[] nums = {0,0,1,2,5};
//        int[] nums = {11,12,5,9,8};
        int[] nums = {0,0,2,2,5};
        System.out.println(isStraight(nums));
    }

    public static boolean isStraight(int[] nums) {
        if(null == nums || nums.length == 0){
            return false;
        }
        Arrays.sort(nums);
        int z = 0;
        int df = 0;
        for(int i=0;i<nums.length-1;i++){
            if(nums[i] != 0){
                if(nums[i+1] - nums[i] == 0){
                    return false;
                }else if(nums[i+1]-nums[i] != 1){
                    df+=nums[i+1]-nums[i]-1;
                }
            }else{
                z++;
            }
        }
        if(df == 0){
            return true;
        }
        if(z >= df){
            return true;
        }
        return false;
    }
}
