package com.gly.leetcode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和 leetcode 15
 *  问题描述：给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 *
 * 示例：
 *  给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 *
 * 满足要求的三元组集合为：
 * [
 *   [-1, 0, 1],
 *   [-1, -1, 2]
 * ]
 *
 *
 * ===================================================
 * 解题方案
 * 思路
 * 标签：数组遍历
 * 首先对数组进行排序，排序后固定一个数 nums[i]，再使用左右指针指向 nums[i]后面的两端，数字分别为 nums[L] 和 nums[R]，计算三个数的和 sumsum 判断是否满足为 00，满足则添加进结果集
 * 如果 nums[i]大于 0，则三数之和必然无法等于 00，结束循环
 * 如果 nums[i] == nums[i−1]，则说明该数字重复，会导致结果重复，所以应该跳过
 * 当 sum == 0 时，nums[L] == nums[L+1] 则会导致结果重复，应该跳过，L++
 * 当 sum == 0 时，nums[R] == nums[R−1] 则会导致结果重复，应该跳过，R--
 * 时间复杂度：O(n^2)，n为数组长度
 *
 *
 *
 */
public class Solution {

    public static void main(String[] args) {
        int[] nums = {-1,0,1,2,-1,-4};
        List<List<Integer>> result = threeSum(nums);
        System.out.println("result："+result.toString());
    }

    public static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> result = new ArrayList<>();
        if(null == nums){
            return result;
        }
        if(nums.length < 3){
            return result;
        }
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i] > 0){
                //当前数大于0，则之后的数必大于0，则结果无论如何必然大于0
                break;
            }
            int L = i+1;
            if(i > 0 && nums[i] == nums[i-1]){
                //去重
                continue;
            }
            int R = nums.length - 1;
            while (L < R){
                int sum = nums[i] + nums[L] + nums[R];
                if(sum == 0){
                    result.add(Arrays.asList(nums[i],nums[L],nums[R]));
                    //todo
//                    while (L<R && nums[L] == nums[L+1]) L++; // 去重
//                    while (L<R && nums[R] == nums[R-1]) R--; // 去重
                    L++;
                    R--;
                }
                if(sum < 0){
                    L++;
                }
                if(sum > 0){
                    R--;
                }
            }
        }
        return result;


    }
}
