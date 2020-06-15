package org.iubbo.proxy.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iubbo.proxy.model.po.DubboInvokeReqRecordPO;

/**
 * 记录dubbo的请求信息
 *
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
@Mapper
public interface DubboInvokeReqRecordDao extends BaseMapper<DubboInvokeReqRecordPO> {

}
