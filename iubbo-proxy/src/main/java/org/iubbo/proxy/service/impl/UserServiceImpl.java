

package org.iubbo.proxy.service.impl;

import org.iubbo.proxy.common.utils.RedisKeyUtil;
import org.iubbo.proxy.dao.UserDao;
import org.iubbo.proxy.model.po.UserPO;
import org.iubbo.proxy.service.RedisService;
import org.iubbo.proxy.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author idea
 * @date 2020/2/26
 * @version V1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private RedisService redisService;


    @Override
    public String login(String username, String password) {
        UserPO userPO = userDao.selectByUsernameAndPwd(username, password);
        if (userPO != null) {
            String randStr = UUID.randomUUID().toString();
            String clientToken = RedisKeyUtil.buildToken(randStr);
            redisService.setObject(clientToken, userPO, 60L, TimeUnit.DAYS);
            return randStr;
        } else {
            return null;
        }
    }
}
