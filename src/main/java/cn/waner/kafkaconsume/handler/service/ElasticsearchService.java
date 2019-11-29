package cn.waner.kafkaconsume.handler.service;

import cn.waner.kafkaconsume.handler.bean.CrudeLog;
import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Service
public class ElasticsearchService {

    private ElasticsearchTemplate elasticsearchTemplate;

    private static final String INDEX_NAME = "was-cloud";

    private static final String INDEX_TYPE = "crude_log";


    @Autowired
    public ElasticsearchService(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public long bulkIndex(List<CrudeLog> crudeLogs){
        int counter = 0;

        if(!elasticsearchTemplate.indexExists(INDEX_NAME)){
            elasticsearchTemplate.createIndex(INDEX_NAME);
        }
        ArrayList<IndexQuery> indexQueries = new ArrayList<>();
        for (CrudeLog crudeLog : crudeLogs) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(crudeLog.getId().toString());
            indexQuery.setSource(crudeLog.getMsg());
            indexQuery.setIndexName(INDEX_NAME);
            indexQuery.setType(INDEX_TYPE);
            indexQueries.add(indexQuery);
            if (counter % 500 == 0) {
                elasticsearchTemplate.bulkIndex(indexQueries);
                indexQueries.clear();
                System.out.println("bulkIndex counter : " + counter);
            }
            counter++;
        }
        //不足批的索引最后不要忘记提交

        if (indexQueries.size() > 0) {
            elasticsearchTemplate.bulkIndex(indexQueries);
        }
        elasticsearchTemplate.refresh(INDEX_NAME);

        System.out.println("bulkIndex completed.");

        return -1;
    }
}
