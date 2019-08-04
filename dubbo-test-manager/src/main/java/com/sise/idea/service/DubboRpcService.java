package com.sise.idea.service;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.remoting.exchange.Request;
import com.alibaba.dubbo.rpc.RpcResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sise.idea.cache.MethodCache;
import com.sise.idea.cache.UrlCache;
import com.sise.idea.client.NettySimpleClient;
import com.sise.idea.common.SysException;
import com.sise.idea.context.ResponseDispatcher;
import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.dto.RpcDataDto;
import com.sise.idea.em.ExceptionEnums;
import com.sise.idea.handler.NettyHandler;
import com.sise.idea.util.ParamUtil;
import com.sise.idea.vo.RpcDataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.sise.idea.util.ParamUtil.*;

/**
 * 通过netty处理ubbo的rpc数据的service
 *
 * @author idea
 * @data 2019/4/7
 */
@Service
@Slf4j
public class DubboRpcService {

    private static final int TIME_OUT = 50;

    /**
     * 发送数据到Dubbo的RPC端
     *
     * @param rpcDataVo
     */
    public String sendData(RpcDataVo rpcDataVo) {
        if (rpcDataVo == null) {
            throw new SysException(ExceptionEnums.PARAM_NOT_NULL);
        }
        RpcDataDto rpcDataDto = new RpcDataDto();
        BeanUtils.copyProperties(rpcDataVo, rpcDataDto);

        //clienturl缺少信息(对于多个provider需要加入额外的处理机制)
        URL clienturl = UrlCache.get(rpcDataDto.getServiceName());

        //设置dubbod的编码器协议类型
        clienturl = clienturl.addParameter(Constants.CODEC_KEY, "dubbo");

        NettySimpleClient nettySimpleClient = new NettySimpleClient(clienturl);
        nettySimpleClient.doConnect();

        List<MethodModelDto> methodModelList = MethodCache.getMethodCache(rpcDataDto.getServiceName());
        if (CollectionUtils.isEmpty(methodModelList)) {

            throw new SysException(ExceptionEnums.INTERFACE_NOT_FOUND);

        } else {

            try {
                // core code
                Map<String, String> map = new HashMap<>();
                map.put(Constants.INTERFACE_KEY, rpcDataDto.getServiceName());
                //简单类型的url
                URL attachUrl = new URL("zookeeper", getHost(rpcDataDto.getAddress()), getPort(rpcDataDto.getAddress()), map);

                return sendCore(nettySimpleClient, attachUrl, rpcDataDto.getMethod());
            } catch (Exception e) {
                log.error("[DubboRpcService]发送请求出现未知异常，异常为{}", e);
            }
        }
        return Strings.EMPTY;
    }

    /**
     * 根据名称查找方法
     *
     * @param methodName
     * @param methodModelList
     * @return
     */
    private Method findMethodByName(String methodName, List<MethodModelDto> methodModelList) {
        Method method = null;
        for (MethodModelDto methodModelDto : methodModelList) {
            if (methodModelDto.getMethodName().equals(methodName)) {
                method = methodModelDto.getMethod();
                return method;
            }
        }
        return null;
    }


    /**
     * netty核心发送数据到rpc端
     *
     * @param client
     * @param url
     * @param method
     * @throws Exception
     */
    private String sendCore(NettySimpleClient client, URL url, Method method) throws Exception {
        HashMap<String, String> attachMap = null;
        attachMap = ParamUtil.getAttachmentFromUrl(url);
        //转换为数组类型的参数
        Object[] params = ParamUtil.parseJson(createJsonParamterStr(method), method);
        Request req = NettyHandler.createDubboRequestObj(method, params, attachMap);

        client.send(req);

        return getResultFromRpc(req, TIME_OUT);
    }


    /**
     * 从rpc那边获取响应数据信息
     *
     * @param request
     * @param timeout
     */
    private String getResultFromRpc(Request request, int timeout) {
        CompletableFuture<RpcResult> future = ResponseDispatcher.getDispatcher().getFuture(request);
        RpcResult result = null;
        try {
            result = future.get(timeout, TimeUnit.SECONDS);
            log.info("【rpc响应数据】" + result.toString());
        } catch (Exception e) {
            log.error("[DubboRpcService],rpc获取数据异常，异常为{}", e);
        }
        ResponseDispatcher.getDispatcher().removeFuture(request);
        if (result == null) {
            return Strings.EMPTY;
        }
        return JSON.toJSONString(result.getValue(), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }
}
