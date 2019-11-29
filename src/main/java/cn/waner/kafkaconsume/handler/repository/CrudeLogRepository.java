package cn.waner.kafkaconsume.handler.repository;

import cn.waner.kafkaconsume.handler.bean.CrudeLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Repository
public interface CrudeLogRepository extends ElasticsearchRepository<CrudeLog,Long> {
}
