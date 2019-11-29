package cn.waner.kafkaconsume.common.config;

import cn.waner.kafkaconsume.common.cahce.UploadTaskCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author TanChong
 * create date 2019\11\22 0022
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
    private UploadTaskCacheManager uploadTaskCacheManager;

    @Autowired
    public ScheduledTasks(UploadTaskCacheManager uploadTaskCacheManager) {
        this.uploadTaskCacheManager = uploadTaskCacheManager;
    }

    @Async
    @Scheduled(fixedDelay = 1_000)
    public void uploadTask(){
        uploadTaskCacheManager.pushCacheBean();
    }

    @Async
    @Scheduled(cron = "0 0 3 * * *")
    public void clear(){
        uploadTaskCacheManager.clear();
    }

}
