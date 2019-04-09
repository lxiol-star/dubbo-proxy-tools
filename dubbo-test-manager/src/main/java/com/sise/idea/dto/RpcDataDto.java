package com.sise.idea.dto;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 前端参数模型DTO
 *
 * @author idea
 * @data 2019/4/7
 */
@Data
public class RpcDataDto {

    /**
     * ip和port
     */
    private String address;

    /**
     * service类的名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private Method method;

    /**
     * json格式的参数
     */
    private String paramJson;

}
