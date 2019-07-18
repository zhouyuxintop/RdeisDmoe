package com.zyx.service;

/**
 * sorted set 集合 （又称zset）：有序且不重复集合，String类型元素的集合。
 * 不同的是每个元素都会关联一个double类型的分数，redis则是通过这个分数为集合中的成员进行大小的排序！！！
 * 有序集合的成员是唯一的，但是分数可以重复！
 * 集合是通过哈希表实现的，添加删除查找复杂度都是O（1）。可存储40多亿个成员
 * @author zyx
 *
 */
public interface SortedSetRedisService {

}

//应用场景：
//1.比如twitter 的public timeline可以以发布时间作为score分数来存储，这样获取数据则是按时间排好序的！
//2.Zset 做带权重的队列，比如普通消息可以设置分数score为1，重要的消息score设置为2，
//那么工作线程可以按照score分数的倒叙来获取工作任务，让重要任务优先执行！
