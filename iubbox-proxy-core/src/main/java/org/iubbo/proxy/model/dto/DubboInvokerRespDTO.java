package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author idea
 * @Date created in 4:47 下午 2020/11/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DubboInvokerRespDTO {

    /**
     * 请求dubbo的参数
     */
    private DubboInvokerParamDTO dubboInvokerParamDTO;

    /**
     * 响应结果值
     */
    private Object response;
}
