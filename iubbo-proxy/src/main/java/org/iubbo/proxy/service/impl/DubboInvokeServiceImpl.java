package org.iubbo.proxy.service.impl;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.iubbo.proxy.config.ReferenceFactory;
import org.iubbo.proxy.model.dto.DubboInvokerParamDTO;
import org.iubbo.proxy.service.DubboInvokeService;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.iubbo.proxy.config.CommonConstants.SAMPLE_REQ;

/**
 * DUBBO的invoke服务封装
 *
 * @author idea
 * @date 2020/1/2
 * @version V1.0
 */
@Service
public class DubboInvokeServiceImpl implements DubboInvokeService {


    @Override
    public Map<String, String> getAllParamsMap() {
        return null;
    }

    @Override
    public Object doInvoke(DubboInvokerParamDTO param) {
        ReferenceConfig<GenericService> referenceConfig = ReferenceFactory.buildReferenceConfig(param);
        GenericService genericService = referenceConfig.get();
        Map<String, String> attachments = param.getAttachments();
        if (attachments != null) {
            RpcContext.getContext().setAttachments(attachments);
        }
        Object result = genericService.$invoke(param.getMethodName(), param.getArgTypes(), param.getArgObjects());
        return result;
    }

    @Override
    public String checkFields(DubboInvokerParamDTO param) {
        if (param.getInterfaceName() == null) {
            return "interfaceName is Required, sample request = \n" + SAMPLE_REQ;
        }

        if (param.getMethodName() == null) {
            return "methodName is Required, sample request = \n" + SAMPLE_REQ;
        }

        if (param.getArgTypes() == null) {
            param.setArgTypes(new String[]{});
        }

        if (param.getArgObjects() == null) {
            param.setArgObjects(new Object[]{});
        }

        if (param.getArgTypes().length != param.getArgObjects().length) {
            return "paramTypes.length is not equal to paramArgs.length, sample request = \n" + SAMPLE_REQ;
        }

        return null;
    }

}
