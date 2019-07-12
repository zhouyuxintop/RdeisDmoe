package com.zhou.redis;

import java.util.Map;

import com.zyx.redisUtil.RedisPoolUtil;

import redis.clients.jedis.Jedis;

/**
 * ͨ��Jedis����redis
 * 
 * @author zyx
 *
 */
public class ConnectResid {
	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String[] args) throws InterruptedException {
		// ͨ���������ȡ���ӳص�Redis����
		Jedis jedis = RedisPoolUtil.getJedis();
		System.out.println("���ӳɹ���");
		System.out.println("-------------------");
		System.out.println("server id runing:" + jedis.ping());
		// �򵥴洢�ַ���
		jedis.set("key3", "Tom");
//		jedis.pexpire("key3", 500);// ����
		System.out.println("key3:" + jedis.get("key3"));
		// �洢������Ϣ��Redis��Hash����HashMap��ʽ��Ŷ�����Ϣ�ģ�����

//		jedis.hset("1", "id", "001");
//		jedis.hset("1", "name", "����");
//		jedis.hset("1", "age", "22");
		// ���������ʱ��
//		jedis.pexpire("1", 3000);

		// Thread.sleep(5000);
		Map<String, String> map = jedis.hgetAll("1");
		System.out.println(map);
	}
}
