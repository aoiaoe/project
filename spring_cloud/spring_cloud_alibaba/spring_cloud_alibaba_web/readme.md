### 通用mvc 统一响应、全局异常处理
    <dependency>
        <groupId>com.cz</groupId>
        <artifactId>spring_cloud_alibaba_web</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    引入次依赖之后，即拥有了web模块，全局异常处理、统一响应
    不过：引用此依赖的模块，需要将入口包名改为com.cz.spring_cloud_alibaba，
    或者在启动类上增加 @ComponentScan(basePackages = "com.cz.spring_cloud_alibaba")注解增加包扫描
    否则，无法扫描到此包内的注解，无法启动成功，或者无法实现异常处理和统一响应
    
    如果某一些接口不需要返回统一响应包装过的内容，则在类上或者方法上加上@IgnoreResponseBody