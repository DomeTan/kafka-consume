package cn.waner.kafkaconsume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class KafkaConsumeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumeApplication.class, args);
    }

}
