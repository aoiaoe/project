package com.cz.rpc.client;

import com.cz.rpc.proxy.JDKProxyFactory;
import com.cz.rpc.service.DataService;

public class RpcReference {

    private JDKProxyFactory jdkProxyFactory;

    public RpcReference(JDKProxyFactory jdkProxyFactory){
        this.jdkProxyFactory = jdkProxyFactory;
    }

    public DataService get(Class<DataService> dataServiceClass) {

        return null;
    }
}
