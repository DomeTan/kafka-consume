//package cn.waner.kafkaconsume.handler.service;
//
//import org.elasticsearch.action.bulk.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.ByteSizeUnit;
//import org.elasticsearch.common.unit.ByteSizeValue;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author TanChong
// * create date 2019\11\28 0028
// */
//@Component
//public class ElasticSearchUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchUtil.class);
//
//
//    private RestHighLevelClient restHighLevelClient;
//
//    public BulkProcessor bulkProcessor;
//
//    @Autowired
//    public ElasticSearchUtil(@Qualifier("elasticsearchConfiguration") RestHighLevelClient restHighLevelClient) {
//        this.restHighLevelClient = restHighLevelClient;
//    }
//
//    @PostConstruct
//    public void init(){
//        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
//            @Override
//            public void beforeBulk(long executionId, BulkRequest request) {
//                int numberOfActions = request.numberOfActions();
//                LOGGER.info("Executing bulk [{}] with {} requests", executionId, numberOfActions);
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
//                if(response.hasFailures()){
//                    LOGGER.error("Bulk [{}] executed with failures,response = {}", executionId, response.buildFailureMessage());
//                } else {
//                    LOGGER.info("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
//                }
//                BulkItemResponse[] responses = response.getItems();
//
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
//                //重写方法，如果发生错误就会调用。
//                LOGGER.error("Failed to execute bulk", failure);
//            }
//        };
//        BulkProcessor.Builder builder = BulkProcessor.builder(
//                (bulkRequest, bulkResponseActionListener) ->
//                        restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener), listener);
//        // 6400条数据请求执行一次bulk
//        builder.setBulkActions(6400);
//        // 5mb的数据刷新一次bulk
//        builder.setBulkSize(new ByteSizeValue(5L, ByteSizeUnit.MB));
//        // 并发请求数量, 0不并发, 1并发允许执行
//        builder.setConcurrentRequests(1);
//        // 固定10s必须刷新一次
//        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
//        // 重试5次，间隔1s
//        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L),3));
//        this.bulkProcessor = builder.build();
//
//
////        BulkProcessor bulkProcessor = BulkProcessor.builder(restHighLevelClient::bulkAsync, listener)
////                         .setBulkActions(6400)
////                         // 5mb的数据刷新一次bulk
////                         .setBulkSize(new ByteSizeValue(5L, ByteSizeUnit.MB))
////                         // 并发请求数量, 0不并发, 1并发允许执行
////                         .setConcurrentRequests(0)
////                         // 固定1s必须刷新一次
////                         .setFlushInterval(TimeValue.timeValueSeconds(10L))
////                         // 重试5次，间隔1s
////                         .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 5))
////                         .build();
////                 this.bulkProcessor = bulkProcessor;
////        insert();
//    }
//
//    @PreDestroy
//    public void destory(){
//        try {
//            bulkProcessor.awaitClose(30, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            LOGGER.error("Failed to close bulkProcessor", e);
//        }
//        LOGGER.info("bulkProcessor closed!");
//    }
//
//    /**
//     * 修改
//     * @param request
//     */
//    public void update(UpdateRequest request){
//        this.bulkProcessor.add(request);
//    }
//
//    /**
//     * 新增
//     */
//    public void insert(){
//
//        IndexRequest one = new IndexRequest("posts").id("1")
//                .source(XContentType.JSON, "title",
//                        "In which order are my Elasticsearch queries executed?");
//        IndexRequest two = new IndexRequest("posts").id("2")
//                .source(XContentType.JSON, "title",
//                        "Current status and upcoming changes in Elasticsearch");
//        IndexRequest three = new IndexRequest("posts").id("3")
//                .source(XContentType.JSON, "title",
//                        "The Future of Federated Search in Elasticsearch");
//
//        bulkProcessor.add(one);
//        bulkProcessor.add(two);
//        bulkProcessor.add(three);
//        try {
//            boolean terminated  = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
//            LOGGER.info("bulkProcessor is close {}",terminated);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
