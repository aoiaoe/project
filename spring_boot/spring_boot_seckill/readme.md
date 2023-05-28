# openresty + springboot + redis 模拟秒杀

## 踩坑
    nginx upstream不能用下划线
    在proxy_pass中配置有下划线的上游服务组名, 导致请求出错