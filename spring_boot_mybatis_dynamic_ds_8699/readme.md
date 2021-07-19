## Springboot整合Mybatis-plus + 多数据源动态切换

    1、mybatis-plus默认不支持多数据源,想要支持多数据源需要引入依赖:
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
        <version>3.1.1</version>
    </dependency>

    2、引入上面依赖之后,需要修改配置文件配置:
    spring:
      datasource:
        dynamic:
          primary: ds1
          strict: false
          datasource:
            ds1:
              url: jdbc:mysql://localhost:3306/ds1
              driver-class-name: com.mysql.cj.jdbc.Driver
              username: root
              password: 123456
            ds2:
              url: jdbc:mysql://localhost:3306/ds2
              driver-class-name: com.mysql.cj.jdbc.Driver
              username: root
              password: 123456

    primary的值必须为其下两行中datasource中配置的数据源名称之一,设置住数据源
    strict代表严格模式,默认为false,如果未找到主数据源则使用primary数据源

    项目启动后会将所有数据源实例化并放入容器中

    3、在需要切换数据源的方法/类上增加注解@DS()
    参考 UserService#selectUserById方法, 定义如下:
    @DS("#ds")
    public Users selectUserById(Integer id, String ds){

    引入多数据源依赖之后:
    a、DynamicDataSourceAnnotationAdvisor类会对所有注解了@DS注解的方法/类中的方法生成切面
    b、DynamicDataSourceAnnotationInterceptor类会对所有切面进行处理
            处理逻辑为: 如果@DS注解中的value带#号,则使用spel从方法参数中回去值, 否则直接使用@DS中的value
                        将获取到的值放入DynamicDataSourceContextHolder栈中
    c、DynamicRoutingDataSource#getDataSource()方法会去DynamicDataSourceContextHolder中获取值之后去决策使用哪个数据源
    

## 自定义数据源决策器
    DynamicDataSourceAutoConfiguration中bean都是@ConditionalOnMissingBean
    可以自行覆盖然后实例化到容器,实现自定义
    

