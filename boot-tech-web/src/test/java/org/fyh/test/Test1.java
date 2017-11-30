package org.fyh.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Test1 {
	
	public static void main(String[] args) throws ExecutionException {
//		ipBlackList.put("a", 1);
		System.out.println(ipBlackList.get("a"));
//		ipBlackList.refresh("a");
		System.out.println(ipBlackList.get("a"));
	}
	
	public final static LoadingCache<String, Integer> ipBlackList = CacheBuilder.newBuilder()
			// 设置cache的初始大小为10，要合理设置该值
			.initialCapacity(1000)
			// 设置并发数为1000，即同一时间最多只能有1000个线程往cache执行写入操作
			.concurrencyLevel(1000).maximumSize(Long.MAX_VALUE)
			// 设置cache中的数据在写入之后的存活时间为24小时
			.expireAfterWrite(7, TimeUnit.DAYS)
			// 构建cache实例
			.build(new CacheLoader<String, Integer>() {
				@Override
				public Integer load(String key) throws Exception {
					return -1;
				}
			});
}
