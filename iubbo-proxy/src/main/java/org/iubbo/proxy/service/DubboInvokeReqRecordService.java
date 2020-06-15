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
}
