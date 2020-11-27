package org.iubbo.proxy.service;

import org.iubbo.proxy.model.po.RegisterConfigPO;

import java.util.List;

/**
 * @Author idea
 * @Date created in 7:42 下午 2020/11/23
 */
public interface RegisterConfigService {

    /**
     * 查询所有注册中心列表
     *
     * @return
     */
    List<RegisterConfigPO> selectAll();
}
