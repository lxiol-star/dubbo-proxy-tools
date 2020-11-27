package org.iubbo.proxy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author idea
 * @Date created in 5:17 下午 2020/11/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeReqArgVO {

    private Integer reqArgId;

    private String username;
}
