package cn.waner.kafkaconsume.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author TanChong
 * create date 2019\11\9 0009
 */
@Configuration
public class HdfsConfig {

    @Value("${first_floor.hdfs.path}")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
