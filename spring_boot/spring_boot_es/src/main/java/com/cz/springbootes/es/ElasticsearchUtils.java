package com.cz.springbootes.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cz.springbootes.entity.EsPageInfo;
import com.cz.springbootes.entity.HighLight;
import com.cz.springbootes.entity.Id;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
public class ElasticsearchUtils {

	public static int MAX_ROWS = 1000000;

	/**
	 * 创建索引
	 *
	 * @param client
	 * @param index
	 * @param source
	 */
	public static void createIndex(RestHighLevelClient client, String index, String source) {
		CreateIndexRequest request = new CreateIndexRequest(index);

		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 1).put(
				"index.max_result_window", MAX_ROWS));
		request.mapping(source, XContentType.JSON);
		CreateIndexResponse createIndexResponse;
		try {
			createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("create index io error, index: {}", index, e);
			throw new RuntimeException("create index io error");
		}
		boolean acknowledged = createIndexResponse.isAcknowledged();
		boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
		log.info("create index finish, index:{},acknowledged:{}, shardsAcknowledged:{}", index, acknowledged,
				shardsAcknowledged);
	}

	/**
	 * 检测索引是否存在
	 *
	 * @param client
	 * @param index
	 * @return
	 */
	public static boolean isExistIndex(RestHighLevelClient client, String index) {
		GetIndexRequest request = new GetIndexRequest(index);
		boolean exists = false;
		try {
			exists = client.indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("check index exist error, index: {}", index, e);
			throw new RuntimeException("check index exist error");
		}
		return exists;
	}

	/**
	 * 删除索引
	 *
	 * @param client
	 * @param index
	 */
	public static void deleteIndexAsync(RestHighLevelClient client, String index) {
		ActionListener<AcknowledgedResponse> listener = new ActionListener<AcknowledgedResponse>() {
			@Override
			public void onResponse(AcknowledgedResponse deleteIndexResponse) {
				boolean acknowledged = deleteIndexResponse.isAcknowledged();
				log.info("delete index finish, index:{},acknowledged:{}", index, acknowledged);
			}

			@Override
			public void onFailure(Exception e) {
				log.info("delete index fail, index:{}", index);
			}
		};
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		client.indices().deleteAsync(request, RequestOptions.DEFAULT, listener);

	}

	public static void deleteIndex(RestHighLevelClient client, String index) {
		DeleteIndexRequest request = new DeleteIndexRequest(index);
		try {
			client.indices().delete(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.info("delete index fail, index:{}", index);
		}

	}

	/**
	 * 创建文档(同步)
	 *
	 * @param client
	 * @param index    索引
	 * @param id       文档ID
	 * @param document 文档
	 * @param <T>
	 * @param <D>
	 */
	public static <T, D> String createDocument(RestHighLevelClient client, String index, T id, D document) {
		try {

			IndexRequest request = new IndexRequest(index).id(String.valueOf(id)).source(JSONObject.toJSONString(document),
					XContentType.JSON);
			IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
			log.info("create document success, name:{}, id:{}, result:{}", indexResponse.getIndex(),
					indexResponse.getId(), indexResponse.getResult());
			return indexResponse.getId();
		} catch (Exception e){
			log.info("create document fall, index:{}", index, e);
		}
		return null;
	}

	/**
	 * 创建文档(异步)
	 *
	 * @param client
	 * @param index    索引
	 * @param id       文档ID
	 * @param document 文档
	 * @param <T>
	 * @param <D>
	 */
	public static <T, D> void createDocumentAsync(RestHighLevelClient client, String index, T id, D document) {
		ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				log.info("create document success, name:{}, id:{}, result:{}", indexResponse.getIndex(),
						indexResponse.getId(), indexResponse.getResult());
			}

			@Override
			public void onFailure(Exception e) {
				log.info("create document fall, index:{}", index, e);
			}
		};
		IndexRequest request = new IndexRequest(index).id(String.valueOf(id)).source(JSONObject.toJSONString(document),
				XContentType.JSON);
		client.indexAsync(request, RequestOptions.DEFAULT, listener);
	}

	/**
	 * 批量创建文档
	 *
	 * @param client
	 * @param index
	 * @param documents
	 * @param <D>
	 */
	public static <D extends Id<?>> void createBulkDocumentAsync(RestHighLevelClient client, String index, List<D> documents) {
		ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse bulkResponse) {
				log.info("create bulk document finish, index:{}", index);
				for (BulkItemResponse bulkItemResponse : bulkResponse) {
					if (bulkItemResponse.isFailed()) {
						BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
						log.warn("create document fail, index:{}, message:{}, cause:{}", failure.getIndex(), failure.getMessage(), failure.getCause());
					}
				}
			}
			@Override
			public void onFailure(Exception e) {
				log.info("create document fall, index:{}", index, e);
			}
		};
		BulkRequest request = new BulkRequest();
		Optional.ofNullable(documents).orElse(new ArrayList<>()).forEach(doc -> request.add(new IndexRequest(index).id(String.valueOf(doc.getId())).source(JSONObject.toJSONString(doc, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON)));
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		client.bulkAsync(request, RequestOptions.DEFAULT, listener);
	}

	public static <D extends Id<?>> void createBulkDocument(RestHighLevelClient client, String index, List<D> documents) {
		if (CollectionUtils.isEmpty(documents)) {
			return;
		}
		if (documents.size() > 100000) {
			List<List<D>> partition = ListUtils.partition(documents, 100000);
			for (List<D> ds : partition) {
				bulkInsert(client, index, ds);
			}
		} else {
			bulkInsert(client, index, documents);
		}
	}


	private static <D extends Id<?>> void bulkInsert(RestHighLevelClient client, String index, List<D> ds) {
		BulkRequest request = new BulkRequest();
		Optional.ofNullable(ds).orElse(new ArrayList<>()).forEach(doc -> request.add(new IndexRequest(index).id(String.valueOf(doc.getId())).source(JSONObject.toJSONString(doc, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON)));
		request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
		try {
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.info("create document fall, index:{}", index, e);
			throw new RuntimeException("create document fall");
		}
	}

	public static <D extends Id<?>> void createBulkDocument2(RestHighLevelClient client, String index, List<D> documents) {
		BulkRequest request = new BulkRequest();
		Optional.ofNullable(documents).orElse(new ArrayList<>()).forEach(doc ->
		{
			request.add(new IndexRequest(index).id(String.valueOf(doc.getId())).source(JSONObject.toJSONString(doc, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON));

		});

		try {
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.info("create document fall, index:{}", index, e);
			throw new RuntimeException("create document fall");
		}
	}

	public static <D extends Id<?>> void copyIndex(RestHighLevelClient client, String sourceIndex, String destIndex) {

		ReindexRequest reindexRequest = new ReindexRequest();
		reindexRequest.setSourceIndices(sourceIndex);
		reindexRequest.setDestIndex(destIndex);
		reindexRequest.setShouldStoreResult(true);
		try {
			client.reindex(reindexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.info("copyIndex  fall, index:{},{}", sourceIndex, destIndex, e);
			throw new RuntimeException("create document fall");
		}
	}

	public static <D extends Id<?>> void updateBulkDocumentObject(RestHighLevelClient client,final String index, List<D> documents) {
		BulkRequest request = new BulkRequest();
		Optional.ofNullable(documents).orElse(new ArrayList<>()).forEach(doc -> {
			request.add(new UpdateRequest(index, String.valueOf(doc.getId())).doc(
					JSONObject.toJSONString(doc, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON));
		});

		try {
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			client.bulk(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.info("update document fall, index:{}", index, e);
			throw new RuntimeException("update document fall");
		}
	}

	/**
	 * 根据文档ID获取文档
	 *
	 * @param client
	 * @param index
	 * @param id
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> getDocumentById(RestHighLevelClient client, String index, T id) {
		GetRequest getSourceRequest = new GetRequest(index, String.valueOf(id));
		GetResponse response = null;
		try {
			response = client.get(getSourceRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error("get document error, index:{}, id:{}", index, id, e);
			return null;
		}
		return response.getSource();
	}



	public static <T extends Id> EsPageInfo<T> searchDocumentPage(RestHighLevelClient client, String index, AbstractQueryBuilder queryBuilder, int pageNum, int pageSize, TypeReference<T> type) {
		if (1 > pageNum) {
			pageNum = 1;
		}
		EsPageInfo<T> esPageInfo = new EsPageInfo<>();
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(from);
		sourceBuilder.size(size);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 根据ID降序
		sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
		//查询条件
		sourceBuilder.query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("search doc error, index:{}", index, e);
			throw new RuntimeException("search doc error");
		}

		SearchHits hits = searchResponse.getHits();
		TotalHits totalHits = hits.getTotalHits();
		long value = totalHits.value;
		int total = (int) value;
		int pages;
		if (total % pageSize == 0) {
			pages = total / pageSize;
		} else {
			pages = total / pageSize + 1;
		}
		esPageInfo.setTotal(total);
		esPageInfo.setPages(pages);
		esPageInfo.setPageNum(pageNum);
		esPageInfo.setPageSize(pageSize);
		SearchHit[] searchHits = hits.getHits();
		List<T> result = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			T t = JSONObject.parseObject(sourceAsString, type);
			t.setId(hit.getId());
			result.add(t);
		}
		esPageInfo.setList(result);
		return esPageInfo;
	}

	/**
	 * 分页查询文档
	 *
	 * @param client
	 * @param index
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T extends Id> EsPageInfo<T> searchDocumentPage(RestHighLevelClient client, String index,
													   AbstractQueryBuilder queryBuilder, SortBuilder sortBuilder,
													   int pageNum, int pageSize, TypeReference<T> type) {
		if (1 > pageNum) {
			pageNum = 1;
		}
		EsPageInfo<T> esPageInfo = new EsPageInfo<>();
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(from);
		sourceBuilder.size(size);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 查询条件
		sourceBuilder.query(queryBuilder);
		// 排序规则
		sourceBuilder.sort(sortBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("search doc error, index:{}", index, e);
			throw new RuntimeException("search doc error");
		}

		SearchHits hits = searchResponse.getHits();
		TotalHits totalHits = hits.getTotalHits();
		long value = totalHits.value;
		esPageInfo.setTotal((int) value);
		esPageInfo.setPageNum(pageNum);
		esPageInfo.setPageSize(pageSize);
		esPageInfo.setPages(esPageInfo.getTotal() / pageSize);
		if(esPageInfo.getTotal() % pageSize > 0){
			esPageInfo.setPages(esPageInfo.getPages() + 1);
		}
		SearchHit[] searchHits = hits.getHits();
		List<T> result = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			T t = JSONObject.parseObject(sourceAsString, type);
			t.setId(hit.getId());
			result.add(t);
		}
		esPageInfo.setList(result);
		return esPageInfo;
	}

	/**
	 * 分页查询文档
	 *
	 * @param client
	 * @param index
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T extends Id> EsPageInfo<T> searchDocumentPage(RestHighLevelClient client, String index,
																  AbstractQueryBuilder queryBuilder, SortBuilder sortBuilder,
																  int pageNum, int pageSize,
																  TypeReference<T> type,
																  String[] includeFields, String[] excludeFields) {
		if (1 > pageNum) {
			pageNum = 1;
		}
		EsPageInfo<T> esPageInfo = new EsPageInfo<>();
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(from);
		sourceBuilder.size(size);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 查询条件
		sourceBuilder.query(queryBuilder);
		// 排序规则
		if(sortBuilder != null) {
			sourceBuilder.sort(sortBuilder);
		}
		sourceBuilder.fetchSource(includeFields, excludeFields);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("search doc error, index:{}", index, e);
			throw new RuntimeException("search doc error");
		}

		SearchHits hits = searchResponse.getHits();
		TotalHits totalHits = hits.getTotalHits();
		long value = totalHits.value;
		esPageInfo.setTotal((int) value);
		esPageInfo.setPageNum(pageNum);
		esPageInfo.setPageSize(pageSize);
		esPageInfo.setPages(esPageInfo.getTotal() / pageSize);
		if(esPageInfo.getTotal() % pageSize > 0){
			esPageInfo.setPages(esPageInfo.getPages() + 1);
		}
		SearchHit[] searchHits = hits.getHits();
		List<T> result = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			T t = JSONObject.parseObject(sourceAsString, type);
			t.setId(hit.getId());
			result.add(t);
		}
		esPageInfo.setList(result);
		return esPageInfo;
	}

	/**
	 * 带高亮分页查询文档
	 * 获取高亮的第一句内容
	 *
	 * @param client
	 * @param index
	 * @param pageNum
	 * @param pageSize
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T extends HighLight> EsPageInfo<T> searchHighLightDocumentPage(RestHighLevelClient client, String index,
																				  AbstractQueryBuilder queryBuilder, SortBuilder sortBuilder,
																				  HighlightBuilder highlightBuilder,
																				  int pageNum, int pageSize,
																				  TypeReference<T> type,
																				  String[] includeFields, String[] excludeFields, String highLightField) {
		if (1 > pageNum) {
			pageNum = 1;
		}
		EsPageInfo<T> esPageInfo = new EsPageInfo<>();
		int from = (pageNum - 1) * pageSize;
		int size = pageSize;

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(from);
		sourceBuilder.size(size);
		sourceBuilder.highlighter(highlightBuilder);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 查询条件
		sourceBuilder.query(queryBuilder);
		// 排序规则
		if(sortBuilder != null) {
			sourceBuilder.sort(sortBuilder);
		}
		sourceBuilder.fetchSource(includeFields, excludeFields);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("search doc error, index:{}", index, e);
			throw new RuntimeException("search doc error");
		}

		SearchHits hits = searchResponse.getHits();
		TotalHits totalHits = hits.getTotalHits();
		long value = totalHits.value;
		esPageInfo.setTotal((int) value);
		esPageInfo.setPageNum(pageNum);
		esPageInfo.setPageSize(pageSize);
		esPageInfo.setPages(esPageInfo.getTotal() / pageSize);
		if(esPageInfo.getTotal() % pageSize > 0){
			esPageInfo.setPages(esPageInfo.getPages() + 1);
		}
		SearchHit[] searchHits = hits.getHits();
		List<T> result = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			T t = JSONObject.parseObject(sourceAsString, type);
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			if(highlightFields != null && highlightFields.containsKey(highLightField)) {
				HighlightField field = highlightFields.get(highLightField);
				if(field == null){
					continue;
				}
				Text[] fragments = field.getFragments();
				if(fragments == null || fragments.length < 1 || fragments[0] == null){
					continue;
				}
				t.setHighlight(fragments[0].toString());
			}
			t.setId(hit.getId());
			result.add(t);
		}
		esPageInfo.setList(result);
		return esPageInfo;
	}

	/**
	 *
	 * @param client
	 * @param index
	 * @param queryBuilder
	 * @param type
	 * @param count
	 * @param pageSize 按照pageSize分页汇总
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> searchLabelList(RestHighLevelClient client, String index,
											  AbstractQueryBuilder queryBuilder,TypeReference<T> type,Integer count,Integer pageSize) {
		if(count <= pageSize){
			return searchLabelPageList(client, index, queryBuilder, type,0,pageSize);
		}
		List<T> result = new ArrayList<>();
		int pages = 0;
		if (count % pageSize == 0){
			pages = count / pageSize;
		}else {
			pages = count / pageSize + 1;
		}
		for (int i = 1; i <= pages; i++) {
			List<T> list = searchLabelPageList(client, index, queryBuilder, type, i, pageSize);
			result.addAll(list);
		}
		return result;
	}

	public static <T> List<T> searchLabelPageList(RestHighLevelClient client, String index, AbstractQueryBuilder queryBuilder, TypeReference<T> type,int pageNum, int pageSize) {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 查询条件
		if(pageNum < 1){
			pageNum = 1;
		}
		sourceBuilder.from((pageNum - 1) * pageSize);
		sourceBuilder.size(pageSize);
		sourceBuilder.query(queryBuilder);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("search doc error, index:{}", index, e);
			throw new RuntimeException("search doc error");
		}

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		List<T> result = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			result.add(JSONObject.parseObject(sourceAsString, type));
		}
		return result;
	}

	/**
	 * 统计索引文档数
	 *
	 * @param client
	 * @param index
	 * @return
	 */
	public static int countDocument(RestHighLevelClient client, String index, QueryBuilder queryBuilder) {
		if (queryBuilder == null) {
			queryBuilder = new MatchAllQueryBuilder();
		}
		CountRequest countRequest = new CountRequest();
		countRequest.indices(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder);
		countRequest.source(searchSourceBuilder);

		CountResponse countResponse = null;
		try {
			countResponse = client.count(countRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			log.error("count doc error, index:{}", index, e);
		}
		if (countResponse == null) {
			return 0;
		}
		return (int) countResponse.getCount();
	}

	// public static <T> List<T> searchDocument(RestHighLevelClient client,
	// String index,
	// QueryBuilder queryBuilder,
	// String[] includes,
	// Class<T> type,
	// Integer count,
	// Integer pageSize
	// ){
	//
	//
	// }

	/**
	 * 查询你索引文档
	 *
	 * @param client
	 * @param index
	 * @return
	 */
	public static  <T> List<T>  searchDocument(RestHighLevelClient client,
											   String index,
											   QueryBuilder queryBuilder,
											   String[] includes,
											   Class<T> type
	) {


		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.from(0);
		if(Objects.nonNull(queryBuilder)){
			searchSourceBuilder.query(queryBuilder);
		}
		// 设置超时时间为5s
		searchSourceBuilder.timeout(new TimeValue(5000));
		searchSourceBuilder.size(10000);
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(searchSourceBuilder);

		if(Objects.nonNull(includes)){
			searchSourceBuilder.fetchSource(includes, new String[]{});
		}

		try {
			SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
			SearchHits hits = search.getHits();

			if(Objects.nonNull(hits) && Objects.nonNull(hits.getHits())){
				return Arrays.stream(hits.getHits()).map(i->JSON.parseObject(i.getSourceAsString(),type))
						.collect(Collectors.toList());
			}

			return null;

		} catch (IOException e) {
			log.error("构建查询失败",e);
			throw new RuntimeException("search doc error");
		}
	}

	public static void updateBulkDocumentByQuery(RestHighLevelClient client, String index, QueryBuilder queryBuilder,
												 Script script) {

		UpdateByQueryRequest updateByQuery = new UpdateByQueryRequest(index);
		updateByQuery.setQuery(queryBuilder);
		updateByQuery.setScript(script);
		updateByQuery.setAbortOnVersionConflict(false);
		updateByQuery.setRefresh(true);

		try {
			client.updateByQuery(updateByQuery, RequestOptions.DEFAULT);
			log.info("update document success, name:{}, result:{}", index);
		} catch (IOException e) {
			log.error("update document fall, index:{}", index, e);
			throw new RuntimeException("update document fall");
		}
	}

	/**
	 * terms聚合会返回bucket
	 * @param client
	 * @param index
	 * @param aggregationBuilder
	 * @return
	 */
	public static List<? extends Terms.Bucket> aggTerms(RestHighLevelClient client, String index,
																   AggregationBuilder aggregationBuilder) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.aggregation(aggregationBuilder);
		// 设置超时时间为5s
		searchSourceBuilder.timeout(new TimeValue(2000));

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(searchSourceBuilder);

		try {
			SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = search.getAggregations();
			Terms term = aggregations.get(aggregationBuilder.getName());
			return term.getBuckets();

		} catch (IOException e) {
			log.error("构建查询失败", e);
			throw new RuntimeException("search doc error");
		}
	}

	/**
	 * 普通聚合，返回Aggregations
	 * @param client
	 * @param index
	 * @param aggregationBuilder
	 * @param queryBuilder
	 * @return
	 */
	public static Aggregations agg(RestHighLevelClient client, String index,
											AggregationBuilder aggregationBuilder, QueryBuilder queryBuilder) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		if (Objects.nonNull(queryBuilder)) {
			searchSourceBuilder.query(queryBuilder);
		}

		searchSourceBuilder.aggregation(aggregationBuilder);
		// 设置超时时间为5s
		searchSourceBuilder.timeout(new TimeValue(2000));

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(searchSourceBuilder);

		try {
			SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
			return search.getAggregations();
		} catch (IOException e) {
			log.error("构建查询失败", e);
			throw new RuntimeException("search doc error");
		}
	}

	/**
	 * 更新文档
	 *
	 * @param client
	 * @param index
	 * @param id
	 * @param document
	 * @param <T>
	 */
	public static <T> void updatePartialDocumentAsync(RestHighLevelClient client, String index, T id, String document) {
		ActionListener listener = new ActionListener<UpdateResponse>() {
			@Override
			public void onResponse(UpdateResponse updateResponse) {
				log.info("update document success, name:{}, id:{}, result:{}", updateResponse.getIndex(),
						updateResponse.getId(), updateResponse.getResult());
			}

			@Override
			public void onFailure(Exception e) {
				log.error("update document fall, index:{}", index, e);
			}
		};
		UpdateRequest request = new UpdateRequest(index, String.valueOf(id)).setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE).doc(document, XContentType.JSON);
		client.updateAsync(request, RequestOptions.DEFAULT, listener);
	}

	/**
	 * 更新文档
	 * ps: If both doc and script are specified, then doc is ignored. If you specify a scripted update,
	 * 	include the fields you want to update in the script.
	 * 	如果同时制定了文档内容和脚本，则会忽略文档内容
	 *
	 * @param client
	 * @param index
	 * @param id
	 * @param document
	 * @param <T>
	 * @param <D>
	 */
	public static <T, D> void updatePartialDocument(RestHighLevelClient client, String index, T id, D document) {
		UpdateRequest request = new UpdateRequest(index, String.valueOf(id)).setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE).doc(
				JSONObject.toJSONString(document, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue), XContentType.JSON);
		try {
			UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
			log.info("update document success, name:{}, id:{}, result:{}", updateResponse.getIndex(),
					updateResponse.getId(), updateResponse.getResult());
		} catch (Exception e) {
			log.error("update document fall, index:{}", index, e);
		}
	}

	/**
	 * 使用脚本更新制定文档
	 * @param client
	 * @param index
	 * @param id
	 * @param script
	 * @param <T>
	 * @param <D>
	 */
	public static <T, D> void updatePartialDocumentByScript(RestHighLevelClient client, String index, T id,
															Script script, Map<String, Object> upsert) {
		UpdateRequest request = new UpdateRequest(index, String.valueOf(id)).setRefreshPolicy(
				WriteRequest.RefreshPolicy.IMMEDIATE)
				.script(script);
		if(upsert != null) {
			request.upsert(upsert);
		}
		try {
			UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
			log.info("update document success, name:{}, id:{}, result:{}", updateResponse.getIndex(),
					updateResponse.getId(), updateResponse.getResult());
		} catch (Exception e) {
			log.error("update document fall, index:{}", index, e);
		}
	}

	public static void updateBulkDocument(RestHighLevelClient client, String index, List<JSONObject> documents) {
		BulkRequest request = new BulkRequest();
		Optional.ofNullable(documents).orElse(new ArrayList<>())
				.forEach(doc ->
						request.add(
								new UpdateRequest(index, doc.getString("id"))
										.doc(JSONObject.toJSONString(doc, SerializerFeature.DisableCircularReferenceDetect), XContentType.JSON)
						)
				);
		try {
			request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
			log.info("update document success, name:{}, result:{}", index);
		} catch (IOException e) {
			log.error("update document fall, index:{}", index, e);
			throw new RuntimeException("update document fall");
		}
	}

	public static void updateByQuery(RestHighLevelClient client, String index, AbstractQueryBuilder queryBuilder,
								  Script script, boolean proceed, boolean refresh) {
		UpdateByQueryRequest updateByQuery = new UpdateByQueryRequest(index);
		// 设置分片并行
		updateByQuery.setSlices(2);

		// 设置版本冲突时继续执行
		if (proceed) {
			updateByQuery.setConflicts("proceed");
		}
		// 设置更新完成后刷新索引 ps很重要如果不加可能数据不会实时刷新
		if (refresh) {
			updateByQuery.setRefresh(true);
		}

		// 查询条件如果是and关系使用must 如何是or关系使用should
		updateByQuery.setQuery(queryBuilder);

		// 设置要修改的内容可以多个值多个用；隔开
		updateByQuery.setScript(script);
		try {
			BulkByScrollResponse response = client.updateByQuery(updateByQuery, RequestOptions.DEFAULT);
			log.info("update document success, name:{}, updated count:{}", index, response.getStatus().getUpdated());
		} catch (IOException e) {
			log.info("update document fall, index:{}", index, e);
			throw new RuntimeException("confirm all document fall");
		}
	}

	/**
	 * 删除文档（异步）
	 *
	 * @param client
	 * @param index
	 * @param id
	 * @param <T>
	 */
	public static <T> void deleteDocumentAsync(RestHighLevelClient client, String index, T id) {
		ActionListener listener = new ActionListener<DeleteResponse>() {
			@Override
			public void onResponse(DeleteResponse deleteResponse) {
				log.info("delete document success, name:{}, id:{}, result:{}", deleteResponse.getIndex(),
						deleteResponse.getId(), deleteResponse.getResult());
			}

			@Override
			public void onFailure(Exception e) {
				log.info("delete document fall, index:{} ", index, e);
			}
		};
		DeleteRequest request = new DeleteRequest(index, String.valueOf(id));
		client.deleteAsync(request, RequestOptions.DEFAULT, listener);
	}

	/**
	 * 删除文档
	 *
	 * @param client
	 * @param index
	 * @param id
	 * @param <T>
	 */
	public static <T> void deleteDocument(RestHighLevelClient client, String index, T id) {
		DeleteRequest request = new DeleteRequest(index, String.valueOf(id));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = client.delete(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.info("delete document fall, index:{} ", index, e);
			throw new RuntimeException("delete document fall");
		}
		log.info("delete document success, name:{}, id:{}, result:{}", deleteResponse.getIndex(),
				deleteResponse.getId(), deleteResponse.getResult());
	}

	/**
	 * 批量删除文档
	 *
	 * @param client
	 * @param index
	 * @param ids
	 * @param <T>
	 */
	public static <T> void deleteDocumentBulk(RestHighLevelClient client, String index, List<T> ids) {
		BulkRequest bulkRequest = new BulkRequest();
		ids.forEach(id -> {
			DeleteRequest deleteRequest = new DeleteRequest(index, String.valueOf(id));
			bulkRequest.add(deleteRequest);
		});
		BulkResponse bulk = null;
		try {
			bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
			bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
			log.info("delete document success, name:{}", index);
		} catch (Exception e) {
			log.info("delete document fall, index:{} ", index, e);
			throw new RuntimeException("delete document fail");
		}
	}

	public static long deleteByQuery(RestHighLevelClient restHighLevelClient, String indexName, Map<String, Object> mustMap) {
		try {
			DeleteByQueryRequest request =
					new DeleteByQueryRequest(indexName);
			// 根据多个条件 生成 boolQueryBuilder
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

			for (Map.Entry<String, Object> entry : mustMap.entrySet()) {
				boolQueryBuilder
						.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
			}
			request.setQuery(boolQueryBuilder);

			BulkByScrollResponse bulkResponse =
					restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
			long deleted = bulkResponse.getDeleted();
			log.info("delete by query document success, index:{}, param:{} ,deleted count:{}", indexName, mustMap, deleted);
			return deleted;
		}catch (Exception e){
			log.info("delete by query document fail, index:{}, param:{} ,error:{}", indexName, mustMap, e.getMessage());
			throw new RuntimeException("delete document fail");
		}
	}

}
