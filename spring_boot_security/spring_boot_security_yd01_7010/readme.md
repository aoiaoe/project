### Springboot整合Spring Security
#### PS：参考来源：芋道源码

    本demo使用内存存储账户  属于入门demo   生产不可用

    默认情况下，Spring Boot UserDetailsServiceAutoConfiguration 自动化配置类，会创建一个内存级别的 InMemoryUserDetailsManager Bean 对象，提供认证的用户信息。
        这里，我们添加了 spring.security.user 配置项，UserDetailsServiceAutoConfiguration 会基于配置的信息创建一个用户 User 在内存中。
        如果，我们未添加 spring.security.user 配置项，UserDetailsServiceAutoConfiguration 会自动创建一个用户名为 "user" ，密码为 UUID 随机的用户 User 在内存中。

    当我们在浏览器中调用localhost:7010/hello/admin请求时,
    因为我们没有自定义登录界面，所以默认会使用 DefaultLoginPageGeneratingFilter类，生成登录界面