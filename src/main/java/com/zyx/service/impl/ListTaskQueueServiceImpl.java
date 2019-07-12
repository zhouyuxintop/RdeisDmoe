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
 * List �������ݵĲ����� List��������ʹ�ó����� ��ע�б��������ۡ���ҳ�������ȵ�...�ȣ����� ���⻹�С�������С�������
 * 
 * @author zyx
 *
 */
@Service
public class ListTaskQueueServiceImpl implements ListTaskQueueService {

	/**
	 * ���巺������ģ�� ��RedisTemplate<String, String>
	 */
	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@Resource(name = "redisTemplate")
	ValueOperations<String, String> redisString;// �͵��� redisTemplate.opsforValue();

	@Resource(name = "redisTemplate")
	ListOperations<String, String> redisList;

	@Override
	public void listAdd() {
		String listKey = "list:top10";// list���͵�Key
		redisList.rightPush(listKey, "000");// �������
		// ��Ӷ������
		redisList.rightPushAll(listKey, "111", "222", "333", "444", "555", "666", "777", "888", "999");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object ListRange() {
		String listKey = "list:top10";
		List list = redisList.range(listKey, 0, -1);// ��ѯredis�е�List��������
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List ListRange(int pageNum, int pageSize) {
		// ʵ�ַ�ҳ
		String listKey = "list:top10";
		/**
		 * start��(pageNum-1)*pageSize
		 * 
		 * stop:pageNum*pageSize-1
		 */
		int start = (pageNum - 1) * pageSize;
		int stop = pageNum * pageSize - 1;
		List list = redisList.range(listKey, start, stop);
		Long count = redisList.size(listKey);// key��ֵ������
		System.out.println(count);
		return list;
	}

	/**
	 * ������� ��ʵ�֣� ���Ա��µ��� ����һ��������Ϣ���û��鿴��������Ϣ������Ա������������Ϣ
	 */
	@Override
	public void creatListQueueInit(String orderId) {
		String taskkey = "order:" + orderId;// �������
		// ��ʼ����ַ����
		redisList.leftPushAll(taskkey, "1.�̼ҳ���", "2.�Ϻ���תվ", "3.�ɶ���תվ", "4.�ɶ�ĳС��", "5.�ռ��˵�ַ");

	}

	// �����¼�
	@Override
	public void listQueueEventTouch(String orderId) {
		String suckey = "order:" + orderId + ":success";// ������������

		// ��������еĵ�һ�����񣬷�������ɶ��е����һ��λ��
		redisList.rightPopAndLeftPush("order:" + orderId, suckey);// �������е�key
	}

	// ��ѯ���ͻ���ѯ����ݵ����
	@Override
	public List listQueueSuc(String orderId) {
		String suckey = "order:" + orderId + ":success";// ������������
		return redisList.range(suckey, 0, -1);
	}

	// ��ѯ��������ѯ�����ж�������û��ִ�У�
	@Override
	public List listQueueWait(String orderId) {
		String taskkey = "order:" + orderId;// �������
		return redisList.range(taskkey, 0, -1);
	}

}
