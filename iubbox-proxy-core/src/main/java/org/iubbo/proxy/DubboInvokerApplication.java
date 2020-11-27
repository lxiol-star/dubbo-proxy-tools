package org.iubbo.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author idea
 * @version V1.0
 * @date 2019/12/19
 */
@SpringBootApplication
public class DubboInvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboInvokerApplication.class, args);
        System.out.println(" >>>>>>>>>>>>>>>> iubbo-proxy is started  >>>>>>>>>>>>>>>> ");
    }
}
