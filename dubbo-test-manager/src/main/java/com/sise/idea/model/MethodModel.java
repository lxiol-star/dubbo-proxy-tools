package com.sise.idea.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 方法模型
 *
 * @author idea
 * @data 2019/4/4
 */
@Getter
@AllArgsConstructor
public class MethodModel {

    private int id;

    /**
     * 所属类名
     */
    private final String key;

    /**
     * 方法描述：名称+参数
     */
    private final Method method;


}
