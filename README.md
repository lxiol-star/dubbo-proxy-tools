不知道你是否在工作中有遇到过类似情况：
dubbo接口调试复杂，需要通过telnet命令或者通过consumer调用来触发。
telnet语句参数格式复杂，每次编写都要小心谨慎，一旦出错又需重来。
复杂对象参数传参调用接口复杂，编写java api调用接口时间成本较高。

![在这里插入图片描述](https://images.gitee.com/uploads/images/2020/0615/235405_bb3f02d3_1777749.png)
上述这些坑我在工作中都有遇见过，发现大部分耗时都会卡在调用dubbo服务做自测的阶段，所以后来花费了写业余时间写了一款高效的dubbo测试工具开源给大家使用。
这款工具目前已在实际工作中应用半年多，基本功能已经成熟，后续依旧会进行版本维护。
相关的代码地址为：
[https://gitee.com/IdeaHome_admin/dubbo-proxy-tools
](https://gitee.com/IdeaHome_admin/dubbo-proxy-tools)
*ps: 如果大家喜欢，希望能给出一颗宝贵的star*

<br>

### 关于如何使用本工具

**前端代码的部署**
前端采用非常简单的vue技术，只需要将文件部署到一台nginx上边即可运作。
但是有两个小点需要改动下js配置

**1.指定zk地址**
在测试dubbo接口之前，我们通常都会去拉取一遍zk上边的service地址，操作如下图：
!!在这里插入片]描述](https://img-blogtcsdnimgps://imgconvert.csdnimg.cn/aHR0cHM6Ly91cGxvYWRlci5zaGltby5pbS9mLzFNaUFvTGlENnpBRHBmVngucG5nIXRodW1ibmFpbA?x-oss-process=image/format,png)

![图片](https://images.gitee.com/uploads/images/2020/0615/235405_97e84efc_1777749.jpeg)

![图片](https://images.gitee.com/uploads/images/2020/0615/235405_4e927b0c_1777749.jpeg)

### **多参数，中英文混合传递**

![图片](https://images.gitee.com/uploads/images/2020/0615/235405_4c081d7c_1777749.jpeg)


### List类型参数的传递

![图片](https://images.gitee.com/uploads/images/2020/0615/235405_871501ad_1777749.jpeg)

### Class类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0615/235405_0e72b93b_1777749.jpeg)

### Map类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_5e462a6d_1777749.jpeg)

### Date类型的参数传递

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_b8a3563c_1777749.jpeg)


### 自定义对象参数传参

如果在实际应用中遇到了这种场景，需要调用以下的接口，那么这个使用就需要使用自定义参数了：

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_d4475504_1777749.jpeg)

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_4c79cf11_1777749.jpeg)

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_2e746821_1777749.jpeg)

### 复杂类型自定义参数传递

假设遇到了List类型参数，而且传输的List里面包含有自定义对象，例如下边这种类型：

```
List<UserDTO> testUserDtoList(List<UserDTO> userDTOList,Class clazz);
```
那么此时的传参案例可以像下边这样来写：
![图片](https://images.gitee.com/uploads/images/2020/0615/235406_8a68813d_1777749.jpeg)




## 易错点归纳

注意请求的参数顺序要和方法对应的参数顺序一致，假设服务的方法定义如下：

```
<T> List<T> getList(List<Long> var1, Class<T> var2);
```
那么传入的参数就必须先填List参数，再写Class参数。
**正确示例：**（先写了List参数，再写Class参数）

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_78dbda9e_1777749.jpeg)

**错误示例：**（先写了Class参数，再写List参数）

![图片](https://images.gitee.com/uploads/images/2020/0615/235406_0cb06ef1_1777749.jpeg)






