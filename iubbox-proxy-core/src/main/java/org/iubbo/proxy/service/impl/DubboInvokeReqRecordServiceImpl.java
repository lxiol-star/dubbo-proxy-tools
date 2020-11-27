package org.iubbo.proxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.iubbo.proxy.common.wrapper.PageResult;
import org.iubbo.proxy.dao.DubboInvokeReqRecordDao;
import org.iubbo.proxy.model.dto.DubboInvokeReqRecordDTO;
import org.iubbo.proxy.model.dto.DubboInvokeRespRecordDTO;
import org.iubbo.proxy.model.po.DubboInvokeReqRecordPO;
import org.iubbo.proxy.service.DubboInvokeReqRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author idea
 * @date 2020/3/1
 * @version V1.0
 */
@Service
public class DubboInvokeReqRecordServiceImpl implements DubboInvokeReqRecordService {

    @Resource
    private DubboInvokeReqRecordDao dubboInvokeReqRecordDao;

    @Override
    public DubboInvokeRespRecordDTO selectOne(Integer id) {
        DubboInvokeReqRecordPO dubboInvokeReqRecordPO = dubboInvokeReqRecordDao.selectByReqId(id);
        if(dubboInvokeReqRecordPO == null){
            return null;
        }
        DubboInvokeRespRecordDTO dubboInvokeRespRecordDTO = new DubboInvokeRespRecordDTO();
        BeanUtils.copyProperties(dubboInvokeReqRecordPO,dubboInvokeRespRecordDTO);
        return dubboInvokeRespRecordDTO;
    }

    @Override
    public PageResult<DubboInvokeRespRecordDTO> selectDubboInokeParam(Integer userId, int page, int pageSize) {
        EntityWrapper<DubboInvokeReqRecordPO> entityWrapper = new EntityWrapper();
        DubboInvokeReqRecordPO dubboInvokeReqRecordPO = new DubboInvokeReqRecordPO();
        dubboInvokeReqRecordPO.setUserId(userId);
        entityWrapper.setEntity(dubboInvokeReqRecordPO);

        new PageResult<>();

        List<DubboInvokeReqRecordPO> dubboInvokeReqRecordPOS = dubboInvokeReqRecordDao.selectPage(new Page<DubboInvokeReqRecordPO>(page, pageSize), entityWrapper);
        List<DubboInvokeRespRecordDTO> dubboInvokeRespRecordDTOS = new ArrayList<>();
        for (DubboInvokeReqRecordPO invokeReqRecordPO : dubboInvokeReqRecordPOS) {
            DubboInvokeRespRecordDTO dubboInvokeRespRecordDTO = new DubboInvokeRespRecordDTO();
            try {
                String argJson = invokeReqRecordPO.getArgJson();
                JSONObject jsonObject = JSONObject.parseObject(argJson);
                JSONObject reqArg = jsonObject.getObject("reqArg", JSONObject.class);

                String interfaceName = reqArg.getString("interfaceName");
                String methodName = reqArg.getString("methodName");
                String argObjects = reqArg.getString("argObjects");
                String argTypes = reqArg.getString("argTypes");
                dubboInvokeRespRecordDTO.setId(invokeReqRecordPO.getId());
                dubboInvokeRespRecordDTO.setArgJson(invokeReqRecordPO.getArgJson());
                dubboInvokeRespRecordDTO.setArgDetail("argTypes:" + argTypes + "  argObjects:" + argObjects);
                dubboInvokeRespRecordDTO.setMethodName(interfaceName + "#" + methodName);
                dubboInvokeRespRecordDTOS.add(dubboInvokeRespRecordDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Integer total = dubboInvokeReqRecordDao.selectCount(entityWrapper);
        PageResult<DubboInvokeRespRecordDTO> pageResult = PageResult.buildPageResult(page, pageSize, dubboInvokeRespRecordDTOS, total);
        return pageResult;
    }

    @Override
    public Boolean saveOne(DubboInvokeReqRecordDTO dubboInvokeReqRecordDTO) {
        DubboInvokeReqRecordPO dubboInvokeReqRecordPO = new DubboInvokeReqRecordPO();
        BeanUtils.copyProperties(dubboInvokeReqRecordDTO, dubboInvokeReqRecordPO);
        int result = dubboInvokeReqRecordDao.insert(dubboInvokeReqRecordPO);
        return result > 0;
    }

    @Override
    public Boolean copyArgFromOther(Integer reqArgId,Integer userId){
        DubboInvokeReqRecordPO dubboInvokeReqRecordPO = dubboInvokeReqRecordDao.selectById(reqArgId);
        dubboInvokeReqRecordPO.setUserId(userId);
        int result = dubboInvokeReqRecordDao.insert(dubboInvokeReqRecordPO);
        return result > 0;
    }


}
