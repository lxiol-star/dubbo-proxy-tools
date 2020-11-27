package org.iubbo.proxy.service;


import org.iubbo.proxy.model.dto.UserDTO;

import java.util.List;

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

    /**
     * 查询所有的用户信息
     *
     * @return
     */
    List<UserDTO> selectAllUser();

    /**
     * 根据用户名进行查询
     *
     * @param username
     * @return
     */
    UserDTO selectByUsername(String username);


    /**
     * 账号名称是否存在
     *
     * @param username
     * @return
     */
    boolean isUserNameExist(String username);

    /**
     * 注册账号
     *
     * @param username
     * @param password
     * @return
     */
    UserDTO register(String username, String password);
}
