package org.iubbo.proxy.service;


import org.iubbo.proxy.common.wrapper.PageResult;
import org.iubbo.proxy.model.dto.DubboInvokeReqRecordDTO;
import org.iubbo.proxy.model.dto.DubboInvokeRespRecordDTO;

/**
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
public interface DubboInvokeReqRecordService {

    /**
     * 查询请求参数信息记录
     *
     * @param id
     * @return
     */
    DubboInvokeRespRecordDTO selectOne(Integer id);

    /**
     * 获取dubbo的请求参数
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<DubboInvokeRespRecordDTO> selectDubboInokeParam(Integer userId, int page, int pageSize);

    /**
     * 保存调用用例
     *
     * @param dubboInvokeReqRecordDTO
     * @return
     */
    Boolean saveOne(DubboInvokeReqRecordDTO dubboInvokeReqRecordDTO);

    /**
     * 将某个请求参数转移给其他用户
     *
     * @param reqArgId
     * @param userId
     * @return
     */
    Boolean copyArgFromOther(Integer reqArgId, Integer userId);
}
