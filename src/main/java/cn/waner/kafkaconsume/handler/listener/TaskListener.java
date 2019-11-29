package cn.waner.kafkaconsume.handler.listener;

import cn.waner.kafkaconsume.handler.service.HdfsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author TanChong
 * create date 2019\11\21 0021
 */
@Component
public class TaskListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListener.class);
    private static final String TOPIC = "upload-task";
    private static final String GROUP_ID = "pull_task";
    private HdfsService hdfsService;

    @Autowired
    public TaskListener(HdfsService hdfsService) {
        this.hdfsService = hdfsService;
    }

    @KafkaListener(containerFactory = "kafkaListenerTaskContainerFactory",topics = TOPIC,groupId = GROUP_ID)
    public void listener(String content){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hdfsService.upload(content);
    }
}
