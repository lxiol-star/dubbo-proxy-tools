package org.iubbo.proxy.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.iubbo.proxy.model.po.RegisterConfigPO;

import java.util.List;

/**
 * @Author idea
 * @Date created in 7:41 下午 2020/11/23
 */
@Mapper
public interface RegisterConfigDao extends BaseMapper<RegisterConfigPO> {

    @Select("select * from t_register_config")
    List<RegisterConfigPO> selectAll();
}
