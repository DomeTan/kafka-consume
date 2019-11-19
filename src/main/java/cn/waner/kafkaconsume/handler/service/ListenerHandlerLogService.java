package cn.waner.kafkaconsume.handler.service;

import cn.waner.kafkaconsume.file.service.FileService;
import cn.waner.kafkaconsume.handler.bean.Asset;
import cn.waner.kafkaconsume.handler.bean.CrudeLog;
import cn.waner.kafkaconsume.util.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
@Component
public class ListenerHandlerLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerHandlerLogService.class);

    private FileService fileService;
    private AtomicLong atomicLong;
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    public ListenerHandlerLogService(FileService fileService,
                                     @Qualifier("runSqlThreadPoolTaskExecutor") ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.fileService = fileService;
        atomicLong = new AtomicLong(0);
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @KafkaListener(topics = "log",groupId = "matching_event")
    public void handleEventMatching(String content) {
        handleEventMatching(content,1);
//        threadPoolTaskExecutor.submit(() -> handleEventMatching(content,1));

    }

    private void handleEventMatching(String content,int a){
        try {
            var root = JsonUtil.OBJECT_MAPPER.readTree(content);
            atomicLong.addAndGet(1);
            JsonNode log = root.get("log");
            var timestamp = root.get("timestamp").asLong();
            handleEventMatching(log.toPrettyString(), timestamp);
        } catch (IOException e) {
            LOGGER.error("[handleEventMatching] Convert json from queue failure.\n" +
                    "message: {}", content, e);
        }
    }

    private void handleEventMatching(String crudeLog,Long time){
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();
        fileService.save(String.valueOf(minute), crudeLog);
    }
}
