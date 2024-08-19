package com.cz.sping_boot_milvus.milvus;

import com.alibaba.fastjson.JSONObject;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.partition.LoadPartitionsParam;
import io.milvus.v2.common.IndexParam;

import java.util.*;

public class Operation2 {
    private static final String dbName = "my_first_db";
    private static final MilvusServiceClient client = ConnectToMilvus.newBuilder().withDbName(dbName).build();

    public static void main(String[] args) {
//        createCollection();
//        addIndex();
        showCollection();
//        insert();
//        loadCollection();
//        search();
        client.close();
    }

    public static void createCollection(){
        CollectionSchemaParam schemaParam = CollectionSchemaParam.newBuilder()
                .addFieldType(FieldType.newBuilder().withName("my_id").withDataType(DataType.Int64).withAutoID(false)
                        .withPrimaryKey(true).build())
                .addFieldType(FieldType.newBuilder().withName("my_vec").withDataType(DataType.FloatVector).withDimension(5)
                        .build())
                .build();

        CreateCollectionParam param = CreateCollectionParam.newBuilder()
                .withSchema(schemaParam)
                .withCollectionName("test_vec")
                .build();
        R<RpcStatus> collection = client.createCollection(param);
        System.out.println(collection.getData().toString());
    }

    public static void showCollection(){
        DescribeCollectionParam param = DescribeCollectionParam.newBuilder()
                .withCollectionName("test_vec")
                .build();
        R<DescribeCollectionResponse> collection = client.describeCollection(param);
        System.out.println(collection.getData().toString());
    }

    public static void addIndex(){
        CreateIndexParam indexParamForIdField = CreateIndexParam.newBuilder()
                .withCollectionName("test_vec")
                .withFieldName("my_id")
                .withIndexType(IndexType.STL_SORT)
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("nlist", 1024);
        CreateIndexParam indexParamForVectorField = CreateIndexParam.newBuilder()
                .withCollectionName("test_vec")
                .withFieldName("my_vec")
                .withIndexType(IndexType.IVF_FLAT)
                .withMetricType(MetricType.L2)
                .withExtraParam("{\"nlist\":1024}")
                .build();

        List<CreateIndexParam> indexParams = new ArrayList<>();
        indexParams.add(indexParamForIdField);
        indexParams.add(indexParamForVectorField);
        System.out.println(client.createIndex(indexParamForIdField).getData());
        System.out.println(client.createIndex(indexParamForVectorField).getData());
    }

    public static void insert(){

        InsertParam insertReq = InsertParam.newBuilder()
                .withCollectionName("test_vec")
//                .withRows(DataHolder.data)
                .withRows(Arrays.asList(new JSONObject(Map.of("my_id", 1046L, "my_vec", Arrays.asList(0.3580376395471989f, -0.6023495712049978f, 0.18414012509913835f, -0.26286205330961354f, 0.9029438446296592f), "color", "pink_8682"))))
                .build();

        final R<MutationResult> insert = client.insert(insertReq);

        System.out.println(insert.getData());
    }

    public static void loadCollection(){
        LoadCollectionParam my_vec = LoadCollectionParam.newBuilder()
                .withCollectionName("test_vec")
                .build();
        System.out.println(client.loadCollection(my_vec).getData());
    }

    public static void search(){
        List<List<Float>> query_vectors = Arrays.asList(Arrays.asList(0.3580376395471989f, -0.6023495712049978f, 0.18414012509913835f, -0.26286205330961354f, 0.9029438446296592f));
        SearchParam param = SearchParam.newBuilder()
                .withCollectionName("test_vec")
                .withFloatVectors(query_vectors)
                .withVectorFieldName("my_vec")
                .withTopK(3)
                .build();
        R<SearchResults> search = client.search(param);
        System.out.println(search.getData().getResults());
    }
}
