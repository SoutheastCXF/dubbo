package org.apache.dubbo.springboot.demo.provider;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @author ziyou.cxf
 * @version : MyProviderFilter.java, v 0.1 2022年06月04日 12:23 ziyou.cxf Exp $
 */
@Activate(group = CommonConstants.PROVIDER)
public class MyProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        System.out.println(JSONObject.toJSONString(invocation));

        Result invoke = invoker.invoke(invocation);

        return invoke;
    }
}
