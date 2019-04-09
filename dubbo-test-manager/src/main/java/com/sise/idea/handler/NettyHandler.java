package com.sise.idea.handler;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.sise.idea.client.NettySimpleClient;
import com.sise.idea.model.MethodModel;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

/**
 * netty发送数据到dubbo rpc那边的核心处理器
 *
 * @author idea
 * @data 2019/4/7
 */
public class NettyHandler {


    /**
     * 创建发送到dubbo那边的request对象
     *
     * @return
     */
    public static Request createDubboRequestObj(Method method, Object[] params, HashMap<String, String> map) {

        Request req = new Request();
        req.setVersion("2.0.0");
        req.setTwoWay(true);
        req.setData(new RpcInvocation(method, params, map));
        return req;
    }



}
