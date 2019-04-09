package com.sise.idea.constants;

import lombok.Data;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
public class ResponseConstants {

    public static final String SUCCESS_CODE="000000";
    public static final String MSG_SUCCESS="操作成功";

    public static final String ERROR_PARAM_CODE="666666";
    public static final String MSG_ERROR_PARAM="参数错误";

    public static final String UNKNOW_ERROR_CODE="999999";
    public static final String MSG_UNKNOW_ERROR="服务器未知异常";

    public static final String AUTH_ERROR_CODE="777777";
    public static final String MSG_AUTH_ERROR="未登录认证";

    public static final String OVER_TIME_CODE="888888";
    public static final String MSG_OVER_TIME="请求超时";
}
