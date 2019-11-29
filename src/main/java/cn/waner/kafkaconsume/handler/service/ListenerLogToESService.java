package cn.waner.kafkaconsume.handler.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.netty.util.internal.StringUtil.LINE_FEED;

/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Component
public class ListenerLogToESService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerSaveLogService.class);

    private ElasticsearchService elasticsearchService;
    // 源IP字节长度 LINUX
    private final static int LEN_SRC_ADDR=12;
    // 源IP字节长度 WINDOWS
//    private final static int LEN_SRC_ADDR=15;

    @Autowired
    public ListenerLogToESService(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @KafkaListener(groupId = "to-es",containerFactory = "kafkaListenerContainerFactory")
    private void listenerLogToEs(List<ConsumerRecord<?,?>> recordList){
        if(recordList.size() > 0){
            recordList.forEach(record ->{
                byte[] content = (byte[]) record.value();
                byte[] ipAddrBytes = new byte[LEN_SRC_ADDR];
                // 获取源IP
                System.arraycopy(content,0,ipAddrBytes,0,LEN_SRC_ADDR);
                String suffixPath = new String(ipAddrBytes).trim();
                LOGGER.info("{} {}",suffixPath,new String(content));
            });

        }
    }
}
