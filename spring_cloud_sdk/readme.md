# spring_cloud_sdk

     sdk模块,存放通用的组件,如拦截器，过滤器等组件
     
     拦截器:
     FeignHeaderInterceptor: 拦截所有请求，获取feign客户端添加的header值并打印
     
     
     
     使用此SDK,因为包名和其他模块不同，故其他模块引用此SDK之后，
     需要在@SpringBootApplication注解上添加scanBasePackages,例如：@SpringBootApplication(scanBasePackages = {"com.cz"})
     才能让spring扫描到SDK中的组件,才能使组件生效