//package cn.waner.kafkaconsume.common.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
///**
// * @author TanChong
// * create date 2019\11\28 0028
// */
//@Configuration
//public class ElasticsearchConfiguration implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfiguration.class);
//    @Value("${spring.elasticsearch.rest.username}")
//    private String username;
//
//    @Value("${spring.elasticsearch.rest.password}")
//    private String password;
//
//    private RestHighLevelClient restHighLevelClient;
//
//
//    @Override
//    public void destroy()  {
//
//        LOGGER.info("Closing elasticSearch client");
//        if(restHighLevelClient != null){
//            try {
//                restHighLevelClient.close();
//            } catch (IOException e) {
//                LOGGER.error("Error closing ElasticSearch client: ", e);
//            }
//        }
//    }
//
//    @Override
//    public RestHighLevelClient getObject() throws Exception {
//
//        return restHighLevelClient;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//
//        return RestHighLevelClient.class;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        buildClient();
//    }
//
//
//    @Bean
//    protected void buildClient(){
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(username,password));
//        restHighLevelClient = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("172.168.1.214", 9200),
//                        new HttpHost("172.168.1.213", 9200),
//                        new HttpHost("172.168.1.212", 9200),
//                        new HttpHost("172.168.1.211", 9200),
//                        new HttpHost("172.168.1.214", 9200))
//                        .setHttpClientConfigCallback(httpClientBuilder -> {
//                            httpClientBuilder.disableAuthCaching();
//                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//                        })
//        );
//    }
//}
