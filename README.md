# **常规使用步骤**

## **指定zk地址**

在测试dubbo接口之前，我们通常都会去拉去一遍zk上边的地址，操作如下图：

如果没有合适的zk可以手动输入具体地址加端口号码,目前已配置了dev环境crm的zk，test环境crm的zk和test环境nora的zk。

![输入图片说明](https://images.gitee.com/uploads/images/2020/0615/223431_e10a577b_1777749.png "屏幕截图.png")

## **拉取zk的配置**

在点击了拉取zk地址按钮之后，会有弹窗提示拉取成功，截图如下：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0615/223456_9dc066b4_1777749.png "屏幕截图.png")

然后可以搜索你需要调用的dubbo服务名称

![输入图片说明](https://images.gitee.com/uploads/images/2020/0615/223520_04a124f9_1777749.png "屏幕截图.png")

最后还需要指定对应的方法全称,这里以调用CrmUserInfoService的get方法为例：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0615/223538_890bee6d_1777749.png "屏幕截图.png")

手动设置参数信息,然后逐个添加：

调用成功截图：

![输入图片说明](https://images.gitee.com/uploads/images/2020/0615/223611_b8b14f9e_1777749.png "屏幕截图.png")

## dubbo额外配置

在基础参数配置项的旁边，还有一个模块是专门配置consumer端的额外内容，这些参数项都是在平时工作中可能会应用到的场景，需要的时候可以进行配置。这里面也配备了工作中非常常用的直连选项。

![图片](https://uploader.shimo.im/f/Gcw5JqL5YlgZQGzc.png!thumbnail)



# **特殊参数请求**

## 参数类型案例

该平台支持多种开发中常见的参数格式调用，目前支持常规参数

**Java的常用基本类型数据：**boolean，short，int，long，double，char，float

**Java中常用的包装类数据：**

```
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
### 对于简单的数据类型传递

![图片](https://uploader.shimo.im/f/1MiAoLiD6zADpfVx.png!thumbnail)

![图片](https://uploader.shimo.im/f/s8WRZPpOOyIn7LWF.png!thumbnail)

![图片](https://uploader.shimo.im/f/aftGOrdiJK0fUkfs.png!thumbnail)

### **多参数，中英文混合传递**

![图片](https://uploader.shimo.im/f/CLoLIsGXQJYXDxa5.png!thumbnail)


### List类型参数的传递

![图片](https://uploader.shimo.im/f/Nc5xZfr7gSYtNAjG.png!thumbnail)

### Class类型的参数传递

![图片](https://uploader.shimo.im/f/cDDbIt93U1Ysh1x5.png!thumbnail)

### Map类型的参数传递

![图片](https://uploader.shimo.im/f/Q3yK4kQ2gOUaZ5ZG.png!thumbnail)

### Date类型的参数传递

![图片](https://uploader.shimo.im/f/UsVY6h4EjMA5kU0O.png!thumbnail)


### 自定义对象参数传参

如果在实际应用中遇到了这种场景，需要调用以下的接口，那么这个使用就需要使用自定义参数了：

![图片](https://uploader.shimo.im/f/GKpiGPwqJrUZVah0.png!thumbnail)

![图片](https://uploader.shimo.im/f/aNmj770zEzEvTjVF.png!thumbnail)

![图片](https://uploader.shimo.im/f/CrImGXuoCcMImCSs.png!thumbnail)

### 复杂类型自定义参数传递

假设遇到了List类型参数，而且传输的List里面包含有自定义对象，例如下边这种类型：

```
List<UserDTO> testUserDtoList(List<UserDTO> userDTOList,Class clazz);
```
那么此时的传参案例可以像下边这样来写：
![图片](https://uploader.shimo.im/f/OpOJB7oQshg63DXf.png!thumbnail)




## 易错点归纳

注意请求的参数顺序要和方法对应的参数顺序一致，假设服务的方法定义如下：

```
<T> List<T> getList(List<Long> var1, Class<T> var2);
```
那么传入的参数就必须先填List参数，再写Class参数。
**正确示例：**（先写了List参数，再写Class参数）

![图片](https://uploader.shimo.im/f/vyFkbFJTYDIwkPzU.png!thumbnail)

**错误示例：**（先写了Class参数，再写List参数）

![图片](https://uploader.shimo.im/f/X0tmB5KbGu8L96sJ.png!thumbnail)






