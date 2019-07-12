package com.zyx.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhou.redis.User;
import com.zyx.service.ListTaskQueueService;
import com.zyx.service.UserService;

public class redisTemplateTest {
	ClassPathXmlApplicationContext applicationContext;
	UserService userService;
	ListTaskQueueService listTaskQueueService;

	@Before
	public void before() {
		applicationContext = new ClassPathXmlApplicationContext("applicationcontext-redis.xml");

		userService = applicationContext.getBean(UserService.class);// ��ȡBean

		listTaskQueueService = applicationContext.getBean(ListTaskQueueService.class);// ��ȡBean

	}

	/**
	 * String ���Ͳ���
	 */
	@Test
	public void StrTest() {
		String key = "applicationName1";
		String result = userService.getStr(key);
		System.out.println(result);

	}

	/**
	 * hash���Ͳ���
	 */
	@Test
	public void HashTest() {
		User user = new User();
		user.setId(1);
		user.setName("username");
		user.setAge(22);
		userService.addUser(user);
	}

	/**
	 * hash���Ͳ���
	 */
	@Test
	public void selectUserFromRedis() {
		User user = userService.selectUserById(4);
		System.out.println(user);
	}

	/**
	 * list ���Ͳ���
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void ListAddTest() {
		listTaskQueueService.listAdd();
		List list = (List) listTaskQueueService.ListRange();
		for (Object object : list) {
			System.out.println(object);
		}

	}

	/**
	 * list ��ҳ����
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void ListRangeTest() {
		List list = (List) listTaskQueueService.ListRange(1, 3);
		for (Object object : list) {
			System.out.println(object);
		}

	}

	/**
	 * ������в��ԣ�
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void listTaskQueueTest() {
		String orderId = "0001";
		System.out.println("-------��ʼ���������-------");
		listTaskQueueService.creatListQueueInit(orderId);// ��ʼ����������

		System.out.println("-------��ǰ��ִ�е��������-------");
		List listWait = listTaskQueueService.listQueueWait(orderId);
		for (Object object : listWait) {
			System.out.println(object);
		}

		System.out.println("-------�����������ĳ���������-------");
		listTaskQueueService.listQueueEventTouch(orderId);

		System.out.println("-------ִ��ĳ�������֮��ĵȴ�����-------");
		List newListWait = listTaskQueueService.listQueueWait(orderId);//
		for (Object object : newListWait) {
			System.out.println(object);
		}

		System.out.println("-------����ɵ��������-------");
		List listSuc = listTaskQueueService.listQueueSuc(orderId);
		for (Object object : listSuc) {
			System.out.println(object);
		}

	}

}
