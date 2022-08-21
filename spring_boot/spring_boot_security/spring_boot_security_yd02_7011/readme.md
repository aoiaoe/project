### Springboot整合Spring Security 
    使用内存用户 并引入简单权限控制

    自定义配置类：SecurityConfig.java并继承抽象类WebSecurityConfigurerAdapter实现对Security的配置
    具体配置请看SecurityConfig.java, 注释详细

    HelloController.java中接口分为两类:
    1、无@PreAuthorize注解的接口
        该类接口权限控制在SecurityConfig.java中进行配置
    2、有@PreAuthorize注解的接口
        该类接口需配合@EnableGlobalMethodSecurity(prePostEnabled = true)注解开启权限控制
        @PreAuthorize 注解，等价于 #access(String attribute) 方法，，当 Spring EL 表达式的执行结果为 true 时，可以访问。

    @PermitAll 注解，等价于 #permitAll() 方法，所有用户可访问。
        重要！！！因为在「SecurityConfig」中，配置了 .anyRequest().authenticated() ，任何请求，访问的用户都需要经过认证。所以这里 @PermitAll 注解实际是不生效的。
        也就是说，Java Config 配置的权限，和注解配置的权限，两者是叠加的。
