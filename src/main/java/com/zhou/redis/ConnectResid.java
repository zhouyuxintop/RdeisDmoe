package com.zhou.redis;

import redis.clients.jedis.Jedis;

public class ConnectResid {
public static void main(String[] args) {
	//连接redis server服务器端 ip
	Jedis jedis=new Jedis("192.168.0.105");
	System.out.println("连接成功！");
	System.out.println("-------------------");
	System.out.println("server id runing:"+jedis.ping());
	jedis.set("key3", "Tom");
	System.out.println("key3:"+jedis.get("key3"));
	
}
}
