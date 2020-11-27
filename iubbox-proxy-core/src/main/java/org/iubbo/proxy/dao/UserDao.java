

package org.iubbo.proxy.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.iubbo.proxy.model.po.UserPO;

import java.util.List;

/**
 * @author idea
 * @date 2020/2/26
 * @version V1.0
 */
@Mapper
public interface UserDao extends BaseMapper<UserPO> {

    @Select("select count(1) from t_user where username=#{username} and password=#{password}")
    Integer login(@Param("username") String username, @Param("password") String password);

    @Select("select * from t_user where username=#{username} and password=#{password} limit 1")
    UserPO selectByUsernameAndPwd(@Param("username") String username, @Param("password") String password);

    @Select("select * from t_user where username=#{username}")
    UserPO selectByUsername(@Param("username") String username);

    @Select("select count(1) from t_user where username=#{username} limit 1")
    Integer selectCountByUsername(@Param("username") String username);

    @Select("select * from t_user")
    List<UserPO> selectAll();
}
