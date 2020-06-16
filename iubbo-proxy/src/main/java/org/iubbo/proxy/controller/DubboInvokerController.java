package org.iubbo.proxy.controller;

import org.iubbo.proxy.model.dto.DubboInvokerParamDTO;
import org.iubbo.proxy.model.dto.ResponseDTO;
import org.iubbo.proxy.model.dto.ResponseUtil;
import org.iubbo.proxy.model.dto.UserDTO;
import org.iubbo.proxy.service.DubboInvokeService;
import org.iubbo.proxy.service.UserService;
import org.iubbo.proxy.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.iubbo.proxy.config.CommonConstants.SAMPLE_REQ;

/**
 * 测试平台的invoke控制器
 *
 * @author idea
 * @date 2019/12/19
 * @version V1.0
 */
@RestController
@RequestMapping("/dubbo-invoker")
public class DubboInvokerController {


    @Autowired
    private ZookeeperService zookeeperService;

    @Autowired
    private DubboInvokeService dubboInvokeService;

    @Autowired
    private UserService userService;


    @PostMapping("/index")
    @ResponseBody
    public Object index(@RequestBody(required = false) DubboInvokerParamDTO param) {
        if (param == null) {
            return SAMPLE_REQ;
        }

        String errMsg = dubboInvokeService.checkFields(param);

        if (errMsg != null) {
            return errMsg;
        }

        return dubboInvokeService.doInvoke(param);
    }

    @GetMapping(value = "/get-service-name-list")
    public ResponseDTO<List<String>> getServiceNameList(String zkHost) {
        return ResponseUtil.successResponse(zookeeperService.getServiceNameList(zkHost));
    }

    @PostMapping(value = "/login")
    public ResponseDTO<String> login(@RequestBody UserDTO userDTO) {
        String clientToekn = userService.login(userDTO.getUsername(), userDTO.getPassword());
        if (clientToekn != null) {
            return ResponseUtil.successResponse(clientToekn);
        } else {
            return ResponseUtil.unauthorized();
        }
    }

}
