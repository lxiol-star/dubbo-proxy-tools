package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DubboInvokeReqRecordDTO {

    private Integer id;

    private Integer userId;

    private String userToken;

    /**
     * 保存的json格式，前端vue用于渲染$data对象
     */
    private String argJson;

    private Date createTime;

    private Date updateTime;

    private Integer page;

    private Integer pageSize;
}
