不知道你是否在工作中有遇到过类似情况：
<br>
dubbo接口调试复杂，需要通过telnet命令或者通过consumer调用来触发。
telnet语句参数格式复杂，每次编写都要小心谨慎，一旦出错又需重来。
复杂对象参数传参调用接口复杂，编写java api调用接口时间成本较高。
<br>
上述这些坑我在工作中都有遇见过，发现大部分耗时都会卡在调用dubbo服务做自测的阶段，所以后来花费了写业余时间写了一款高效的dubbo测试工具开源给大家使用。
这款工具目前已在实际工作中应用1年多，基本功能已经成熟，后续依旧会进行版本维护。
相关的代码地址为：
<br>
[https://gitee.com/IdeaHome_admin/dubbo-proxy-tools
](https://gitee.com/IdeaHome_admin/dubbo-proxy-tools)
*ps: 如果大家喜欢，希望能给出一颗宝贵的star*

### 2.0.0-release版本
下边部分是针对于2.0.0-release版本进行迭代之后的使用说明文档
<br>

### 关于如何部署本工具
<br>


新版本的代码结构去除了原先的前后端分离，采用前后端合并的思路进行整合，减轻使用者的部署成本。

#### 前端代码的部署
前端采用非常简单的vue技术，只需要调整js目录下方的constants.js文件中的server_addr变量即可改变请求地址。
默认的请求地址为：

```js
let server_addr="http://127.0.0.1:7090/";
```

#### 后端代码的部署
后端工程采用了springboot框架技术，核心的配置放在了application.properties里面,调整为对应的redis数据库或者mysql配置即可

```
server.port=7090
application.invoker.name=iubbo-invoker-proxy

spring.datasource.druid.password=password
spring.datasource.druid.username=root
spring.datasource.druid.url=jdbc:mysql://10.11.9.243:3306/iubbox
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver

mybatis-plus.configuration.map-underscore-to-camel-case=true

spring.redis.port=6379
spring.redis.host=localhost
spring.redis.password=password


```

然后导入建表的sql:

```sql
CREATE TABLE `t_dubbo_invoke_req_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `arg_json` varchar(2500) COLLATE utf8_bin DEFAULT NULL COMMENT 'dubbo请求参数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8mb4;


CREATE TABLE `t_user` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8mb4;

CREATE TABLE `t_register_config` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) DEFAULT NULL COMMENT 'host地址',
  `ip` varchar(60) DEFAULT NULL COMMENT '注册中心真实ip',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` smallint(2) DEFAULT NULL COMMENT '注册中心类型：1 zk,2 nacos',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

```



**t_user** 用于记录相关的用户账号，方便于保存用户账号信息。
**t_dubbo_invoke_req_record** 用于记录请求dubbo接口的用例信息。
**t_register_config** 用于记录注册中心的配置信息


最后就是启动入口类**org.iubbo.proxy.DubboInvokerApplication**

**启动成功截图**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127222248113.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)



### 关于本工具的使用教程

本工具区分了已登录账号和未登录账号两类角色，已登录账号的使用者可以对请求的用例进行保存，方便下一次提取信息，未登录账号虽然没有保存用例的功能，但是不影响其使用本工具进行测试。

首页地址：
http://localhost:7090/html/test-dubbo-web.html
或者访问：
http://localhost:7090/html/index.html

首页截图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127222800555.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
ps：2.0.0版本对颜色做了一些细微调整，如果觉得绿色太丑，可以在代码里面进行调整

#### **1.指定zk注册地址**
2.0.0版本中将注册中心的配置抽离为通过MySQL进行管理：
只需要在t_register_config表里面加入一行记录即可看到相关信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127223128409.png)

在测试dubbo接口之前，我们通常都会去拉取一遍zk上边的service地址，操作如下图:
先在文本框点击，输入和js配置有关的字母或数字会有模糊匹配的选项供各位选择：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127223538186.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

选中了zk地址之后，再去点击拉取zk地址按钮（以前有同事刚接触这个工具的时候，这里被绕蒙了~~）

拉取zk地址的时候，后端会根据文章上边提到的js配置里面的ip值去拉取，拉取成功会有相关提示：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112722360342.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

接下来便是筛选dubbo服务地址的功能，在拉取zk地址下方有一个下拉框，这里面此时应当会被注入zk上所有dubbo服务列表的名称。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127223625350.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

选择对于的service名称，然后在右边的“请输入名称”文本框中输入该接口对应的方法名称（一定要名称对应）
例如测试该方法：

```java
public interface DubboService {

    /**
     * 测试接口功能
     *
     * @return
     */
    String doTest(String str);
}

```

然后配置相关的参数和名称：
选择对应参数类型和值：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127224422326.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
选择参数之后需要点击一遍添加参数按钮，这样才能保证刚才设置的参数生效。

最后发起请求：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127224542717.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)



在基础参数配置项的旁边，还有一个模块是专门配置consumer端的额外内容，这些参数项都是在平时工作中可能会应用到的场景，需要的时候可以进行配置。这里面也配备了工作中非常常用的直连选项。

#### 额外参数
个人最喜欢使用的还是直连功能，因为在开发过程中，经常需要直连机器做自测：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127224827940.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

假如说希望保存自己曾经发送过的dubbo测试用例，那么你只需先进行登录账号，或者点击注册按钮重新注册账号即可：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127224920532.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127224939449.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

#### 保存请求用例
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225116460.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
#### 查看保存用例信息
用户手动刷新下页面才能显示保存的用例信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225223681.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
点击选用参数，则页面又会重新回显之前使用过的请求信息。


### 转让测试用例
点击转让测试用例按钮即可进行用户的转让
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225309334.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

#### 压力测试
支持对于dubbo接口进行压力测试的配置
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020112722544342.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)

### 请求详情分析
对于每次dubbo请求，都会有对应的参数请求信息，响应数据相关信息展示：

接口响应数据信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225718430.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
实际发送给dubbo服务端的各项参数指标信息：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225731911.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)
请求耗时的各项指标数据
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201127225803363.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Rhbm55X2lkZWE=,size_16,color_FFFFFF,t_70)


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

![图片](https://img-blog.csdnimg.cn/img_convert/a387aa3658797d148e4ce4f1a57d2bf1.png)


### List类型参数的传递

![图片](https://img-blog.csdnimg.cn/img_convert/7b7f227beee0ee67eae9693ebaec5a15.png)

### Class类型的参数传递

![图片](https://img-blog.csdnimg.cn/img_convert/11352de63c7846570ea58a27b0aaaf33.png)

### Map类型的参数传递

![图片](https://img-blog.csdnimg.cn/img_convert/3be062f82fd693ff9efd160d4e2a4180.png)

### Date类型的参数传递

![图片](https://img-blog.csdnimg.cn/img_convert/eb8ff6f5310d28ef054f4e0368113147.png)


### 自定义对象参数传参

如果在实际应用中遇到了这种场景，需要调用以下的接口，那么这个使用就需要使用自定义参数了：

![图片](https://img-blog.csdnimg.cn/img_convert/1c7b1fe30b7cb4df1c318a509298eb00.png)

![图片](https://img-blog.csdnimg.cn/img_convert/660e73db3001aa2c35b1f09143e2bc35.png)

![图片](https://img-blog.csdnimg.cn/img_convert/035bccfb2b6b5d9f5f7e87efe5b43275.png)

### 复杂类型自定义参数传递

假设遇到了List类型参数，而且传输的List里面包含有自定义对象，例如下边这种类型：

```
List<UserDTO> testUserDtoList(List<UserDTO> userDTOList,Class clazz);
```
那么此时的传参案例可以像下边这样来写：
![图片](https://img-blog.csdnimg.cn/img_convert/7780a32213a13830425e97a4c5fad31d.png)




## 易错点归纳

注意请求的参数顺序要和方法对应的参数顺序一致，假设服务的方法定义如下：

```
<T> List<T> getList(List<Long> var1, Class<T> var2);
```
那么传入的参数就必须先填List参数，再写Class参数。
**正确示例：**（先写了List参数，再写Class参数）
**错误示例：**（先写了Class参数，再写List参数）


## 实际运行

目前该工具已经在自己公司运行一年左右，较为平稳，给团队的开发效率带来了极大地提升。希望本工具也能对各位有所帮助。



