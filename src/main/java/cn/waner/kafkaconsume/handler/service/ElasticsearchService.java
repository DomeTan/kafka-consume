//package cn.waner.kafkaconsume.handler.service;
//
//import cn.waner.kafkaconsume.handler.bean.CrudeLog;
//import com.google.gson.Gson;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.RandomUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.IndexQuery;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * @author TanChong
// * create date 2019\11\28 0028
// */
//@Service
//public class ElasticsearchService {
//
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    private static final String INDEX_NAME = "was-cloud";
//
//    private static final String INDEX_TYPE = "crude_log";
//
//
//    @Autowired
//    public ElasticsearchService(ElasticsearchTemplate elasticsearchTemplate) {
//        this.elasticsearchTemplate = elasticsearchTemplate;
//    }
//
//    public long bulkIndex(){
//
//        int counter = 0;
//
//        if(!elasticsearchTemplate.indexExists(INDEX_NAME)){
//            elasticsearchTemplate.createIndex(INDEX_NAME);
//        }
//
//        Gson gson = new Gson();
//        List queries = new ArrayList();
//        List<CrudeLog> cars = assembleTestData();
//
//
//        for (CrudeLog car : cars) {
//            IndexQuery indexQuery = new IndexQuery();
//            indexQuery.setId(car.getId().toString());
//            indexQuery.setSource(car.getMsg());
//            indexQuery.setIndexName(INDEX_NAME);
//            indexQuery.setType(INDEX_TYPE);
//            queries.add(indexQuery);
//            if (counter % 500 == 0) {
//                elasticsearchTemplate.bulkIndex(queries);
//                queries.clear();
//                System.out.println("bulkIndex counter : " + counter);
//            }
//            counter++;
//        }
//        //不足批的索引最后不要忘记提交
//
//        if (queries.size() > 0) {
//            elasticsearchTemplate.bulkIndex(queries);
//        }
//        elasticsearchTemplate.refresh(INDEX_NAME);
//
//        System.out.println("bulkIndex completed.");
//
//        return -1;
//    }
//
//    private List<CrudeLog> assembleTestData(){
//        CrudeLog crudeLog = new CrudeLog();
//        List<CrudeLog> crudeLogs = new ArrayList<>();
//
//        for (int i = 0; i < 10000; i++) {
//            final double d = Math.random();
//            final int ip = (int)(d*100);
//            crudeLog.setId((long) i);
//            crudeLog.setMsg("2019-11-22 18:10:00 WARNING nxlog-ce received a termination request signal, exiting");
//            String ipAddr = "172.168.1."+ip;
//            crudeLog.setSrcAddr(ipAddr);
//        }
//        return crudeLogs;
//    }
//}
