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

# 安装FastDFS

    参考: Docker安装部署FastDFS详细过程.png

    # 使用docker的host网络，容器共享宿主机ip和端口
    # 先创建文件夹
    mkdir /mydata
    cd /mydata
    mkdir /tracker
    ll
    # 执行docker命令
    docker run -d --name tracker --network=host -v /alian/fastdfs/tracker:/var/fdfs delron/fastdfs tracker
    # 注意:tracker服务默认的端口为22122
    
    
    # 创建文件夹
    cd /mydata
    mkdir /storage
    ll
    # 执行命令
    docker run -d --name storage --network=host  -e TRACKER_SERVER=192.168.18.101:22122 -v /alian/fastdfs/storage:/var/fdfs -e GROUP_NAME=group1 delron/fastdfs storage
    # 注意:其中TRACKER_SERVER中的ip要修改为你的Tracker服务所在的服务IP地址
