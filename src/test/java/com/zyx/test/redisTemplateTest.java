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

		userService = applicationContext.getBean(UserService.class);// 获取Bean

		listTaskQueueService = applicationContext.getBean(ListTaskQueueService.class);// 获取Bean

	}

	/**
	 * String 类型测试
	 */
	@Test
	public void StrTest() {
		String key = "applicationName1";
		String result = userService.getStr(key);
		System.out.println(result);

	}

	/**
	 * hash类型测试
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
	 * hash类型测试
	 */
	@Test
	public void selectUserFromRedis() {
		User user = userService.selectUserById(4);
		System.out.println(user);
	}

	/**
	 * list 类型测试
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
	 * list 分页测试
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
	 * 任务队列测试：
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void listTaskQueueTest() {
		String orderId = "0001";
		System.out.println("-------初始化任务队列-------");
		listTaskQueueService.creatListQueueInit(orderId);// 初始化队列任务

		System.out.println("-------当前待执行的任务队列-------");
		List listWait = listTaskQueueService.listQueueWait(orderId);
		for (Object object : listWait) {
			System.out.println(object);
		}

		System.out.println("-------触发任务：完成某项队列任务-------");
		listTaskQueueService.listQueueEventTouch(orderId);

		System.out.println("-------执行某项触发任务之后的等待队列-------");
		List newListWait = listTaskQueueService.listQueueWait(orderId);//
		for (Object object : newListWait) {
			System.out.println(object);
		}

		System.out.println("-------已完成的任务队列-------");
		List listSuc = listTaskQueueService.listQueueSuc(orderId);
		for (Object object : listSuc) {
			System.out.println(object);
		}

	}

}
