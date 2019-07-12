package com.zyx.service;

import com.zhou.redis.User;

public interface UserService {

	/**
	 * 从redis 或 数据库中 获取String 类型数据
	 * 
	 * @param key
	 * @return
	 */
	public String getStr(String key);

	/**
	 * 往redis中存存放Hash数据
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 根据id查询用户信息 如果Redis中存在数据，则直接返回。 否则查询数据库返回，并早增加数据到Redis中！
	 * 
	 * @param id
	 * @return
	 */
	public User selectUserById(Integer id);

}
