### Springboot整合Spring Session

    配置类:SpringSessionConfig.java
        在配置类上添加@EnableRedisHttpSession注解,即可开启Redis存储共享session
        登录成功之后,默认会将sessionId放入cookeie中响应请求
    
    我们会看到，默认情况下，Spring Session 产生的 sessionid 的 KEY 为 "SESSION" 。
    这是因为 sessionid 在返回给前端时，使用 DefaultCookieSerializer 写回 Cookie 给浏览器，在未自定义 sessionid 的 Cookie 名字的情况下，使用 "SESSION" 。

    比较神奇的是，sessionid 的 VALUE 竟然看起来是一串加密的字符串？！ 
    在 DefaultCookieSerializer 写回 Cookie 给前端时，会将 sessionid 先 BASE64 编码一下，然后再写回 Cookie 给浏览器。

    那么，如果我们想自定义 sessionid 在 Cookie 中，使用别的 KEY 呢，例如说 "JSESSIONID" 。
    我们可以通过自定义 CookieHttpSessionIdResolver Bean 来实现,参考配置类.
    