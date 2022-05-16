# Apache Dubbo Project

[![Build Status](https://github.com/apache/dubbo/workflows/Build%20and%20Test%20For%20Dubbo%203/badge.svg?branch=3.0)](https://github.com/apache/dubbo/actions/workflows/build-and-test-3.yml?query=branch%3A3.0)
[![Build Status](https://travis-ci.com/apache/dubbo.svg?branch=master)](https://travis-ci.com/apache/dubbo)
[![Codecov](https://codecov.io/gh/apache/dubbo/branch/3.0/graph/badge.svg)](https://codecov.io/gh/apache/dubbo)
![Maven](https://img.shields.io/maven-central/v/org.apache.dubbo/dubbo.svg)
![License](https://img.shields.io/github/license/alibaba/dubbo.svg)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/apache/dubbo.svg)](http://isitmaintained.com/project/apache/dubbo "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/apache/dubbo.svg)](http://isitmaintained.com/project/apache/dubbo "Percentage of issues still open")
[![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Apache%20Dubbo%20is%20a%20high-performance%2C%20java%20based%2C%20open%20source%20RPC%20framework.&url=http://dubbo.apache.org/&via=ApacheDubbo&hashtags=rpc,java,dubbo,micro-service)
[![Twitter Follow](https://img.shields.io/twitter/follow/ApacheDubbo.svg?label=Follow&style=social&logoWidth=0)](https://twitter.com/intent/follow?screen_name=ApacheDubbo)
[![Gitter](https://badges.gitter.im/alibaba/dubbo.svg)](https://gitter.im/alibaba/dubbo?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Apache Dubbo is a high-performance, Java-based open-source RPC framework. Please visit the [official site](http://dubbo.apache.org) for the quick start guide and documentation, as well as the [wiki](https://github.com/apache/dubbo/wiki) for news, FAQ, and release notes.

We are now collecting Dubbo user info to help us to improve Dubbo further. Kindly support us by providing your usage information on [issue#1012: Wanted: who's using dubbo](https://github.com/apache/dubbo/issues/1012), thanks :)

## Architecture

![Architecture](https://dubbo.apache.org/imgs/architecture.png)

## Features

* Transparent interface based RPC
* Intelligent load balancing
* Automatic service registration and discovery
* High extensibility
* Runtime traffic routing
* Visualized service governance

## Getting started

The following code snippet comes from [Dubbo Samples](https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-api). You may clone the sample project and step into the `dubbo-samples-api` subdirectory before proceeding.

```bash
# git clone https://github.com/apache/dubbo-samples.git
# cd dubbo-samples/dubbo-samples-api
```

There's a [README](https://github.com/apache/dubbo-samples/tree/master/dubbo-samples-api/README.md) file under `dubbo-samples-api` directory. We recommend referencing the samples in that directory by following the below-mentioned instructions: 

### Maven dependency

```xml
<properties>
    <dubbo.version>3.0.4</dubbo.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo</artifactId>
        <version>${dubbo.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-dependencies-zookeeper</artifactId>
        <version>${dubbo.version}</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

### Define service interfaces

```java
package org.apache.dubbo.samples.api;

public interface GreetingsService {
    String sayHi(String name);
}
```

*See [api/GreetingsService.java](https://github.com/apache/dubbo-samples/blob/master/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/api/GreetingsService.java) on GitHub.*

### Implement service interface for the provider

```java
package org.apache.dubbo.samples.provider;

import org.apache.dubbo.samples.api.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}
```

*See [provider/GreetingsServiceImpl.java](https://github.com/apache/dubbo-samples/blob/master/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/provider/GreetingsServiceImpl.java) on GitHub.*

### Start service provider

```java
package org.apache.dubbo.samples.provider;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.samples.api.GreetingsService;

import java.util.concurrent.CountDownLatch;

public class Application {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setApplication(new ApplicationConfig("first-dubbo-provider"));
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());
        service.export();

        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}
```

*See [provider/Application.java](https://github.com/apache/dubbo-samples/blob/master/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/provider/Application.java) on GitHub.*

### Build and run the provider

```bash
# mvn clean package
# mvn -Djava.net.preferIPv4Stack=true -Dexec.mainClass=org.apache.dubbo.samples.provider.Application exec:java
```

### Call remote service in the consumer

```java
package org.apache.dubbo.samples.client;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.samples.api.GreetingsService;

public class Application {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "127.0.0.1");

    public static void main(String[] args) {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingsService.class);
        GreetingsService service = reference.get();
        String message = service.sayHi("dubbo");
        System.out.println(message);
    }
}
```
*See [consumer/Application.java](https://github.com/apache/dubbo-samples/blob/master/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/client/Application.java) on GitHub.*

### Build and run the consumer

```bash
# mvn clean package
# mvn -Djava.net.preferIPv4Stack=true -Dexec.mainClass=org.apache.dubbo.samples.client.Application exec:java
```

The consumer will print out `hi, dubbo` on the screen.


### Next steps

* [Your first Dubbo application](https://dubbo.apache.org/blog/2018/08/07/dubbo-101/) - A 101 tutorial to reveal more details, with the same code above.
* [Dubbo user manual](https://dubbo.apache.org/docs/v2.7/user/preface/background/) - How to use Dubbo and all its features.
* [Dubbo developer guide](https://dubbo.apache.org/docs/v2.7/dev/build/) - How to involve in Dubbo development.
* [Dubbo admin manual](https://dubbo.apache.org/docs/v2.7/admin/install/provider-demo/) - How to admin and manage Dubbo services.

## Building

If you want to try out the cutting-edge features, you can build with the following commands. (Java 1.8 is needed to build the master branch)

```
  mvn clean install
```

## Contact

* Mailing list:
  * dev list: for dev/user discussion. [subscribe](mailto:dev-subscribe@dubbo.apache.org), [unsubscribe](mailto:dev-unsubscribe@dubbo.apache.org), [archive](https://lists.apache.org/list.html?dev@dubbo.apache.org),  [guide](https://github.com/apache/dubbo/wiki/Mailing-list-subscription-guide)

* Bugs: [Issues](https://github.com/apache/dubbo/issues/new?template=dubbo-issue-report-template.md)
* Gitter: [Gitter channel](https://gitter.im/alibaba/dubbo)
* Twitter: [@ApacheDubbo](https://twitter.com/ApacheDubbo)

## Contributing

See [CONTRIBUTING](https://github.com/apache/dubbo/blob/master/CONTRIBUTING.md) for details on submitting patches and the contribution workflow.

### How can I contribute?

* Take a look at issues with tags marked [`Good first issue`](https://github.com/apache/dubbo/issues?q=is%3Aopen+is%3Aissue+label%3A%22good+first+issue%22) or [`Help wanted`](https://github.com/apache/dubbo/issues?q=is%3Aopen+is%3Aissue+label%3A%22help+wanted%22).
* Join the discussion on the mailing list, subscription [guide](https://github.com/apache/dubbo/wiki/Mailing-list-subscription-guide).
* Answer questions on [issues](https://github.com/apache/dubbo/issues).
* Fix bugs reported on [issues](https://github.com/apache/dubbo/issues), and send us a pull request.
* Review the existing [pull request](https://github.com/apache/dubbo/pulls).
* Improve the [website](https://github.com/apache/dubbo-website), typically we need
  * blog post
  * translation on documentation
  * use cases around the integration of Dubbo in enterprise systems.
* Improve the [dubbo-admin/dubbo-monitor](https://github.com/apache/dubbo-admin).
* Contribute to the projects listed in [ecosystem](https://github.com/dubbo).
* Other forms of contribution not explicitly enumerated above.
* If you would like to contribute, please send an email to dev@dubbo.apache.org to let us know!

## Reporting bugs

Please follow the [template](https://github.com/apache/dubbo/issues/new?template=dubbo-issue-report-template.md) for reporting any issues.

## Reporting a security vulnerability

Please report security vulnerabilities to [us](mailto:security@dubbo.apache.org) privately.

## Dubbo ecosystem

* [Dubbo Ecosystem Entry](https://github.com/apache?utf8=%E2%9C%93&q=dubbo&type=&language=) - A GitHub group `dubbo` to gather all Dubbo relevant projects not appropriate in [apache](https://github.com/apache) group yet
* [Dubbo Website](https://github.com/apache/dubbo-website) - Apache Dubbo official website
* [Dubbo Samples](https://github.com/apache/dubbo-samples) - samples for Apache Dubbo
* [Dubbo Spring Boot](https://github.com/apache/dubbo-spring-boot-project) - Spring Boot Project for Dubbo
* [Dubbo Admin](https://github.com/apache/dubbo-admin) - The reference implementation for Dubbo admin
* [Dubbo Awesome](https://github.com/apache/dubbo-awesome) - Dubbo's slides and video links in Meetup

#### Language

* [Go](https://github.com/dubbo/dubbo-go) (recommended)
* [Node.js](https://github.com/apache/dubbo-js)
* [Python](https://github.com/dubbo/py-client-for-apache-dubbo)
* [PHP](https://github.com/apache/dubbo-php-framework)
* [Erlang](https://github.com/apache/dubbo-erlang)

## License

Apache Dubbo software is licenced under the Apache License Version 2.0. See the [LICENSE](https://github.com/apache/dubbo/blob/master/LICENSE) file for details.

## 各模块的作用
### dubbo-common
公共逻辑子项目，定义了各子项目中通用的组件的工具类，如：IO、日志、配置处理等。
### dubbo-rpc
分布式协调服务框架的核心，该模块定义了 RPC 相关的组件，包括 服务发布、服务调用代理、远程调用结果、RPC 调用网络协议，RPC 调用监听器和过滤器等等。该模块提供了默认的 基于 dubbo 协议的实现，还提供了 hessian、http、rmi、及 webservice 等协议的实现，能够满足绝大多数项目的使用需求，另外 还提供了对自定义协议的扩展。
### dubbo-registry
注册中心子项目，它是 RPC 中 consumer 服务消费者 和 provider 服务提供者 两个重要角色的协调者，该子项目定义了核心的 注册中心组件，提供了 mutilcast、redis 和 zookeeper 等多种方式的注册中心实现，用于不同的使用场景。当然，几乎所有的项目都会选择基于 zookeeper 的实现。
### dubbo-remote
远程通讯子项目，RPC 的实现基础就是远程通讯，consmer 要调用 provider 的远程方法必须通过 远程通讯实现。该模块定义了远程传输器、endpoint 终端、客户端、服务端、编码解码器、数据交换、缓冲区、通讯异常定义 等核心组件。他是对于远程网络通讯的抽象，提供了诸如 netty、mina、http 等 协议和技术框架的实现方式。
### dubbo-monitor
监控子项目，该模块可以监控服务调用的各种信息，例如调用耗时、调用量、调用结果等等，监控中心在调用过程中收集调用的信息，发送到监控服务，在监控服务中可以存储这些信息，对这些数据进行统计分析 和 展示。dubbo 默认提供了一个实现，该实现非常简单，只是作为默认的实现范例，生产环境使用价值不高，往往需要自行实现。
### dubbo-container
容器子项目，是一个独立的容器，以简单的 Main(类) 加载 Spring 启动，因为服务通常不需要 Tomcat/JBoss 等 Web 容器的特性，没必要用 Web 容器去加载服务。
### dubbo-config
配置中心子项目，该模块通过 配置信息，将 dubbo 组件的各个模块整合在一起，给 框架的使用者 提供 可配置的、易用的 分布式服务框架。它定义了面向 dubbo 使用者的各种信息配置，比如服务发布配置、方法发布配置、服务消费配置、应用程序配置、注册中心配置、协议配置、监控配置等等。
### dubbo-cluster
集群子项目，将多个服务提供方伪装为一个提供方，包括：负载均衡、容错、路由等，集群的地址列表可以是静态配置的，也可以是由注册中心下发。
### dubbo-admin
该子项目是一个 web 应用，可以独立部署，用于管理 dubbo 服务，该管理应用可以连接注册中心，读取和更新 注册中心中的内容。




