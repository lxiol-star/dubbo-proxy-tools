package com.sise.idea.vo;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 前端参数模型VO
 *
 * @author idea
 * @data 2019/4/7
 */
@Data
public class RpcDataVo {

    private Integer id;

    /**
     * ip和port
     */
    private String address;

    /**
     * service类的名称
     */
    private String serviceName;

    /**
     * 方法
     */
    private Method method;

    /**
     * json格式的参数
     */
    private String paramJson;

}
