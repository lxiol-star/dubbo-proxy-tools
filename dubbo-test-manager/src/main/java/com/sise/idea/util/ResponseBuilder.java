package com.sise.idea.util;

import com.sise.idea.vo.resp.ResponseMsgVO;
import lombok.Data;

import static com.sise.idea.constants.ResponseConstants.*;


/**
 * 响应构造器
 *
 * @author idea
 * @data 2018/9/22
 */
@Data
public class ResponseBuilder {

    private static ResponseMsgVO SUCCESS_RESPONSE;
    private static ResponseMsgVO ERROR_PARAM_RESPONSE;
    private static ResponseMsgVO UNKOWN_ERROR_RESPONSE;
    private static ResponseMsgVO OVER_TIME_RESPONSE;
    private static ResponseMsgVO AUTH_ERROR_RESPONSE;

    static {
        SUCCESS_RESPONSE = new ResponseMsgVO<>(true, SUCCESS_CODE, MSG_SUCCESS);
        ERROR_PARAM_RESPONSE = new ResponseMsgVO<>(false, ERROR_PARAM_CODE, MSG_ERROR_PARAM);
        UNKOWN_ERROR_RESPONSE = new ResponseMsgVO<>(false, UNKNOW_ERROR_CODE, MSG_UNKNOW_ERROR);
        OVER_TIME_RESPONSE = new ResponseMsgVO<>(false, OVER_TIME_CODE, MSG_OVER_TIME);
        AUTH_ERROR_RESPONSE = new ResponseMsgVO<>(false, AUTH_ERROR_CODE, MSG_AUTH_ERROR);
    }

    public static <T> ResponseMsgVO<T> buildSuccessResponVO() {
        SUCCESS_RESPONSE.setData(null);
        return SUCCESS_RESPONSE;
    }

    public static <T> ResponseMsgVO<T> buildSuccessResponVO(T date) {
        SUCCESS_RESPONSE.setData(date);
        return SUCCESS_RESPONSE;
    }

    public static <T> ResponseMsgVO<T> buildErrorParamResponVO() {
        return ERROR_PARAM_RESPONSE;
    }

    public static <T> ResponseMsgVO<T> buildUnkownErrorResponseVO() {
        return UNKOWN_ERROR_RESPONSE;
    }

    public static <T> ResponseMsgVO<T> buildOverTimeResponseVO() {
        return OVER_TIME_RESPONSE;
    }

    public static <T> ResponseMsgVO<T> buildAuthErrorResponseVO() {
        return AUTH_ERROR_RESPONSE;
    }

}
