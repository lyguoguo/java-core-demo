package com.gly.leetcode.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DLinkedNode {
	public int key;
	public int value;
	public DLinkedNode pre;
	public DLinkedNode post;
}