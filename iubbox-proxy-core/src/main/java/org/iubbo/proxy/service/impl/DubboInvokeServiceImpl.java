package org.iubbo.proxy.service.impl;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.iubbo.proxy.config.ReferenceFactory;
import org.iubbo.proxy.model.dto.DubboInvokerParamDTO;
import org.iubbo.proxy.model.dto.DubboResponseItemDTO;
import org.iubbo.proxy.service.DubboInvokeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static org.iubbo.proxy.config.CommonConstants.SAMPLE_REQ;

/**
 * DUBBO的invoke服务封装
 *
 * @author idea
 * @version V1.0
 * @date 2020/1/2
 */
@Service
@Slf4j
@Scope("prototype")
public class DubboInvokeServiceImpl implements DubboInvokeService {

    private volatile long maxTimeCount = 0;
    private volatile long minTimeCount = 0;

    @Override
    public Map<String, String> getAllParamsMap() {
        return null;
    }

    @Override
    public Object doInvoke(DubboInvokerParamDTO param) {
        AtomicLong totalTime = new AtomicLong(0);
        ReferenceConfig<GenericService> referenceConfig = ReferenceFactory.buildReferenceConfig(param);
        System.out.println(referenceConfig.toString());
        GenericService genericService = referenceConfig.get();
        Map<String, String> attachments = param.getAttachments();
        attachments.put("zone", "master");
        RpcContext.getContext().setAttachments(attachments);
        int count = param.getRequestTimes();
        int parallelTimes = param.getParallelTimes();
        //最高1000次并发请求
        boolean needParallel = parallelTimes > 0 && parallelTimes < 1000;
        Map<String, Object> requestMap = new ConcurrentHashMap<>(count);
        for (int i = 0; i < count; i++) {
            List<DubboResponseItemDTO> dubboResponseDTOList = new CopyOnWriteArrayList<>();
            CountDownLatch beginLatch = new CountDownLatch(1);
            CountDownLatch endLatch = new CountDownLatch(parallelTimes);
            //一次请求也会到这里
            if (needParallel) {
                for (int j = 0; j < parallelTimes; j++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DubboResponseItemDTO dubboResponseDTO = new DubboResponseItemDTO();
                            try {
                                beginLatch.await();

                                long beginTime = System.currentTimeMillis();
                                Object result = genericService.$invoke(param.getMethodName(), param.getArgTypes(), param.getArgObjects());
                                long timeCount = System.currentTimeMillis() - beginTime;
                                //统计耗时
                                dubboResponseDTO.setTimeConsuming((timeCount) + "ms");
                                dubboResponseDTO.setRespData(result);
                                dubboResponseDTO.setRespStatus("success");

                                //统计最大耗时 最小耗时
                                synchronized (this) {
                                    if (maxTimeCount == 0 || timeCount > maxTimeCount) {
                                        maxTimeCount = timeCount;
                                    }
                                    if (minTimeCount == 0 || timeCount < minTimeCount ) {
                                        minTimeCount = timeCount;
                                    }
                                    totalTime.addAndGet(timeCount);
                                }
                            } catch (Exception e) {
                                log.error("[doInvoke] error is {}", e);
                                dubboResponseDTO.setRespData(e.getMessage());
                                dubboResponseDTO.setRespStatus("error");
                            } finally {
                                dubboResponseDTOList.add(dubboResponseDTO);
                                endLatch.countDown();
                            }
                        }
                    }).start();
                }
            }
            beginLatch.countDown();
            try {
                endLatch.await();
            } catch (Exception e) {
                log.error("[doInvoke] error is {}", e);
            }
            requestMap.put(String.valueOf(i), dubboResponseDTOList);
        }
        double avgReqTime = (double) totalTime.get() / (double) (count * parallelTimes);
        Map<String, Object> requestStatistics = new HashMap<>(3);
        requestStatistics.put("avgReqTime", avgReqTime + "ms");
        requestStatistics.put("minReqTime", minTimeCount + "ms");
        requestStatistics.put("maxReqTime", maxTimeCount + "ms");
        maxTimeCount = 0;
        minTimeCount = 0;
        requestMap.put("requestStatistics", requestStatistics);
        return requestMap;
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

        if (param.getRequestTimes() <= 0) {
            param.setRequestTimes(1);
        }

        if (param.getParallelTimes() <= 0) {
            param.setParallelTimes(1);
        }

        //最大并发请求次数 上限防范
        if (param.getParallelTimes() >= 200) {
            param.setParallelTimes(200);
        }
        //最大请求次数 上限防范
        if (param.getRequestTimes() >= 200) {
            param.setRequestTimes(200);
        }
        return null;
    }

}
