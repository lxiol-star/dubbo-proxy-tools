package org.iubbo.proxy.model.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
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
@TableName(value = "t_dubbo_invoke_req_record")
public class DubboInvokeReqRecordPO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String argJson;

    private Date createTime;

    private Date updateTime;
}
