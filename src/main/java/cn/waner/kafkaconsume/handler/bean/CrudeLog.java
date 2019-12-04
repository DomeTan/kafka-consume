package cn.waner.kafkaconsume.handler.bean;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


/**
 * @author TanChong
 * create date 2019\11\28 0028
 */
@Document(indexName = "was-cloud",type = "crude_log",shards = 100, replicas = 0,refreshInterval = "30s")
public class CrudeLog {

    private Long id;

    private String srcAddr;


//    @Field(type = FieldType.Date,format = DateFormat.custom, pattern ="yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Long timestamp;

    @Field(analyzer = "standard")
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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CrudeLog{" +
                "id=" + id +
                ", srcAddr='" + srcAddr + '\'' +
                ", timestamp=" + timestamp +
                ", msg='" + msg + '\'' +
                '}';
    }
}
