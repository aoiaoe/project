# 整合Swagger API对接文档

    1、编写Swagger配置类:com.cz.springbootswagger.config.SwaggerConfig
        注意:需要加上@EnableSwagger2注解
    2、如果访问项目ip:port/swagger-ui.html出现404,那么需要配置本地访问资源
        com.cz.springbootswagger.config.SwaggerStaticResourceConfigurer
     
    3、其他项目引入本模块之后,需要@ComponentScan(basePackage = "com.cz")
        才能将swagger配置和本地资源配置类加载进容器
        然后在需要加入api文档的接口上添加@ApiOpration注解,即可将api加载进文档中