package com.sise.idea.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author idea
 * @data 2019/4/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "jar")
public class JarPropModel {

    private String path;
}
