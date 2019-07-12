package com.zyx.redisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis���ݿ����ӳع�����
 * 
 * @author zyx
 *
 */
public class RedisPoolUtil {

	private static JedisPool jedisPool;
	private static final String host = "127.0.0.1";
	private static final int port = 6379;

	static {
		// ��ȡ���ݿ����ӳض���
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(5);// ���������
		jedisPoolConfig.setMaxIdle(1);// ��������
		// ......���������

		// �������ӳ�
		jedisPool = new JedisPool(jedisPoolConfig, host, port);
	}

	/**
	 * ��RedisPool�л�ȡ Jedis ����
	 * 
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}

	/**
	 * �ر�����
	 * 
	 * @return
	 */
	public static void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	// ÿ�λ�ȡ�����Ӷ���һ��
	public static void main(String[] args) {
		for (int i = 0; i < 6; i++) {
			Jedis jedis = RedisPoolUtil.getJedis();
			System.out.println(jedis);
		}
	}
}
