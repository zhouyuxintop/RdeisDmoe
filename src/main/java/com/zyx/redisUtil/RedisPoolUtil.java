package com.zyx.redisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis数据库连接池工具类
 * 
 * @author zyx
 *
 */
public class RedisPoolUtil {

	private static JedisPool jedisPool;
	private static final String host = "127.0.0.1";
	private static final int port = 6379;

	static {
		// 获取数据库连接池对象
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(5);// 最大连接数
		jedisPoolConfig.setMaxIdle(1);// 最大空闲数
		// ......设置其参数

		// 创建连接池
		jedisPool = new JedisPool(jedisPoolConfig, host, port);
	}

	/**
	 * 从RedisPool中获取 Jedis 连接
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	/**
	 * 关闭连接
	 * 
	 * @return
	 */
	public static void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	// 每次获取的连接都不一样
	public static void main(String[] args) {
		for (int i = 0; i < 6; i++) {
			Jedis jedis = RedisPoolUtil.getJedis();
			System.out.println(jedis);
		}
	}
}
