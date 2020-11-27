package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -8663135421118923399L;

    private Integer id;

    private String username;

    private String password;

    private String token;

    private Date createTime;

    private Date updateTime;
}
