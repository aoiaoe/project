## Spring Security Demo

### 文章参考:

        系列文章:
        https://blog.csdn.net/u012702547/article/details/106206339/?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242

### 依赖:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

### 接口保护

        引入Spring Security依赖之后，就可以保护项目中的所有接口
        在 Spring Security 中，如果我们不做任何配置，默认的登录页面和登录接口的地址都是 /login，也就是说，默认会存在如下两个请求：

        GET http://localhost:8080/login
        POST http://localhost:8080/login

### 简单演示时 用户名和密码

        用户名和密码在SS的属性配置文件(SecurityProperties)中：
        private String name = "user";
        private String password = UUID.randomUUID().toString();
        如果不在配置文件中指定密码,则会生成一个uuid并打印在日志中：
        Using generated security password: 30abfb1f-36e1-446a-a79b-f70024f589ab
        但是此种方式每次启动都会生成不同的密码,因此有以下两种方式进行用户名和密码的配置
        
        1、在配置文件中配置：
            spring:
                security:
                    user:
                        name: admin
                        password: admin
        2、使用JavaConfig,继承WebSecurityConfigurerAdapter,覆写protected void configure(AuthenticationManagerBuilder auth) throws Exception方法
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication()
                        .withUser("admin")
                        .password("admin")
                        .roles("admin");
            }

### 生产用户名和密码使用数据库存储

        1、提供用户实体类和业务类，需实现UserDetailsService,参考UsersServiceImpl.Java
        2、配置类中进行认证配置
             @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                // 搭配数据库
                auth.userDetailsService(usersService);
            }
        
