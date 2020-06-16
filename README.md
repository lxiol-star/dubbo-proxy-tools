不知道你是否在工作中有遇到过类似情况：
<br>
dubbo接口调试复杂，需要通过telnet命令或者通过consumer调用来触发。
telnet语句参数格式复杂，每次编写都要小心谨慎，一旦出错又需重来。
复杂对象参数传参调用接口复杂，编写java api调用接口时间成本较高。

![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085546_3b26a815_1777749.png)
<br>
上述这些坑我在工作中都有遇见过，发现大部分耗时都会卡在调用dubbo服务做自测的阶段，所以后来花费了写业余时间写了一款高效的dubbo测试工具开源给大家使用。
这款工具目前已在实际工作中应用半年多，基本功能已经成熟，后续依旧会进行版本维护。
相关的代码地址为：
<br>
[https://gitee.com/IdeaHome_admin/dubbo-proxy-tools
](https://gitee.com/IdeaHome_admin/dubbo-proxy-tools)
*ps: 如果大家喜欢，希望能给出一颗宝贵的star*

<br>

### 关于如何部署本工具
<br>

下载了gitlab的代码之后，你会看到有两个文件包：iubbo-proxy和iubbo-proxy-web，它们分别正好对应了后端代码和前端代码。
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085546_c75faf36_1777749.png)
#### 前端代码的部署
前端采用非常简单的vue技术，只需要将文件部署到一台nginx上边即可运作。
前端的默认访问页面是test-dubbo-web.html。

**但是有两个小点需要改动下js配置**
constants.js
这份文件里面编写了对应的请求server地址，这块是对应了后端服务的url。
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085546_2258fdab_1777749.png)
由于请求dubbo接口需要通过zk注册中心来拉去服务名列表，所以需要在iubbo.x.js里面的commonzk中做配置：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_155b7881_1777749.png)
这里的host是一个别称，相当于一个key用于供前端展示，实际上传输给后端的是用ip这个值。


#### 后端代码的部署
后端工程采用了springboot框架技术，核心的配置放在了application.properties里面：

```java
server.port=7089
application.invoker.name=iubbo-invoker-proxy

spring.datasource.druid.password=
spring.datasource.druid.username=
spring.datasource.druid.url=jdbc:mysql://127.0.0.1:3306/iubbox-proxy
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver

mybatis-plus.configuration.map-underscore-to-camel-case=true

spring.redis.port=6379
spring.redis.host= 127.0.0.1

```

然后倒入建表的sql：

```sql
CREATE TABLE `t_dubbo_invoke_req_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `arg_json` varchar(2500) COLLATE utf8_bin DEFAULT NULL COMMENT 'dubbo请求参数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `t_user` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
```
**t_user** 用于记录相关的用户账号，方便于保存用户账号信息。
**t_dubbo_invoke_req_record** 用于记录请求dubbo接口的用例信息。

最后就是启动入口类**org.iubbo.proxy.DubboInvokerApplication**

**启动成功截图**
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_78defaf0_1777749.png)





### 关于本工具的使用教程

本工具区分了已登录账号和未登录账号两类角色，已登录账号的使用者可以对请求的用例进行保存，方便下一次提取信息，未登录账号虽然没有保存用例的功能，但是不影响其使用本工具进行测试。

首页截图：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_bc7daed7_1777749.png)

**1.指定zk地址**
在测试dubbo接口之前，我们通常都会去拉取一遍zk上边的service地址，操作如下图:
先在文本框点击，输入和js配置有关的字母或数字会有模糊匹配的选项供各位选择：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_34da96ae_1777749.png)
选中了zk地址之后，再去点击拉取zk地址按钮（以前有同事刚接触这个工具的时候，这里被绕蒙了~~）

拉取zk地址的时候，后端会根据文章上边提到的js配置里面的ip值去拉取，拉取成功会有相关提示：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_a8349e06_1777749.png)
接下来便是筛选dubbo服务地址的功能，在拉取zk地址下方有一个下拉框，这里面此时应当会被注入zk上所有dubbo服务列表的名称。
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_b45b31ec_1777749.png)
选择对于的service名称，然后在右边的“请输入名称”文本框中输入该接口对应的方法名称（一定要名称对应）
这里以调用MsgService的sendMsg方法为例：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085547_66d36e8b_1777749.png)
然后配置相关的参数和名称：
选择对应参数类型和值：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_8c0a5fd3_1777749.png)
最后发起请求：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_c3d95d5f_1777749.png)
在基础参数配置项的旁边，还有一个模块是专门配置consumer端的额外内容，这些参数项都是在平时工作中可能会应用到的场景，需要的时候可以进行配置。这里面也配备了工作中非常常用的直连选项。

我个人最喜欢使用的还是直连功能，因为在开发过程中，经常需要直连机器做自测：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_0183935b_1777749.png)

假如说希望保存自己曾经发送过的dubbo测试用例，那么你只需先进行登录账号，账号直接往t_user表里面写入一条数据即可：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_f8709a1f_1777749.png)
在首页的右上方有个进入登录页面按钮：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_d3fe5fe8_1777749.png)
点击登录
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085548_5700fecb_1777749.png)


如何保存请求用例：
![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085549_46525ab3_1777749.png)
保存用例这里有些小瑕疵，需要用户手动刷新下页面才能显示保存的用例信息：

![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0616/085549_68a38418_1777749.png)
点击选用参数，则页面又会重新回显之前使用过的请求信息。


### 特殊参数请求

该工具支持多种开发中常见的参数格式调用，目前支持常规参数
*ps:注意这里的参数格式需要和dubbo方法里面的参数格式按照相同顺序相同类型做映射，否则后端程序会返回找不到对应方法*

Java的常用基本类型数据：boolean，short，int，long，double，char，float
Java中常用的包装类数据：

```java
java.lang.String,
java.lang.Integer,
java.lang.Object,
java.util.List,
java.lang.Class,
java.lang.Long,
java.lang.Boolean,
java.util.Map,
java.util.Date,
java.lang.Float,
java.lang.Double
```

### **对于简单的数据类型传递**
![图片: https://uploader.shimo.im/f/1MiAoLiD6zADpfVx.png
图片: https://uploader.shimo.im/f/s8WRZPpOOyIn7LWF.png
图片: https://uploader.shimo.im/f/aftGOrdiJK0fUkfs.png](https://img-blog.csdnimg.cn/20200616084433681.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)


### **多参数，中英文混合传递**

![图片](https://images.gitee.com/uploads/images/2020/0616/085548_3331a89e_1777749.jpeg)


### List类型参数的传递

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_84501d46_1777749.jpeg)

### Class类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_3ed465c7_1777749.jpeg)

### Map类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_bc2b9830_1777749.jpeg)

### Date类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_616080e2_1777749.jpeg)


### 自定义对象参数传参

如果在实际应用中遇到了这种场景，需要调用以下的接口，那么这个使用就需要使用自定义参数了：

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_eca2de9a_1777749.jpeg)

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_4c47ae7d_1777749.jpeg)

![图片](https://images.gitee.com/uploads/images/2020/0616/085549_e603facb_1777749.jpeg)

### 复杂类型自定义参数传递

假设遇到了List类型参数，而且传输的List里面包含有自定义对象，例如下边这种类型：

```
List<UserDTO> testUserDtoList(List<UserDTO> userDTOList,Class clazz);
```
那么此时的传参案例可以像下边这样来写：
![图片](https://images.gitee.com/uploads/images/2020/0616/085549_58ebb85c_1777749.jpeg)




## 易错点归纳

注意请求的参数顺序要和方法对应的参数顺序一致，假设服务的方法定义如下：

```
<T> List<T> getList(List<Long> var1, Class<T> var2);
```
那么传入的参数就必须先填List参数，再写Class参数。
**正确示例：**（先写了List参数，再写Class参数）
**错误示例：**（先写了Class参数，再写List参数）





