package org.iubbo.proxy.service;


/**
 * @author idea
 * @date 2020/2/26
 * @version V1.0
 */
public interface UserService {

    /**
     * 登录成功返回token
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);
}
