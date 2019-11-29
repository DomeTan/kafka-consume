package cn.waner.kafkaconsume.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置。
 * @author Gotham
 * Create Date: 2019-09-09
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("ReceiverLogThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor receiverLogThreadPoolTaskExecutor(
           ) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = setThreadPoolTackExecutor(10, 20,
                30, 60);
        threadPoolTaskExecutor.setThreadNamePrefix("receiverLogThreadExecutor-");

        return threadPoolTaskExecutor;
    }

    @Bean("runSqlThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor runSqlThreadPoolTaskExecutor() {

        ThreadPoolTaskExecutor threadPoolTaskExecutor = setThreadPoolTackExecutor(10, 15,
                10, 50);
        threadPoolTaskExecutor.setThreadNamePrefix("pullLogThreadExecutor-");

        return threadPoolTaskExecutor;
    }

    private ThreadPoolTaskExecutor setThreadPoolTackExecutor (int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);



        return threadPoolTaskExecutor;
    }

}
