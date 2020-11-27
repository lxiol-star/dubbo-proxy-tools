package org.iubbo.proxy.model.dto;

import com.alibaba.nacos.api.naming.pojo.ServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author idea
 * @Date created in 1:59 下午 2020/6/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfoResult implements Serializable {

    private List<ServiceInfo> serviceList;

    private Integer count;
}
