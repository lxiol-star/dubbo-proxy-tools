package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author idea
 * @Date created in 4:09 下午 2020/11/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DubboResponseItemDTO {

    /**
     * 响应数据
     */
    private Object respData;

    /**
     * 耗时 单位ms
     */
    private String timeConsuming;

    /**
     * 请求响应状态
     */
    private String respStatus;
}
