package org.iubbo.proxy.service;


import org.iubbo.proxy.model.dto.DubboInvokerParamDTO;

import java.util.Map;

/**
 * invoke dubbo服务
 *
 * @author idea
 * @date 2020/1/2
 * @version V1.0
 */
public interface DubboInvokeService {


    /**
     * 获取所有参数类型
     *
     * @return
     */
    Map<String, String> getAllParamsMap();

    /**
     * 请求dubbo服务端
     *
     * @param param
     * @return
     */
    Object doInvoke(DubboInvokerParamDTO param) ;


    /**
     * 校验dubbo的请求接口
     *
     * @param param
     * @return
     */
    String checkFields(DubboInvokerParamDTO param);


}
