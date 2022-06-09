package org.apache.dubbo.springboot.demo.consumer;

import org.springframework.stereotype.Service;

/**
 * @author ziyou.cxf
 * @version : ResultCallBack.java, v 0.1 2022年06月08日 21:48 ziyou.cxf Exp $
 */
@Service
public class ResultCallBack {

    public void doInvoker(String message) {
        System.out.println("doInvoker message");
    }

    public void doReturn(String response) {
        System.out.println("doReturn response");

    }

    public void doThrow(Throwable throwable) {
        System.out.println("doThrow throwable");
    }
}
