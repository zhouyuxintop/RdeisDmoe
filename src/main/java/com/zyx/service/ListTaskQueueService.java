package com.zyx.service;

import java.util.List;

/**
 * redisTemplate List类型
 * 
 * @author zyx
 *
 */
public interface ListTaskQueueService {

	/**
	 * redisTemplate 添加List类型数据
	 */
	public void listAdd();

	/**
	 * redis 获取list类型的数据
	 * 
	 * @return
	 */
	public Object ListRange();

	/**
	 * 实现分页功能
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List ListRange(int pageNum, int pageSize);

	// 任务队列 简单实现： 在淘宝下单后 会有一个物流信息。用户查看的物流信息、管理员看到的物流信息

	/**
	 * 初始化：创建任务队列
	 * 
	 * @param orderId
	 */
	public void creatListQueueInit(String orderId);

	/**
	 * 出发事件
	 * 
	 * @param orderId
	 */
	public void listQueueEventTouch(String orderId);

	/**
	 * 查询：客户查询，快递到哪里？
	 * 
	 * @param orderId
	 * @return 成功的队列
	 */
	public List listQueueSuc(String orderId);

	/**
	 * 查询：物流查询，还有多少任务没有执行？
	 * 
	 * @param orderId
	 * @return 待完成队列
	 */
	public List listQueueWait(String orderId);

}
