package com.sise.idea.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
@AllArgsConstructor
public class ResponseVO {

    /**
     * 是否成功
     */
    public boolean success;

    /**
     * 响应代码
     */
    public String code;

    /**
     * 响应信息
     */
    public String msg;
}
