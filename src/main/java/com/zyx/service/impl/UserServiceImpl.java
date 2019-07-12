package com.zyx.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.zhou.redis.User;
import com.zyx.service.UserService;

/**
 * redis 操作
 * 
 * @author zyx
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 定义泛型，规定存储的数据格式 避免强制类型转换!
	 */
	@Resource(name = "redisTemplate")
	HashOperations<String, Integer, User> hashUser;

	@Override
	public String getStr(String key) {

		ValueOperations<String, String> str = redisTemplate.opsForValue();

		if (redisTemplate.hasKey(key)) {
			System.out.println("在redis中获取数据：");
			return str.get(key);

		} else {
			System.out.println("缓存中不存在,从数据库中查询数据：");
			String value = "测试数据";
			str.set(key, value, 1, TimeUnit.HOURS);
			return value;
		}
	}

	@Override
	public void addUser(User user) {
		// redisTemplate.opsForHash().put("user", user.getId(), user);
		// 使用定义好的泛型
		hashUser.put(User.getKeyName(), user.getId(), user);
	}

	@Override
	public User selectUserById(Integer id) {
		// 先从redis中查询是否存在Key，存在则返回，不存在则去查数据库返回。
		// 1.判断redis中是否存在key！
		boolean isExist = hashUser.hasKey(User.getKeyName(), id);
		if (isExist) {
			// User user = (User) redisTemplate.opsForHash().get("user", id);
			User user = hashUser.get(User.getKeyName(), id);
			System.out.println("在redis中获取数据：");
			return user;
		} else {
			// 从数据库中查询数据，并放入Redis中！！
			User user = new User();
			user.setId(id);
			user.setName("username");
			user.setAge(22);
			System.out.println("缓存中不存在,从数据库中查询数据：");
			addUser(user);// 增肌到Redis中！
			return user;
		}
	}

}
