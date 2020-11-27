package org.iubbo.proxy.model.dto;


import static org.iubbo.proxy.common.constants.ResponseDTOConstants.*;

/**
 * @author idea
 * @date 2019/7/17
 * @version V1.0
 */
public class ResponseUtil {

    /**
     * 响应成功
     *
     * @return
     */
    public static ResponseDTO successResponse() {
        return new ResponseDTO(CODE_SUCCESS, MSG_SUCCESS, null);
    }

    /**
     * 响应成功
     *
     * @param data 响应数据
     * @return
     */
    public static <T> ResponseDTO successResponse(T data) {
        return new ResponseDTO(CODE_SUCCESS, MSG_SUCCESS, data);
    }

    /**
     * 参数异常
     *
     * @return
     */
    public static ResponseDTO errorParamResponse() {
        return new ResponseDTO(CODE_PARAM_ERROR, PARAM_ERROR, null);
    }

    /**
     * 参数异常
     *
     * @param data 响应数据
     * @return
     */
    public static ResponseDTO errorParamResponse(Object data) {
        return new ResponseDTO(CODE_PARAM_ERROR, PARAM_ERROR, data);
    }

    /**
     * 未知异常
     *
     * @return
     */
    public static ResponseDTO unkownErrorResponse() {
        return new ResponseDTO(CODE_UNKOWN_ERROR, UNKOWN_ERROR, null);
    }

    /**
     * 响应超时
     *
     * @return
     */
    public static ResponseDTO timeOutResponse() {
        return new ResponseDTO(CODE_TIME_OUT, TIME_OUT, null);
    }

    /**
     * 找不到相关资源
     *
     * @return
     */
    public static ResponseDTO notFoundResponse() {
        return new ResponseDTO(CODE_NOT_FOUND, NOT_FOUND, null);
    }

    /**
     * 无权限访问
     *
     * @return
     */
    public static ResponseDTO unauthorized() {
        return new ResponseDTO(CODE_UN_AUTHORIZED, MSG_UN_AUTHORIZED, null);
    }

    /**
     * 无权限访问
     *
     * @return
     */
    public static ResponseDTO usernameExist() {
        return new ResponseDTO(USERNAME_EXIST, MSG_USERNAME_EXIST, null);
    }
}
