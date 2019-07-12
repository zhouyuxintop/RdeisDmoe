package com.zyx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.zyx.service.ListTaskQueueService;

/**
 * List 类型数据的操作。 List类型数据使用场景： 关注列表、留言评论、分页、新闻热点...等！！！ 此外还有【任务队列】！！！
 * 
 * @author zyx
 *
 */
@Service
public class ListTaskQueueServiceImpl implements ListTaskQueueService {

	/**
	 * 定义泛型类型模板 ：RedisTemplate<String, String>
	 */
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	ValueOperations<String, String> redisString;// 就等于 redisTemplate.opsforValue();

	@Resource(name = "redisTemplate")
	ListOperations<String, String> redisList;

	@Override
	public void listAdd() {
		String listKey = "list:top10";// list类型的Key
		redisList.rightPush(listKey, "000");// 添加数据
		// 添加多个数据
		redisList.rightPushAll(listKey, "111", "222", "333", "444", "555", "666", "777", "888", "999");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object ListRange() {
		String listKey = "list:top10";
		List list = redisList.range(listKey, 0, -1);// 查询redis中的List类型数据
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List ListRange(int pageNum, int pageSize) {
		// 实现分页
		String listKey = "list:top10";
		/**
		 * start：(pageNum-1)*pageSize
		 * 
		 * stop:pageNum*pageSize-1
		 */
		int start = (pageNum - 1) * pageSize;
		int stop = pageNum * pageSize - 1;
		List list = redisList.range(listKey, start, stop);
		Long count = redisList.size(listKey);// key的值的数量
		System.out.println(count);
		return list;
	}

	/**
	 * 任务队列 简单实现： 在淘宝下单后 会有一个物流信息。用户查看的物流信息、管理员看到的物流信息
	 */
	@Override
	public void creatListQueueInit(String orderId) {
		String taskkey = "order:" + orderId;// 任务队列
		// 初始化地址队列
		redisList.leftPushAll(taskkey, "1.商家出货", "2.上海中转站", "3.成都中转站", "4.成都某小区", "5.收件人地址");

	}

	// 触发事件
	@Override
	public void listQueueEventTouch(String orderId) {
		String suckey = "order:" + orderId + ":success";// 已完成任务队列

		// 将任务队列的第一个任务，放置已完成队列的最后一个位置
		redisList.rightPopAndLeftPush("order:" + orderId, suckey);// 两个队列的key
	}

	// 查询：客户查询，快递到哪里？
	@Override
	public List listQueueSuc(String orderId) {
		String suckey = "order:" + orderId + ":success";// 已完成任务队列
		return redisList.range(suckey, 0, -1);
	}

	// 查询：物流查询，还有多少任务没有执行？
	@Override
	public List listQueueWait(String orderId) {
		String taskkey = "order:" + orderId;// 任务队列
		return redisList.range(taskkey, 0, -1);
	}

}
