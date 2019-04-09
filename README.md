# RPC测试平台

#### 介绍
基于java语言开发的Rpc测试平台，使用技术有springboot，netty，zookeeper

截图：
![输入图片说明](https://gitee.com/uploads/images/2019/0409/150750_0b7c31b0_1777749.png "屏幕截图.png")

功能：
目前支持通过zookeeper来拉去rpc服务内容，然后通过netty封装数据之后发送到server端。
缺陷：
对于多台provider需要进行ip指定的功能暂未开发完成，目前只能针对单独一台provider机器进行rpc测试