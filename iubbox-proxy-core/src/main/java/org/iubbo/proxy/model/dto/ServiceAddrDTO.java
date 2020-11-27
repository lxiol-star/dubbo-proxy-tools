/*
 * Copyright (C), 2005-2019, 深圳市珍爱网信息技术有限公司
 */

package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linhao
 * @version V1.0
 * @date 2020/7/2
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceAddrDTO {

    private String serviceAddr;

    /**
     * 前端传输为字符串类型
     */
    private String registerType;

    /**
     * 针对nacos有用
     */
    private String namespace;
}
