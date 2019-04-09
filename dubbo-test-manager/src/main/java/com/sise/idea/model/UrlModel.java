package com.sise.idea.model;

import com.alibaba.dubbo.common.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author idea
 * @data 2019/4/7
 */
@AllArgsConstructor
@Data
public class UrlModel {

    private final String key;

    private final URL url;

}
