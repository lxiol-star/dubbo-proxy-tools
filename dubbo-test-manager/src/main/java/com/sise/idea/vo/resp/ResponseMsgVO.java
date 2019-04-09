package com.sise.idea.vo.resp;

import lombok.Data;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
public class ResponseMsgVO<T> extends ResponseVO {


    /**
     * 响应数据
     */
    public T data;

    public ResponseMsgVO(boolean success, String code, String msg) {
        super(success, code, msg);
        this.data = null;
    }

}
