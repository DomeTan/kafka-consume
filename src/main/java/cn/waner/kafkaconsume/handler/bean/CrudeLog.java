package cn.waner.kafkaconsume.handler.bean;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Document(indexName = "was_cloud",type = "crude_log",shards = 5,replicas = 0,refreshInterval = "10s")
public class CrudeLog {

    private Long id;

    private String srcAddr;

    private String msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSrcAddr() {
        return srcAddr;
    }

    public void setSrcAddr(String srcAddr) {
        this.srcAddr = srcAddr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
