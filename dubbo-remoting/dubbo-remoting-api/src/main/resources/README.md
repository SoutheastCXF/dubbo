## dubbo-remoting-api 定义了远程通信模块最核心的API
### buffer包：缓冲在NIO框架中很重要的存在，各个NIO框架都实现了自己相应的缓存操作
### exchange包：信息交换层，其中封装了请求响应模式，在传输层之上重新封装了Request-Response语义，为了满足RPC的需求。这层可以认为专注在Request和Response携带的信息上 
### transport包：网络传输层，只负责单向消息传输，是对Mina、Netty、Grizzly的抽象，它也可以扩展UDP传输

![Architecture](https://github.com/doocs/source-code-hunter/blob/main/images/Dubbo/Dubbo%E6%95%B4%E4%BD%93%E6%9E%B6%E6%9E%84%E5%9B%BE.png?raw=true)
