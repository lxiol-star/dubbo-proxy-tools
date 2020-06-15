package org.iubbo.proxy.service;

import java.util.concurrent.TimeUnit;

/**
 * @author idea
 * @date 2019/7/29
 * @version V1.0
 */
public interface RedisService {

    String getString(String key);

    Integer getInt(String key);

    <T> T getObject(String key, Class<T> clazz);

    void del(String key);

    void setString(String key, String value);

    void setString(String key, String value, Long time, TimeUnit timeUnit);

    void setInt(String key, Integer value);

    void setInt(String key, Integer value, Long time, TimeUnit timeUnit);

    void setObject(String key, Object obj);

    void setObject(String key, Object obj, Long time, TimeUnit timeUnit);

    /**
     * 往队列中加入元素
     *
     * @param queueName
     * @param value
     */
    void rpush(String queueName, Object value);

    /**
     * 往队列中取出元素
     *
     * @param queueName
     */
    Object rpop(String queueName);

    /**
     * 从队列左边加入元素
     *
     * @param queueName
     * @param value
     */
    void lpush(String queueName, Object value);


    /**
     * 从队列左边取出元素
     *
     * @param queueName
     */
    Object lpop(String queueName);
}
