package org.iubbo.proxy.controller;

import com.alibaba.nacos.client.naming.utils.StringUtils;
import org.iubbo.proxy.common.utils.RedisKeyUtil;
import org.iubbo.proxy.model.dto.*;
import org.iubbo.proxy.model.po.RegisterConfigPO;
import org.iubbo.proxy.model.po.UserPO;
import org.iubbo.proxy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.iubbo.proxy.config.CommonConstants.RegisterTypeEnum.NACOS;
import static org.iubbo.proxy.config.CommonConstants.RegisterTypeEnum.ZOOKEEPER;
import static org.iubbo.proxy.config.CommonConstants.SAMPLE_REQ;

/**
 * 测试平台的invoke控制器
 *
 * @author idea
 * @version V1.0
 * @date 2019/12/19
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

    @Autowired
    private NacosClientService nacosClientService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RegisterConfigService registerConfigService;

    @Autowired
    private DubboInvokeReqRecordService dubboInvokeReqRecordService;

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
        Object resp = dubboInvokeService.doInvoke(param);
        DubboInvokerRespDTO dubboInvokerRespDTO = new DubboInvokerRespDTO();
        dubboInvokerRespDTO.setDubboInvokerParamDTO(param);
        dubboInvokerRespDTO.setResponse(resp);
        return dubboInvokerRespDTO;
    }

    @GetMapping(value = "/get-service-name-list")
    public ResponseDTO<List<String>> getServiceNameList(ServiceAddrDTO serviceAddrDTO) {
        //默认请求zk注册中心
        if (ZOOKEEPER.getName().equals(serviceAddrDTO.getRegisterType()) || StringUtils.isEmpty(serviceAddrDTO.getRegisterType())) {
            return ResponseUtil.successResponse(zookeeperService.getServiceNameList(serviceAddrDTO.getServiceAddr()));
        } else if (NACOS.getName().equals(serviceAddrDTO.getRegisterType())) {
            try {
                return ResponseUtil.successResponse(nacosClientService.getServiceNameList(serviceAddrDTO.getServiceAddr(), serviceAddrDTO.getNamespace()));
            } catch (Exception e) {
                return ResponseUtil.unkownErrorResponse();
            }
        }
        return ResponseUtil.errorParamResponse();
    }

    @PostMapping(value = "/login")
    public ResponseDTO<String> login(@RequestBody UserDTO userDTO) {
        String clientToken = userService.login(userDTO.getUsername(), userDTO.getPassword());
        if (clientToken != null) {
            return ResponseUtil.successResponse(clientToken);
        } else {
            return ResponseUtil.unkownErrorResponse();
        }
    }

    @PostMapping(value = "/get-user-info")
    public ResponseDTO<UserDTO> getUserInfo(@RequestBody UserDTO userDTO) {
        String clientToken = RedisKeyUtil.buildToken(userDTO.getToken());
        UserPO userPO = redisService.getObject(clientToken, UserPO.class);
        return ResponseUtil.successResponse(userPO);
    }

    @PostMapping(value = "/register")
    public ResponseDTO<String> register(@RequestBody UserDTO userDTO) {
        if (userService.isUserNameExist(userDTO.getUsername())) {
            return ResponseUtil.usernameExist();
        }
        UserDTO newUserDTO = userService.register(userDTO.getUsername(), userDTO.getPassword());
        if (newUserDTO != null) {
            String clientToken = userService.login(userDTO.getUsername(), userDTO.getPassword());
            return ResponseUtil.successResponse(clientToken);
        } else {
            return ResponseUtil.unkownErrorResponse();
        }
    }

    @GetMapping(value = "/get-all-register")
    public ResponseDTO<List<RegisterConfigPO>> selectAllRegisterConfig() {
        List<RegisterConfigPO> registerConfigPOS = registerConfigService.selectAll();
        return ResponseUtil.successResponse(registerConfigPOS);
    }

    @GetMapping(value = "/get-all-user")
    public ResponseDTO<List<String>> getAllUsername() {
        return ResponseUtil.successResponse(userService.selectAllUser().stream().map(UserDTO::getUsername).collect(Collectors.toList()));
    }

    /**
     * 转移请求参数
     *
     * @return
     */
    @PostMapping(value = "/change-req-arg")
    public ResponseDTO<Boolean> changeReqArg(@RequestBody ChangeReqArgVO changeReqArgVO) {
        DubboInvokeRespRecordDTO dubboInvokeRespRecordDTO = dubboInvokeReqRecordService.selectOne(changeReqArgVO.getReqArgId());
        if (dubboInvokeRespRecordDTO == null) {
            return ResponseUtil.errorParamResponse("请求的参数id错误");
        }
        UserDTO userDTO = userService.selectByUsername(changeReqArgVO.getUsername());
        if (userDTO == null) {
            return ResponseUtil.errorParamResponse("用户名不存在");
        }
        boolean result = dubboInvokeReqRecordService.copyArgFromOther(changeReqArgVO.getReqArgId(), userDTO.getId());
        return ResponseUtil.successResponse(result);
    }

}
