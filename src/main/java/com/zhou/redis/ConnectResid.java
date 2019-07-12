package com.zhou.redis;

import java.util.Map;

import com.zyx.redisUtil.RedisPoolUtil;

import redis.clients.jedis.Jedis;

/**
 * 通过Jedis连接redis
 * 
 * @author zyx
 *
 */
public class ConnectResid {
	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) throws InterruptedException {
		// 通过工具类获取连接池的Redis连接
		Jedis jedis = RedisPoolUtil.getJedis();
		System.out.println("连接成功！");
		System.out.println("-------------------");
		System.out.println("server id runing:" + jedis.ping());
		// 简单存储字符串
		jedis.set("key3", "Tom");
//		jedis.pexpire("key3", 500);// 毫秒
		System.out.println("key3:" + jedis.get("key3"));
		// 存储对象信息，Redis的Hash是以HashMap形式存放对象信息的！！！

//		jedis.hset("1", "id", "001");
//		jedis.hset("1", "name", "张三");
//		jedis.hset("1", "age", "22");
		// 设置其过期时间
//		jedis.pexpire("1", 3000);

		// Thread.sleep(5000);
		Map<String, String> map = jedis.hgetAll("1");
		System.out.println(map);
	}
}
