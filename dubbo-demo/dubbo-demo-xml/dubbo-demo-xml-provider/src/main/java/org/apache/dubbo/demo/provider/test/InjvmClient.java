package org.apache.dubbo.demo.provider.test;

import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.demo.provider.DemoServiceImpl;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author ziyou.cxf
 * @version : InjvmClient.java, v 0.1 2022年05月27日 9:25 ziyou.cxf Exp $
 */
public class InjvmClient {

    public void test(ConfigurableApplicationContext context) {
        DemoService demoService = (DemoService) context.getBean("inJvmProvider");
        System.out.println(demoService.sayHello("name"));
    }
}
