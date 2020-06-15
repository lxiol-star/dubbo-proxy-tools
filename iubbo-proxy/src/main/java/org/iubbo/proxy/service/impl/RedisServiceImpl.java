

package org.iubbo.proxy.service.impl;

import org.iubbo.proxy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author idea
 * @date 2019/7/18
 * @version V1.0
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public String getString(String key) {
        ValueOperations<String, String> operations = template.opsForValue();
        String value = operations.get(key);
        return value;
    }

    @Override
    public void setString(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, value);
    }

    @Override
    public void setString(String key, String value, Long time, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, value, time, timeUnit);
    }


    @Override
    public Integer getInt(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        ValueOperations<String, String> operations = template.opsForValue();
        String val = operations.get(key);
        if (val!=null && val!="" ) {
            return Integer.valueOf(val);
        } else {
            return null;
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        Object object = valueOperations.get(key);
        return (T) object;
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setObject(String key, Object obj) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, obj);
    }

    @Override
    public void setObject(String key, Object obj, Long time, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, obj, time, timeUnit);
    }

    @Override
    public void rpush(String queueName, Object value) {
        redisTemplate.opsForList().rightPush(queueName,value);
    }

    @Override
    public Object rpop(String queueName) {
        return redisTemplate.opsForList().rightPop(queueName);
    }

    @Override
    public void lpush(String queueName, Object value) {
        redisTemplate.opsForList().leftPush(queueName,value);
    }

    @Override
    public Object lpop(String queueName) {
        return redisTemplate.opsForList().leftPop(queueName);
    }


    @Override
    public void setInt(String key, Integer value) {
        if (StringUtils.isEmpty(key) && value != null) {
            return;
        }
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, String.valueOf(value));
    }


    @Override
    public void setInt(String key, Integer value, Long time, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) && value != null) {
            return;
        }
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set(key, String.valueOf(value), time, timeUnit);
    }


}
