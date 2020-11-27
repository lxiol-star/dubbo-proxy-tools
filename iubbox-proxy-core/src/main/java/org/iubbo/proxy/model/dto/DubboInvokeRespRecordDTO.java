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
public class DubboInvokeRespRecordDTO {

    private Integer id;

    private Integer userId;

    /**
     * 保存的json格式，前端vue用于渲染$data对象
     */
    private String methodName;

    private String argDetail;

    private String argJson;

    private Date createTime;

    private Date updateTime;
}
