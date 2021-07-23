### Springboot整合Spring Security可生产化实战
    
    spring + Spring Security + JWT + RBAC + Redis

    1、配置类上添加@EnableWebSecurity注解,开启Spring Security
    2、配置类 SecurityConfig.java
        实现WebSecurityConfigurer接口或者继承WebSecurityConfigurerAdapter抽象类(推荐)
        a、重写protected void configure(AuthenticationManagerBuilder auth) throws Exception{}方法
            主要配置用户信息加载类(参考第3步), 设置密码编码器
        b、重写protected void configure(HttpSecurity http) throws Exception {}方法
            主要配置路径的访问性、访问失败处理端点、访问拒绝处理器、配置自定义的过滤器(JwtFilter)
            重要：将自定义的JwtFilter配置在UsernamePasswordAuthenticationFilter前面
                代码如下： http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                原因参看类中注释
        c、重写public void configure(WebSecurity web) throws Exception {}方法
            需要配置需要security忽略的路径, 需要注意的点参照注释
    3、用户加载类 SysUserServiceImpl.java, 实现UserDetailsService
        重写public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {}方法
        当调用AuthenticationManager#authenticate()时,由于SecurityConfig.java中配置的是自定义的当前用户加载类
        故最终会转到此方法进行用户获取,该方法最终将用户信息封装为第4步定义的类对象返回
    4、用户类 LoginUser.java, 可以区别于系统用户, 实现UserDetails接口
        该用户类为用户加载类SysUserServiceImpl.java重写方法返回类,该类对象存储Spring Security所需信息并在上下文中传递
    5、JwtToken过滤器 JwtFilter.java, 继承OncePerRequestFilter(该过滤器子类每个请求只会执行过滤一次)
        a、重写protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {}方法
            该方法是判断是否需要过滤,可以配置不需要token解析的路径
        b、重写protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {}方法
            在该方法中,最主要是获取header中的jwt,解析其中承载的系统用户信息,封装成UsernamePasswordAuthenticationToken对象放入上下文中
    6、Token业务类 TokenService.java
        主要实现生产token(并存入redis), 刷新token, 验证/解析token等操作
    7、Redis配置类 RedisConfig.java
        主要配置Redis操作模板类RedisTemplate, 配置key/value序列化器
        