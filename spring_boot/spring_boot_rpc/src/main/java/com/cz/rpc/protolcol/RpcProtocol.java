package com.cz.rpc.protolcol;

import lombok.Data;

import java.io.Serializable;

import static com.cz.rpc.common.Constants.MAGIC_NUMBER;

@Data
public class RpcProtocol implements Serializable {
    private static final long serialVersionUID = 5359096060555795690L;

    private short magicNumber = MAGIC_NUMBER;
    private int contentLength;
    //这个字段其实是RpcInvocation类的字节数组，在RpcInvocation中包含了更多的调用信息，详情见下方介绍
    private byte[] content;
 
    //getter setter 省去

    public RpcProtocol(byte[] content){
        this.content = content;
        this.contentLength = content.length;
    }
}   
