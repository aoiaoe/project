# single_db模块

     传统单数据源项目 springboot + mybatis-plus + druid数据源
     采用mybatis-plus的codeGenerator生成实体类、mapper等文件
     并开启了druid数据源的sql监控web界面
     如果需要关闭的话,只需要将配置文件中的配置spring.datasource.webEnabld改为注释掉或者改为false
     即可关闭在DruidConfig类将druid的web页面配置注入到容器