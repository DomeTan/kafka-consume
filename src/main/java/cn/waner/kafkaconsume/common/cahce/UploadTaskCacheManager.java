package cn.waner.kafkaconsume.common.cahce;

import cn.waner.kafkaconsume.handler.service.KafkaService;
import com.google.common.collect.Sets;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HDFS 上传任务缓存管理类
 * @author TanChong
 * create date 2019\11\22 0022
 */
@Component
public class UploadTaskCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadTaskCacheManager.class);
    @Value("${kafka.template.task.default-topic}")
    private String TOPIC;
    private KafkaService kafkaService;
    private AtomicInteger atomicInteger;


    @Autowired
    public UploadTaskCacheManager(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
        atomicInteger = new AtomicInteger(0);
    }

    /**
     * 总缓存Map (整天的upload task)
     */
    private static final ConcurrentHashMap<String, Long> CACHE_MAP = new ConcurrentHashMap<>();
    /**
     * Task Map (保存待upload task)
     */
    private static final ConcurrentHashMap<String, Long> TASK_MAP = new ConcurrentHashMap<>();
    /**
     * 中转Map (保存已经upload task)
     */
    private static final ConcurrentHashMap<String, Long> TRANSFER_MAP = new ConcurrentHashMap<>();

    /**
     * 获取缓存值。
     */
    public Long getCacheBean(String key) {
        return CACHE_MAP.get(key);
    }

    public boolean exists(String key){
        return CACHE_MAP.containsKey(key);
    }

    /**
     * 更新缓存值。
     */
    public void updateCacheBean(String key, Long value) {
        CACHE_MAP.put(key, value);
    }

    /**
     * 设置缓存值。
     */
    public void createCacheBean(String key, Long value) {
        CACHE_MAP.put(key, value);
    }

    /**
     * 提交任务
     */
    public void pushCacheBean(){
        if(CACHE_MAP.isEmpty())return;
        long start = System.currentTimeMillis();
        if (TRANSFER_MAP.isEmpty()){
            TASK_MAP.putAll(CACHE_MAP);
            pushCacheBean(TASK_MAP);
        }else {
            ConcurrentHashMap.KeySetView<String, Long> cacheSetView = CACHE_MAP.keySet();
            ConcurrentHashMap.KeySetView<String, Long> traSetView = TRANSFER_MAP.keySet();
            Sets.SetView<String> difference = Sets.difference(cacheSetView, traSetView);
            for (String key : difference){
                TASK_MAP.put(key,0L);
            }
            pushCacheBean(TASK_MAP);
        }
        long end = System.currentTimeMillis();
    }

    private void pushCacheBean(ConcurrentHashMap<String,Long> map){
        map.forEach((key,value)->{
            atomicInteger.addAndGet(1);

            kafkaService.pushTask(key,TOPIC);
            TRANSFER_MAP.put(key, value);
        });
        TASK_MAP.clear();
    }

    public void clear(){
        TRANSFER_MAP.clear();
        CACHE_MAP.clear();
    }

}
