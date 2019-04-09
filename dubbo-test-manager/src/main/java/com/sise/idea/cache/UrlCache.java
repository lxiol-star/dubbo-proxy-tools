package com.sise.idea.cache;

import com.alibaba.dubbo.common.URL;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于存储dubbo的URL对象
 *
 * @author idea
 * @data 2019/4/7
 */
public class UrlCache {

    private static ConcurrentHashMap<String, URL> map = new ConcurrentHashMap<>();


    /**
     * 存储缓存
     *
     * @param interfaceName
     * @param url
     */
    public static void put(String interfaceName, URL url) {

        map.put(interfaceName, url);
    }


    /**
     * 获取URL模型集合
     *
     * @param interfaceName
     * @return
     */
    public static URL get(String interfaceName) {
        return map.get(interfaceName);
    }

    /**
     * 清空缓存
     */
    public static void clean() {
        map.clear();
    }

}
