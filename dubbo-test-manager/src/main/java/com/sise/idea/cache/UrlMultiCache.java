package com.sise.idea.cache;

import com.alibaba.dubbo.common.URL;
import com.sise.idea.util.LinkedMultiValueMap;
import com.sise.idea.util.MultiValueMap;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

/**
 * 一键多值类型的map
 *
 * @author idea
 * @data 2019/8/4
 */
public class UrlMultiCache {

    private static MultiValueMap<String, URL> map = new LinkedMultiValueMap();


    /**
     * 存储缓存
     *
     * @param interfaceName
     * @param url
     */
    public static void put(String interfaceName, URL url) {

        map.add(interfaceName, url);
    }


    /**
     * 获取URL模型集合
     *
     * @param interfaceName
     * @return
     */
    public static URL get(String interfaceName) {
        List<URL> urlList = map.getValues(interfaceName);
        //暂时随机处理
        int randIndex = RandomUtils.nextInt(0, urlList.size());
        return urlList.get(randIndex);
    }

    /**
     * 清空缓存
     */
    public static void clean() {
        map.clear();
    }

}
