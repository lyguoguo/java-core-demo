package com.gly.leetcode;

import java.util.Stack;

/**
 * 链表反转
 */
public class ReverseListNode {
    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next= new ListNode(3);
        listNode.next.next.next=new ListNode(4);
        listNode.next.next.next.next = new ListNode(5);
        System.out.println(reverseList(listNode));
    }

    public static ListNode reverseList(ListNode head) {
       ListNode pre = null;
       ListNode cur = head;
       ListNode tmp = null;
       while (cur != null){
           tmp = cur.next;
           cur.next = pre;
           pre = cur;
           cur = tmp;

       }
       return pre;
    }

    public static class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
  }
}
