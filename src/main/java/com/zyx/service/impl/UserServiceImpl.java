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
 * redis ����
 * 
 * @author zyx
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * ���巺�ͣ��涨�洢�����ݸ�ʽ ����ǿ������ת��!
	 */
	@Resource(name = "redisTemplate")
	HashOperations<String, Integer, User> hashUser;

	@Override
	public String getStr(String key) {

		ValueOperations<String, String> str = redisTemplate.opsForValue();

		if (redisTemplate.hasKey(key)) {
			System.out.println("��redis�л�ȡ���ݣ�");
			return str.get(key);

		} else {
			System.out.println("�����в�����,�����ݿ��в�ѯ���ݣ�");
			String value = "��������";
			str.set(key, value, 1, TimeUnit.HOURS);
			return value;
		}
	}

	@Override
	public void addUser(User user) {
		// redisTemplate.opsForHash().put("user", user.getId(), user);
		// ʹ�ö���õķ���
		hashUser.put(User.getKeyName(), user.getId(), user);
	}

	@Override
	public User selectUserById(Integer id) {
		// �ȴ�redis�в�ѯ�Ƿ����Key�������򷵻أ���������ȥ�����ݿⷵ�ء�
		// 1.�ж�redis���Ƿ����key��
		boolean isExist = hashUser.hasKey(User.getKeyName(), id);
		if (isExist) {
			// User user = (User) redisTemplate.opsForHash().get("user", id);
			User user = hashUser.get(User.getKeyName(), id);
			System.out.println("��redis�л�ȡ���ݣ�");
			return user;
		} else {
			// �����ݿ��в�ѯ���ݣ�������Redis�У���
			User user = new User();
			user.setId(id);
			user.setName("username");
			user.setAge(22);
			System.out.println("�����в�����,�����ݿ��в�ѯ���ݣ�");
			addUser(user);// ������Redis�У�
			return user;
		}
	}

}
