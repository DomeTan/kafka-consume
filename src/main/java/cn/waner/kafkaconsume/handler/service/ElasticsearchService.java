package cn.waner.kafkaconsume.handler.service;

import cn.waner.kafkaconsume.handler.bean.CrudeLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.UUIDs;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Service
public class ElasticsearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchService.class);
    private final static String INDEX_NAME = "was-cloud";
    private final static String INDEX_TYPE = "crude_log";

    private BulkProcessor bulkProcessor;

    @Autowired
    public ElasticsearchService(BulkProcessor bulkProcessor) {
        this.bulkProcessor = bulkProcessor;
    }
    public void insert(CrudeLog crudeLog){
        try {
            String data = new ObjectMapper().writeValueAsString(crudeLog);
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, INDEX_TYPE).source(data, XContentType.JSON);
            bulkProcessor.add(indexRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
