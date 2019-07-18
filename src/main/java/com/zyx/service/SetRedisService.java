package com.zyx.service;

/**
 * set类型使用场景：
 * 
 * 1.对两个集合的数据进行计算【交集、并集、差集】
 * 2.可以实现共同关注、共同爱好、二度好友等功能。
 * 3.利用唯一性，可以统计访问网站的所有杜力IP
 * @author zyx
 *
 */
public interface SetRedisService {

}

//Redis的 set类型 非常类似与java中的HashTable集合。
//底层使用了Intset和hashtable两种数据结构存储。Intset可以理解为数组，hashtable 哈希表（key 为set的值）
//Intset内部其实是一个数组【int8_coentents[]数组】，而且存储数据的时候是有序的！！！在查找数据的时候使用的是二分查找法实现的。