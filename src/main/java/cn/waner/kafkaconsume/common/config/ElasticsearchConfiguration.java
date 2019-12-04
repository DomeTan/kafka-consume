package cn.waner.kafkaconsume.common.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;


/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Configuration
public class ElasticsearchConfiguration  {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    @Bean
    public BulkProcessor bulkProcessor() throws UnknownHostException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic","Waner123456"));
        Settings settings = Settings.builder()
                .put("cluster.name", "waner-es-cluster")
                .put("client.transport.sniff",true)
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
//        TransportAddress transportAddress0 = new TransportAddress(Inet4Address.getByName("172.168.1.210"), 9300);
//        TransportAddress transportAddress1 = new TransportAddress(Inet4Address.getByName("172.168.1.211"), 9300);
        TransportAddress transportAddress2 = new TransportAddress(Inet4Address.getByName("172.168.1.212"), 9300);
        TransportAddress transportAddress3 = new TransportAddress(Inet4Address.getByName("172.168.1.213"), 9300);
        TransportAddress transportAddress4 = new TransportAddress(Inet4Address.getByName("172.168.1.214"), 9300);
        transportClient.addTransportAddresses(transportAddress2,transportAddress3,transportAddress4);
        final Long[] start = {0L};
        return BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                start[0] = System.currentTimeMillis();
                LOGGER.info("批量执行 [{}] with {} 请求 {}", executionId, executionId,start);
            }
            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if(response.hasFailures()){
                    LOGGER.error("Bulk [{}] 执行失败,响应 = {}", executionId, response.buildFailureMessage());
                } else {
                    long end = System.currentTimeMillis();
                    LOGGER.info("Bulk [{}] 请求数据 {} 完成于 {} milliseconds 用时 {}", executionId,request.numberOfActions(), response.getTook().getMillis(),(end-start[0]));
                }
                BulkItemResponse[] responses = response.getItems();

            }
            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                LOGGER.error("{} 批量数据失败, 原因 :{}", request.numberOfActions(), failure);
            }
        }).setBulkActions(20000)  //每2w条执行一次bulk插入
                .setBulkSize(new ByteSizeValue(23, ByteSizeUnit.MB)) //  数据量达到25M后执行bulk插入
                .setFlushInterval(TimeValue.timeValueSeconds(20)) //无论数据量多少，间隔20s执行一次bulk
                .setConcurrentRequests(10) // 允许并发的bulk请求数
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
    }

}
