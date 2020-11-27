package org.iubbo.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author idea
 * @version V1.0
 * @date 2019/12/19
 */
@SpringBootApplication
@Slf4j
public class DubboInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboInvokerApplication.class, args);
        log.info(" >>>>>>>>>>>>>>>> iubbox-proxy is started  >>>>>>>>>>>>>>>> ");
    }
}
