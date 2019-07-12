package com.zyx.service;

import com.zhou.redis.User;

public interface UserService {

	/**
	 * ��redis �� ���ݿ��� ��ȡString ��������
	 * 
	 * @param key
	 * @return
	 */
	public String getStr(String key);

	/**
	 * ��redis�д���Hash����
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * ����id��ѯ�û���Ϣ ���Redis�д������ݣ���ֱ�ӷ��ء� �����ѯ���ݿⷵ�أ������������ݵ�Redis�У�
	 * 
	 * @param id
	 * @return
	 */
	public User selectUserById(Integer id);

}
