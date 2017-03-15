# CloudFoundry Autoscaler Demo 

## 介绍

**Autoscaler Demo** 作为一个ServiceBroker，它能动态的根据数据库中已绑定的应用信息，来调整应用的实例个数，来实现应用的自动弹性扩展

##  必须环境

- java 8

## 使用介绍

1. 在resource目录下，创建一个application.properties的文件,在文件中添加以下内容：

   ```
   spring.datasource.url = jdbc:mysql://localhost:3306/autoscalerdemo?useUnicode=true&characterEncoding=utf8&useSSL=false
   spring.datasource.username = root
   spring.datasource.password = root
   spring.datasource.driverClassName = com.mysql.jdbc.Driver
   spring.jpa.database = MYSQL
   spring.jpa.show-sql = true
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
   cf.apiHost= cloudfoundry api url
   cf.username=username
   cf.password=password
   ```

2. 直接把项目上传到cloudfoundry 平台上，它会自动读取resource下的`ServiceDescription.json`文件的信息，并把这些信息自动存在配置的数据库中,一下则是`ServiceDescription.json`中的信息

   ```json
   {
     "name" : "autoscaler_demo",
     "description" : "cloudfoundry app autoscaler",
     "bindable" : true,
     "tags" : ["min"],
     "metadata" : {
       "longDescription" : "autoscaler app instances",
       "imageUrl" : "",
       "displayName" : "AutoscalerService",
       "providerDisplayName" : "geekyzk",
       "supportUrl" : "https://github.com/geekyzk/cloudfoundry_autoscaler_demo"
     }
   }

   ```

   当然，这些信息是可以修改的，可以根据需要，把相关的属性信息都修改，但是要注意一点，name则为service的名字，跟平台上已有的服务名是不能相同的。

3. 当上传完了以后，还需要为service设置对应的plan值

   - 在数据库找到要设置plan对应service的guid，例如`6182ba15-95b3-4e5c-a36e-50a07c16b406`

   - 使用http请求工具，可以使用curl，或则使用postman，下面则是调用过程

     ```json
     Post url: http://network/v2/catalog/services/6182ba15-95b3-4e5c-a36e-50a07c16b406/plans
     Post data:
     {"name" : "quckly",
     "description" : "xx",
     "metadata" : {
     	"max_app" : "10",
     	"bullets" : ["Each app has its own credential','100 apps at most"],
     	"displayName":"quckly",
     	"type" : "quckly"
     	}
     }
     ```

     创建plan的这个步骤，需要注意:同一个服务下的plan的名字不能一样。metadata中的数据，除了max_app是非必须的，其他都是必须的，当然，也可以根据业务添加自己需要的key-value到metadata中，昨晚这一步，就可以把这个应用创建成 service-broker了。

4. 当把服务绑定成service broker后，还有一个步骤需要操作。就是需要把plan的public属性设置成true，默认为false，只这样，这个服务才能在marketplace中可见，设置过程如下:

   - 在命令行窗口，调用` cf curl /v2/service_plans` 来获取得到我们平台上的所有plan

   - 找到我们程序对应的plan，在metadata中找到guid，通过调用cf 命令来根据plan的id来把plan的public属性设置成true，在这个步骤中，测试时是使用了cf-java-client封装的api调用来更改，例子如下：

     ```java
     cloudFoundryClient.servicePlans().update(UpdateServicePlanRequest.builder()
                     .servicePlanId("6fa4b6ee-7359-4b2a-9365-39d7ecb9fb80")
                     .publiclyVisible(true)
                     .build())
                     .block();
     ```
   - 通过以上步骤，一个能根据cpu使用率来调整应用实例的简单弹性伸缩服务就已经创建成功，后续可以根据业务需求来进行一定量的扩展。