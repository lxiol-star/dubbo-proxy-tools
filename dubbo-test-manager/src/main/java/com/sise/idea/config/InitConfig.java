package com.sise.idea.config;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.sise.idea.cache.UrlCache;
import com.sise.idea.handler.JarHandler;
import com.sise.idea.handler.ZookeeperHandler;
import com.sise.idea.model.JarPropModel;
import com.sise.idea.model.ServiceModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动类之前所必需开启的配置
 *
 * @author idea
 * @data 2019/4/7
 */
@Component
@Slf4j
public class InitConfig  {


    @Autowired
    private JarPropModel jarPropModel;

    public void run(String... args) throws Exception {
        //加载class的信息到缓存里面
        JarHandler jarHandler = new JarHandler();
        log.info("[InitConfig] 加载Jar-class成功！");
    }
}
