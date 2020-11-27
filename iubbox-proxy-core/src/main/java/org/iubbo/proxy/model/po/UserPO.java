package org.iubbo.proxy.model.po;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登录使用用户
 *
 * @author idea
 * @date 2020/2/26
 * @version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_user")
public class UserPO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;
    
}
