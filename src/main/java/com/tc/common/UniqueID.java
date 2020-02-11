package com.tc.common;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 生成唯一长整型ID
 * @author shiguang.zhou
 *
 */
public class UniqueID {

	private static AtomicLong uniqueId = new AtomicLong();
	
	private static AtomicInteger uniqueIntId = new AtomicInteger();

	public static long nextId() {
		return uniqueId.getAndIncrement();
	}

	public static int nextIntId() {
		return uniqueIntId.getAndIncrement();
	}
}
