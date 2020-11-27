package org.iubbo.proxy.service.impl;

import org.iubbo.proxy.dao.RegisterConfigDao;
import org.iubbo.proxy.model.po.RegisterConfigPO;
import org.iubbo.proxy.service.RegisterConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author idea
 * @Date created in 7:45 下午 2020/11/23
 */
@Service
public class RegisterConfigServiceImpl implements RegisterConfigService {

    @Resource
    private RegisterConfigDao registerConfigDao;

    @Override
    public List<RegisterConfigPO> selectAll() {
        return registerConfigDao.selectAll();
    }
}
