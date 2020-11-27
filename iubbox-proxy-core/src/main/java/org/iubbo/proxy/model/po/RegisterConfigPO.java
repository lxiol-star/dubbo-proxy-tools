package org.iubbo.proxy.model.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author idea
 * @Date created in 7:02 下午 2020/11/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_register_config")
public class RegisterConfigPO {

    @TableId(type = IdType.AUTO)
    private int id;

    private String host;

    private String ip;

    /**
     * 1 ZK
     * 2 NACOS
     */
    private Integer type;

    private Date createTime;

    private Date updateTime;
}
