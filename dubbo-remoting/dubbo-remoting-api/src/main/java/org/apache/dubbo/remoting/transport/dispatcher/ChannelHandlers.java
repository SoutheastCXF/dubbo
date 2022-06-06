/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.remoting.transport.dispatcher;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Dispatcher;
import org.apache.dubbo.remoting.exchange.support.header.HeartbeatHandler;
import org.apache.dubbo.remoting.transport.MultiMessageHandler;

public class ChannelHandlers {

    private static ChannelHandlers INSTANCE = new ChannelHandlers();

    protected ChannelHandlers() {
    }

    // 执行Dubbo的线程模型
    public static ChannelHandler wrap(ChannelHandler handler, URL url) {
        return ChannelHandlers.getInstance().wrapInternal(handler, url);
    }

    protected static ChannelHandlers getInstance() {
        return INSTANCE;
    }

    static void setTestingChannelHandlers(ChannelHandlers instance) {
        INSTANCE = instance;
    }

    // 内部包装器
    // netty的channelHandler被包装了好几层
    protected ChannelHandler wrapInternal(ChannelHandler handler, URL url) {
        return new MultiMessageHandler(
            new HeartbeatHandler(
                // 获取相应的DispatchHandler，其中该DispatchHandler是一个包装类，真正执行netty传输的，还是需要依靠传入的handler
                url.getOrDefaultFrameworkModel().getExtensionLoader(Dispatcher.class)
                    .getAdaptiveExtension()
                    .dispatch(handler, url)
            )
        );
    }
}
