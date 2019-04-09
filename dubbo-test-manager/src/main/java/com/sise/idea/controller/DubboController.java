package com.sise.idea.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.sise.idea.cache.MethodCache;
import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.service.DubboRpcService;
import com.sise.idea.util.ResponseBuilder;
import com.sise.idea.vo.RpcDataVo;
import com.sise.idea.vo.resp.ResponseMsgVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;

import static com.sise.idea.util.ParamUtil.createJsonParamterStr;

/**
 * 请求dubbo
 *
 * @author idea
 * @data 2019/4/8
 */
@RestController
@RequestMapping(value = "/dubbo")
public class DubboController {

    @Autowired
    private DubboRpcService dubboRpcService;

    @GetMapping(value = "/getParameters")
    public ResponseMsgVO<String> getParameters(String serviceName, Integer id) {
        if (StringUtils.isBlank(serviceName) || id == null) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        List<MethodModelDto> list = MethodCache.getMethodCache(serviceName);

        Method method = findMethodById(list, id);
        if (method != null) {
            return ResponseBuilder.buildSuccessResponVO(createJsonParamterStr(method));
        }
        return ResponseBuilder.buildSuccessResponVO();
    }

    @GetMapping(value = "/sendReq")
    public ResponseMsgVO<String> sendReq(RpcDataVo rpcDataVo) {
        if (StringUtils.isBlank(rpcDataVo.getParamJson()) || rpcDataVo.getId() == null) {
            return ResponseBuilder.buildErrorParamResponVO();
        }
        List<MethodModelDto> list = MethodCache.getMethodCache(rpcDataVo.getServiceName());
        Method method = findMethodById(list, rpcDataVo.getId());
        String result = Strings.EMPTY;
        if (method != null) {
            rpcDataVo.setMethod(method);
            result = dubboRpcService.sendData(rpcDataVo);
        }
        return ResponseBuilder.buildSuccessResponVO(result);
    }


    /**
     * 通过id从缓存里面查找方法
     *
     * @param list
     * @param id
     * @return
     */
    private Method findMethodById(List<MethodModelDto> list, Integer id) {
        Method method = null;
        for (MethodModelDto methodModelDto : list) {
            if (id.equals(methodModelDto.getId())) {
                method = methodModelDto.getMethod();
                return method;
            }
        }
        return null;
    }
}
