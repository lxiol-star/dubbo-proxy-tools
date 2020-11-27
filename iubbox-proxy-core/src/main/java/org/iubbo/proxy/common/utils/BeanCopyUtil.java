package org.iubbo.proxy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author idea
 * @date 2019/7/30
 * @version V1.0
 */
public class BeanCopyUtil {

    private static final Logger log = LoggerFactory.getLogger(BeanCopyUtil.class);

    public static <T> List copyList(List list, Class clazz) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<Object> newResultList = new ArrayList<>();
        try {
            for (Object source : list) {
                Object newObject = clazz.newInstance();
                BeanUtils.copyProperties(source, newObject);
                newResultList.add(newObject);
            }
        } catch (Exception e) {
            log.error("【BeanCopyUtil】 copyList 出现异常，异常信息为{}", e);
        }
        return newResultList;
    }

}
