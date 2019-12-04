//package cn.waner.kafkaconsume;
//
//import cn.waner.kafkaconsume.handler.bean.CrudeLog;
//import org.elasticsearch.action.bulk.BulkProcessor;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class KafkaConsumeApplicationTests {
//
//    @Autowired
//    private BulkProcessor bulkProcessor;
//
//    @Test
//   public void contextLoads() {
////        ArrayList<CrudeLog> crudeLogs = new ArrayList<>();
////        for (int i = 0; i < 10000; i++) {
////            CrudeLog crudeLog = new CrudeLog();
////            crudeLog.setId((long) i);
////            crudeLog.setSrcAddr("172.168.1.15");
////            crudeLog.setMsg("2019-11-22 18:34:32 WARNING nxlog-ce received a termination request signal, exiting..."+ i);
////            crudeLogs.add(crudeLog);
////        }
//        CrudeLog crudeLog = new CrudeLog();
//        crudeLog.setId(9L);
//        crudeLog.setSrcAddr("172.168.1.15");
//        crudeLog.setMsg("2019-11-22 18:34:32 WARNING nxlog-ce received a termination request signal, exiting.sasas..");
//        bulkProcessor.add(new IndexRequest("was-cloud", "crude_log",String.valueOf(crudeLog.getId())).source(crudeLog, XContentType.JSON));
//
//
//    }
//    @After
//    public void clean(){
//        if(bulkProcessor != null){
//            bulkProcessor.close();
//            System.out.println("清空...");
//        }
//    }
//
//}
