package com.sise.idea.em;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author idea
 * @data 2019/4/7
 */
@AllArgsConstructor
@Getter
public enum  ExceptionEnums {

    PARAM_NOT_NULL("参数不能为空",400),

    INTERFACE_NOT_FOUND("接口查找异常",402),

    SERVER_UNKOWN_EXCEPTION("服务端位置异常",500),

    SERVER_CONNECT_FAIL("服务器链接异常",505);

    private String msg;

    private int code;
}
