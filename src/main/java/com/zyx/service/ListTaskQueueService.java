package com.zyx.service;

import java.util.List;

/**
 * redisTemplate List����
 * 
 * @author zyx
 *
 */
public interface ListTaskQueueService {

	/**
	 * redisTemplate ���List��������
	 */
	public void listAdd();

	/**
	 * redis ��ȡlist���͵�����
	 * 
	 * @return
	 */
	public Object ListRange();

	/**
	 * ʵ�ַ�ҳ����
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List ListRange(int pageNum, int pageSize);

	// ������� ��ʵ�֣� ���Ա��µ��� ����һ��������Ϣ���û��鿴��������Ϣ������Ա������������Ϣ

	/**
	 * ��ʼ���������������
	 * 
	 * @param orderId
	 */
	public void creatListQueueInit(String orderId);

	/**
	 * �����¼�
	 * 
	 * @param orderId
	 */
	public void listQueueEventTouch(String orderId);

	/**
	 * ��ѯ���ͻ���ѯ����ݵ����
	 * 
	 * @param orderId
	 * @return �ɹ��Ķ���
	 */
	public List listQueueSuc(String orderId);

	/**
	 * ��ѯ��������ѯ�����ж�������û��ִ�У�
	 * 
	 * @param orderId
	 * @return ����ɶ���
	 */
	public List listQueueWait(String orderId);

}
