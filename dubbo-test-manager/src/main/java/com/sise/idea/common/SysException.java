package com.sise.idea.common;

import com.sise.idea.em.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 公共异常
 *
 * @author idea
 * @data 2019/4/7
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SysException extends RuntimeException{

    private ExceptionEnums exceptionEnums;

}
