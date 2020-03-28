package com.gly.leetcode;

import java.util.Scanner;

/**
 * 房间分配问题
 *
 * 有n个房间，现在i号房间里的人需要被重新分配，分配的规则是这样的：先让i号房间里的人全都出来，接下来按照 i+1, i+2, i+3, ...
 * * 的顺序依此往这些房间里放一个人，n号房间的的下一个房间是1号房间，直到所有的人都被重新分配。
 * *
 * * 现在告诉你分配完后每个房间的人数以及最后一个人被分配的房间号x，你需要求出分配前每个房间的人数。数据保证一定有解，若有多解输出任意一个解。
 * *
 * *
 * * 输入描述: 第一行两个整数n, x (2<=n<=10^5, 1<=x<=n)，代表房间房间数量以及最后一个人被分配的房间号；
 * 第二行n个整数 a_i(0<=a_i<=10^9) ，代表每个房间分配后的人数。
 * *
 * *
 * * 输出描述: 输出n个整数，代表每个房间分配前的人数。
 * *
 * * 输入例子1: 3 1 6 5 1
 * *
 * * 输出例子1: 4 4 4
 * *
 *
 * 算法思想：
 *      首先明确一点，最初被重新分配的房间一定是人数最少的房间（或之一）。因为里面的人都出来后人数为0，而下一次加1要等到所有其他房间都加1之后。
 *
 *      读入的同时找出人数最少的房间的人数（因为人数最少的房间有多个，还不能通过这一步就确定最初被分配的房间是哪个）。
 *
 *      每个房间同时减去该人数。
 *
 *      然后设置一个游标，从最后一个人被分配的房间开始，每次给当前房间-1，然后给一个临时变量temp+1，然后游标向左移动，直到找到第一个人数为0的房间，最初被分配的房间就是它了！
 *
 *      然后将临时变量temp加到最初房间的人数中，再将刚才减去的最少的房间的人数加回来。
 */
public class RoomAllocation {
    public static void main(String[]args){
        Scanner in=new Scanner(System.in);
        while(in.hasNext()) {
            int n=in.nextInt();
            int x=in.nextInt();
            long[]a=new long[n];
            long sum=0;
            long minnum=Integer.MAX_VALUE;
            for(int i=0;i<n;i++) {
                a[i]=in.nextInt();
                if(minnum>a[i]) {
                    minnum=a[i];
                }
            }
            for(int i=0;i<n;i++) {
                a[i]-=minnum;
                sum+=minnum;
            }
            int pointer=x-1;
            while(true) {
                if(a[pointer]==0)break;
                a[pointer]--;
                sum++;
                if(pointer==0)
                    pointer=n-1;
                else
                    pointer--;
            }
            a[pointer]=sum;
            for(int i=0;i<n;i++) {
                if(i==n-1)
                    System.out.println(a[i]);
                else
                    System.out.print(a[i]+" ");
            }
        }
    }
}
