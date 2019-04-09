package com.sise.idea.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 方法模型dto
 *
 * @author idea
 * @data 2019/4/4
 */
@NoArgsConstructor
@Data
public class MethodModelDto {

    private int id;

    private String interfaceName;

    private String methodName;

    private Method method;

    private List<Parameter> parameters;

    private String des;

}
