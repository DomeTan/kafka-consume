package cn.waner.kafkaconsume.common.config;

import cn.waner.kafkaconsume.common.util.JsonUtil;
import cn.waner.kafkaconsume.handler.bean.CrudeLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author TanChong
 * create date 2019\12\2 0002
 */
public class ESCF {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESCF.class);
    private final static int PORT = 9300;
    private TransportClient transportClient;

    @Before
    public void setup() throws UnknownHostException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("elastic","Waner123456"));

        Settings settings = Settings.builder().put("cluster.name", "waner-es-cluster").build();

        transportClient = new PreBuiltTransportClient(settings);
        TransportAddress transportAddress0 = new TransportAddress(Inet4Address.getByName("172.168.1.214"), PORT);
//        TransportAddress transportAddress1 = new TransportAddress(Inet4Address.getByName("172.168.1.211"), PORT);
//        TransportAddress transportAddress2 = new TransportAddress(Inet4Address.getByName("172.168.1.212"), PORT);
//        TransportAddress transportAddress3 = new TransportAddress(Inet4Address.getByName("172.168.1.213"), PORT);
//        TransportAddress transportAddress4 = new TransportAddress(Inet4Address.getByName("172.168.1.214"), PORT);
//        transportClient.addTransportAddresses(transportAddress0,transportAddress1,transportAddress2,transportAddress3,transportAddress4);
        transportClient.addTransportAddresses(transportAddress0);

    }

    @Test
    public void test() throws JsonProcessingException {
        System.out.println("starting test....");

        Role role = new Role();
        role.setDescription("超级管理员拥有一切权限");
        role.setId(1L);
        role.setName("超级管理员");
        role.setAuthorities(Authority.getAllAuthority());

        BulkProcessor bulkProcessor = BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                LOGGER.info("执行批量 [{}] with {} 请求", executionId, executionId);
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                if(response.hasFailures()){
                    LOGGER.error("Bulk [{}] 执行失败, 响应 = {}", executionId, response.buildFailureMessage());
                } else {
                    LOGGER.info("Bulk [{}] 完成与 {} milliseconds", executionId, response.getTook().getMillis());
                }
                BulkItemResponse[] responses = response.getItems();
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                LOGGER.error("执行批量失败", failure);
            }
        }).setBulkActions(1)
                .setBulkSize(new ByteSizeValue(1, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(1))
                .setConcurrentRequests(1)
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        byte[] bytes = new ObjectMapper().writeValueAsBytes(role);
        bulkProcessor.add(new IndexRequest("role", "role").source(bytes,XContentType.JSON));

    }


    @After
    public void cleanup(){
        System.out.println("释放资源...");
        if(transportClient != null){
            transportClient.close();
        }
    }

}
