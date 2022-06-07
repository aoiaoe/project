### java sdk demo

    sdk和客户端之间通过RSA算法，通过公私钥加解密进行交换数据
    暂时只适配了json参数
    参考：https://blog.csdn.net/weixin_38538285/article/details/107436849
    
#### server
    客户端以固定的参数请求服务器
    1、服务器首先通过拦截器拦截参数，从参数中获取加密的数据，然后使用私钥解密
        将解密之后得到的字节数组放于ThreadLocal中后续参数解析器使用
        参考 AuthInterceptor.java
    2、由于服务器接口所需的参数json数据存在于客户端请求的加密数据中
        所以使用自定义参数解析器，从ThreadLocal中获取解析后得到的字节数组
        将其反序列化为接口所需要的类
        参考 DemoArgResolver.java 自定义参数解析器
            MyWebMvcConfiguration.java 将参数解析配置到容器中
        当接口所需参数为json类型时，接口参数多用@RequestBody以注解，
        而容器中以存在可以解析@RequestBody注解的参数解析，
        就算我们将自定义的解析器配置到容器中，也不会起作用
        这时，有两种解决方案
        方案1：
            自定义注解，替换掉@Response注解
        方案2：
            修改自定义解析器在解析器链中的位置
            参考 ArgumentResolverBeanPostProcessor.java
    3、参数处理之后，服务器响应会经过统一响应配置类处理
        将响应参数经过私钥加密，再通过统一响应类响应至客户端
        参考： MyRestControllerAdvice.java

#### 替代方案
    针对json请求接口，对参数中多加几个参数，比如版本号，appid，对所有字段加密后的encrypt字段
    这样就可以不使用参数解析器这么复杂
    但是数据就采用了明文传输，安全性降低
