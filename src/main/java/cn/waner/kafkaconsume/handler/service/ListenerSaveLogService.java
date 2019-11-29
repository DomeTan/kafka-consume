package cn.waner.kafkaconsume.handler.service;

import cn.waner.kafkaconsume.file.service.FileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
@Component
public class ListenerSaveLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerSaveLogService.class);

    private FileService fileService;


    @Autowired
    public ListenerSaveLogService(FileService fileService) {
        this.fileService = fileService;
    }

//    @KafkaListener(topics = "first_floor",groupId = "second_worker")
//    public void handleEventMatching(List<ConsumerRecord<?,?>> recordList) {
//        if(recordList.size() > 0){
//            recordList.forEach(record ->{
//
//                fileService.save((byte[]) record.value());
//            });
//            LOGGER.info("{}",atomicLong.get());
//        }
//    }

    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"0"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching0(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }


    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"1"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching1(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }

    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"2"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching2(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }

    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"3"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching3(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }
    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"4"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching4(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }
    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"5"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching5(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }
    @KafkaListener(groupId = "second_worker",topicPartitions = { @TopicPartition(topic = "first_floor", partitions = {"6"})},containerFactory = "kafkaListenerContainerFactory")
    public void handleEventMatching6(List<ConsumerRecord<?,?>> recordList) {
        if(recordList.size() > 0){
            recordList.forEach(record ->{

                fileService.save((byte[]) record.value());
            });

        }
    }
}
