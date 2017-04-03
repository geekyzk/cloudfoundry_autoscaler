# Autoscaler 配置流程

## 使用前提

- java8
- javabuildpack v3.9 or laster


## 一.获取war包

获取war包有两种途径，分别如下：

* 从release中获取最新的war包
* 下载源码，使用maven进行编译生成war包

## 二.修改配置文件(修改war包方式)

###  修改application.properties文件

打开war包中`WEB-INF\classes\`下的applicaiton.properties文件,其中内容如下

```json
spring.datasource.url = jdbc:mysql://192.168.4.107:3306/autoscalerdemo?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.driverClassName = com.mysql.jdbc.Driver
spring.jpa.database = MYSQL
spring.jpa.show-sql = true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
cf.apiHost=api.xx.com
cf.username=admin
cf.password=xxx
spring.freemarker.suffix=.html
```

修改其中以下字段:

- `spring.datasource.url`:为即将上传的broker绑定的数据库信息，其中数据库必须创建好。
- `spring.datasource.username`:数据库的账号
- `spring.datasource.password`:数据库的密码。
- `cf.apiHost`:cf平台的api地址
- `cf.username`:cf平台的admn账号
- `cf.password`::cf平台的admn密码。

修改完后，保存，打包。

## 三. 上传war包

通过上一步的步骤，我们就可以上传war包了，步骤如下:

`cf push tian_broker_2 -p D:\autoscaler_broker.war -b java_buildpack_offline-v39`

通过上述命令，就可以把war包上传成功

## 四. 设置service和plan信息

当上传完应用后，我们可以获取应用的地址，访问应用域名，点击首页的创建Broker，开始创建service和plan。

当我们开始创建，第一步就是输入service信息,大致信息可以根据如下json格式中key对应的value值输入到页面。

```json
{
  "name" : "autoscaler_ext",
  "description" : "autoscaler app instance",
  "bindable" : true,
  "tags" : ["tiny"],
  "metadata" : {
    "longDescription" : "tian design autoscaler app instance service broker",
    "imageUrl" : "http://xx.img",
    "displayName" : "autoscaler_ext",
    "providerDisplayName" : "Tian",
    "supportUrl" : "http://www.em248.com"
  }
}
```

当信息填写完后，点击下一步，就可以创建plan，部分参数说明如下：

* addCpu:是设置当cpu使用率超过百分之多少就添加实例
* cutCpu:是设置当cpu少于百分比多少就减少实例
* minInstance:是设置最小实例数
* maxInstance:是设置最大实例数
* 其中所需的信息大致如下:

```json
{
  "name" : "tiny",
  "description" : "微弹性",
  "metadata" : {
	"addCpu" : "0.8",
	"cutCpu" : "0.2",
  	"minInstance" : "1",
    "maxInstance": "5"
	"bullets" : ["微扩展","微调"],
	"displayName":"微弹",
	"type" : "tiny"
	}
}
```

随后，点击保存配置，即完成设置service和plan的信息。

## 五. 创建broker

当完成上面步骤后,就可以使应用成为broker,如下:

```powershell
PS C:\Users\tian> cf create-service-broker tian_auto_broker tian tian http://tian-broker.xx.cn
Creating service broker tian_auto_broker as admin...
OK
```

## 六. 设置public

当设置为broker后，还需要设置public为可见，才能使用，步骤如下:

### 1. 获取plan guid

通过`cf curl /v2/service_plans`命令，获取得到刚创建的plan的guid，如**24b63f2b-17b6-473e-9a53-96ace18163a5**

### 2. 设置可见

可以执行以下命令，来设置plan成为可见

```shell
cf curl /v2/service_plans/24b63f2b-17b6-473e-9a53-96ace18163a5 -X PUT -d "{\"public\":true}"
```

当设置完后，此broker就可以使用了