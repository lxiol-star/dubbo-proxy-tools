package com.sise.idea.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sise.idea.cache.MethodCache;
import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.service.ZookeeperService;
import com.sise.idea.task.ZookeeperTask;
import com.sise.idea.util.ResponseBuilder;
import com.sise.idea.vo.resp.MethodModelVo;
import com.sise.idea.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 请求zk的数据信息
 *
 * @author idea
 * @data 2019/4/8
 */
@RestController
@RequestMapping(value = "/zk")
@Slf4j
public class ZookeeperController {


    @Autowired
    private ZookeeperService zookeeperService;

    @GetMapping(value = "/add/zkServer")
    public ResponseMsgVO<List<String>> addZkServer(String address) {
        if (StringUtils.isBlank(address)) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        List<String> serviceNameList = zookeeperService.getServiceNameList(address);
        return ResponseBuilder.buildSuccessResponVO(serviceNameList);
    }


    @GetMapping(value = "/getServiceDetail")
    public ResponseMsgVO<List<MethodModelVo>> getServiceDetail(String serviceName) {
        List<MethodModelDto> list = MethodCache.getMethodCache(serviceName);
        List<MethodModelVo> resultList = new ArrayList<>();
        for (MethodModelDto methodModelDto : list) {
            MethodModelVo methodModelVo = new MethodModelVo();
            BeanUtils.copyProperties(methodModelDto, methodModelVo);
            resultList.add(methodModelVo);
        }
        return ResponseBuilder.buildSuccessResponVO(resultList);
    }


}
