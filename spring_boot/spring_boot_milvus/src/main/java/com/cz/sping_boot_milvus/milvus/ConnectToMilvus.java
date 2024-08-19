package com.cz.sping_boot_milvus.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.param.ConnectParam;
import io.netty.util.internal.StringUtil;

public class ConnectToMilvus {

    String URI = "http://192.168.18.203:19530";
    String TOKEN = "";

    private String _dbName = "default";

    private ConnectToMilvus(){}
    public static ConnectToMilvus newBuilder() {
        return new ConnectToMilvus();
    }

    public MilvusServiceClient build() {
        ConnectParam connectParam = ConnectParam.newBuilder()
            .withUri(URI)
            .withToken(StringUtil.isNullOrEmpty(TOKEN) ? TOKEN : null)
            .withDatabaseName(_dbName)
            .build();

        return new MilvusServiceClient(connectParam);
    }

    public ConnectToMilvus withDbName(String dbName) {
        this._dbName = dbName;
        return this;
    }
}
