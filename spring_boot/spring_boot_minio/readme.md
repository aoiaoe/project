## 安装minio
    docker run -idt \
    -p 19000:9000 \
    -p 19001:9001  \
    --name minio   \
    -v /alian/minio/mnt:/data   \
    -e "MINIO_ROOT_USER=minio"   \
    -e "MINIO_ROOT_PASSWORD=Alian123"   \
    minio/minio server /data \
    --console-address ":9000" \ # 控制台端口
    --address ":9001" # api端口
    
    坑： 如果在云环境启动minio，不设置最后两行配置，外网不能访问
        本地可无视