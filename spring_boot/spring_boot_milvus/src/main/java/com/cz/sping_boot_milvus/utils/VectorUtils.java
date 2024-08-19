package com.cz.sping_boot_milvus.utils;

import com.alibaba.fastjson.JSONObject;
import io.milvus.client.MilvusClient;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CollectionSchemaParam;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VectorUtils {
    private final static VectorStrategy DEFAULT = new VectorTensorStrategy();

    public static List<Float> embedText(String text){
        return convertFloatArrayToList(DEFAULT.embedText(text));
    }

    /**
     *
     * @param client
     * @param collectionName
     * @param fieldTypes
     * @param load              是否加载集合，只有加载了才可以插入、查询等
     */
    public static void createCollectionAndLoad(MilvusClient client, String collectionName,
                                                 List<FieldType> fieldTypes, boolean load) {
        CollectionSchemaParam schema = CollectionSchemaParam.newBuilder()
                .withFieldTypes(fieldTypes)
                .build();
        CreateCollectionParam createCollectionParam = CreateCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .withSchema(schema)
                .build();

        R<RpcStatus> collection = client.createCollection(createCollectionParam);
        if (load) {
            LoadCollectionParam loadParam = LoadCollectionParam.newBuilder()
                    .withCollectionName(collectionName)
                    .build();
            client.loadCollection(loadParam);
        }

    }

    public static void insert(MilvusClient client, String collectionName, List<JSONObject> vectors){

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withRows(vectors)
                .build();

        R<MutationResult> insert = client.insert(insertParam);
    }

    public static List<Long> searchSimilarVectors(MilvusClient client, String collectionName, List<Float> queryVector) {
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withTopK(10)
                .withMetricType(MetricType.L2)
                .withFloatVectors(List.of(queryVector))
                .build();

        R<SearchResults> search = client.search(searchParam);
        return search.getData().getResults().getIds().getIntId().getDataList();
    }

    public static List<Float> convertFloatArrayToList(Float[] floatArray) {
        if(floatArray == null || floatArray.length < 1){
            return null;
        }
        return Arrays.stream(floatArray).collect(Collectors.toList());
    }


}
