package org.iubbo.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


/**
 * @author idea
 * @date 2019/12/19
 * @version V1.0
 */
@ImportResource(value = {"classpath:applicationContext.xml"})
@SpringBootApplication
public class DubboInvokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboInvokerApplication.class, args);
		System.out.println(" >>>>>>>>>>>>>>>> iubbo-proxy is started  >>>>>>>>>>>>>>>> ");
	}

}
