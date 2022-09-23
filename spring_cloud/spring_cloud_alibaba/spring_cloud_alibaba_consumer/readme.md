## Sentinel 流控
    官方文档： https://sentinelguard.io/zh-cn/docs/introduction.html
    本示例参考文档: https://mp.weixin.qq.com/s/Fvdj7aRYLwtzFE8kBSS8Hw
        该文档保存成了图片: sentinel教程.jpeg

### API流控
    此种方式为硬编码方式, 不过可以基于配置中心，监听配置变更，重新加载流控规则
    参考: SentinelConfig.java