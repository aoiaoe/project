package com.cz.sping_boot_milvus.milvus;

import com.alibaba.fastjson.JSONObject;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.CreateDatabaseRequest;
import io.milvus.grpc.ListDatabasesResponse;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateDatabaseParam;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.common.DataType;
import io.milvus.v2.common.IndexParam;
import io.milvus.v2.service.collection.request.AddFieldReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.collection.request.DescribeCollectionReq;
import io.milvus.v2.service.collection.request.GetLoadStateReq;
import io.milvus.v2.service.collection.response.DescribeCollectionResp;
import io.milvus.v2.service.collection.response.ListCollectionsResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Operation {

    private static final String dbName = "my_first_db";
    private static final String CLUSTER_ENDPOINT = "http://192.168.18.203:19530";
    private static final ConnectConfig connectConfig = ConnectConfig.builder()
            .uri(CLUSTER_ENDPOINT)
            .dbName(dbName)
            .build();

    private static final MilvusClientV2 client = new MilvusClientV2(connectConfig);

    private static final MilvusServiceClient client1  = ConnectToMilvus.newBuilder().withDbName(dbName).build();
    public static void main(String[] args) {

//        createDefaultFieldsCollection();

        createCustomiseCollection();

//        descCollections();
        descAllCollections();
//        createDb();

        client1.close();
    }

    public static void createDb(){

        R<ListDatabasesResponse> listDatabasesResponseR = client1.listDatabases();

        if(!listDatabasesResponseR.getData().getDbNamesList().contains(dbName)){
            CreateDatabaseParam param = CreateDatabaseParam.newBuilder().withDatabaseName(dbName).build();
            R<RpcStatus> databaseRes = client1.createDatabase(param);
            System.out.println(databaseRes.getData().toString());
        }
    }

    /**
     * 只给定一个集合名称, milvus会自动添加id （作为主键）和 vector （作为向量字段），以及 auto_id和enable_dynamic_field设置默认启用。
     * auto_id 启用此设置可确保主键自动递增。在数据插入期间无需手动提供主键。
     * enable_dynamic_field: 启用后，要插入的数据中除 id 和 vector 之外的所有字段都将被视为动态字段。
     *      这些附加字段作为键值对保存在名为 $meta 的特殊字段中。此功能允许在数据插入期间包含额外的字段。 类似于es的自动映射
     * @return
     */
    public static boolean createDefaultFieldsCollection(){

// 1. Connect to Milvus server


// 2. Create a collection in quick setup mode
        CreateCollectionReq quickSetupReq = CreateCollectionReq.builder()
                .collectionName("quick_setup")
                .dimension(5)
                .build();

        client.createCollection(quickSetupReq);

// Thread.sleep(5000);

        GetLoadStateReq quickSetupLoadStateReq = GetLoadStateReq.builder()
                .collectionName("quick_setup")
                .build();

        Boolean res = client.getLoadState(quickSetupLoadStateReq);

        System.out.println(res);
        return res;
    }

    /**
     * 自定义集合，指定字段、类型、索引等
     * @return
     */
    public static boolean createCustomiseCollection(){
        String collectionName = "my_first_vec_collection";
        CreateCollectionReq.CollectionSchema schema = client.createSchema();
        schema.addField(AddFieldReq.builder()
                .fieldName("uid")
                .dataType(DataType.Int64)
                .isPrimaryKey(true)
                .autoID(false)
        .build());
        schema.addField(AddFieldReq.builder()
                .fieldName("vec")
                .dataType(DataType.FloatVector)
                .dimension(5)
        .build());

        // 3.3 Prepare index parameters
        IndexParam indexParamForIdField = IndexParam.builder()
                .fieldName("uid")
                .indexType(IndexParam.IndexType.STL_SORT)
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("nlist", 1024);
        IndexParam indexParamForVectorField = IndexParam.builder()
                .fieldName("vec")
                .indexType(IndexParam.IndexType.IVF_FLAT)
                .metricType(IndexParam.MetricType.L2)
                .extraParams(map)
                .build();

        List<IndexParam> indexParams = new ArrayList<>();
        indexParams.add(indexParamForIdField);
        indexParams.add(indexParamForVectorField);
// 3.4 Create a collection with schema and index parameters
        CreateCollectionReq customizedSetupReq1 = CreateCollectionReq.builder()
                .collectionName(collectionName)
                .collectionSchema(schema)
                .indexParams(indexParams)
                .build();

        client.createCollection(customizedSetupReq1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

// 3.5 Get load state of the collection
        GetLoadStateReq customSetupLoadStateReq1 = GetLoadStateReq.builder()
                .collectionName(collectionName)
                .build();

        boolean res = client.getLoadState(customSetupLoadStateReq1);
        return res;
    }

    /**
     * 查看集合
     */
    public static void descCollections(){
        String collectionName = "my_first_vec_collection";
        DescribeCollectionReq describeCollectionReq = DescribeCollectionReq.builder()
                .collectionName(collectionName)
                .build();

        DescribeCollectionResp describeCollectionRes = client.describeCollection(describeCollectionReq);

        System.out.println(JSONObject.toJSON(describeCollectionRes));
    }

    /**
     * 查看所有集合名
     */
    public static void descAllCollections(){
        // 5. List all collection names
        ListCollectionsResp listCollectionsRes = client.listCollections();

        System.out.println(listCollectionsRes.getCollectionNames());
    }
}
