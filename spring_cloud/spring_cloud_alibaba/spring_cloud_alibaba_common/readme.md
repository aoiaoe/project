## 通用模块
    常量
    枚举
    实体类
    工具类:
        断言工具类
        jwt工具类(结合非对称加密 RSA256), 此模块保存公钥， 私钥在spring_cloud_alibaba_auth_center模块中, 只要需要校验的模块，引用了此模块即可实现jwt校验