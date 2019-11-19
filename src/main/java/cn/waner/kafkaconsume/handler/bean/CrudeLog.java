package cn.waner.kafkaconsume.handler.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
public class CrudeLog {

    /**
     * 对象Id。
     */
    @Id
    private ObjectId id;

    /**
     * 资产Id。
     */
    @Field("asset_id")
    private ObjectId assetId;

    /**
     * 日志等级。
     */
    private int severity;

    /**
     * 程序模块。
     */
    private int facility;

    /**
     * 日志内容。
     */
    private String msg;

    /**
     * 日志的主机名。
     */
    private String hostname;

    /**
     * 日志产生时间。
     */
    private Timestamp timestamp;

    /**
     * 发送日志的源IP。
     */
    @Field("src_ip_address")
    private String srcIpAddress;

    /**
     * 细粒度字段。
     */
    @Field("fine_field")
    private Map<String, String> fineField;

    /**
     * 日志接收时间。
     */
    @Field("log_create_timestamp")
    private Timestamp logCreateTimestamp;

    @Indexed(expireAfterSeconds = 0)
    @Field("e_time")
    private Date expirationTime;

    public CrudeLog() {
    }

    public CrudeLog(ObjectId id, ObjectId assetId, int severity, int facility, Timestamp timestamp,
                    String hostname, String msg, String srcIpAddress, Timestamp logCreateTimestamp) {
        this.id = id;
        this.assetId = assetId;
        this.severity = severity;
        this.facility = facility;
        this.timestamp = timestamp;
        this.hostname = hostname;
        this.msg = msg;
        this.srcIpAddress = srcIpAddress;
        this.logCreateTimestamp = logCreateTimestamp;
    }

    @Override
    public String toString() {
        return "CrudeLog{" +
                "id=" + id +
                ", assetId=" + assetId +
                ", severity=" + severity +
                ", facility=" + facility +
                ", msg='" + msg + '\'' +
                ", hostname='" + hostname + '\'' +
                ", timestamp=" + timestamp +
                ", srcIpAddress='" + srcIpAddress + '\'' +
                ", fineField=" + fineField +
                ", logCreateTimestamp=" + logCreateTimestamp +
                '}';
    }





    public ObjectId getId() {
        return id;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getAssetId() {
        return assetId;
    }

    public void setAssetId(ObjectId assetId) {
        this.assetId = assetId;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getFacility() {
        return facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSrcIpAddress() {
        return srcIpAddress;
    }

    public void setSrcIpAddress(String srcIpAddress) {
        this.srcIpAddress = srcIpAddress;
    }

    public Map<String, String> getFineField() {
        return fineField;
    }

    public void setFineField(Map<String, String> fineField) {
        this.fineField = fineField;
    }

    public Timestamp getLogCreateTimestamp() {
        return logCreateTimestamp;
    }

    public void setLogCreateTimestamp(Timestamp logCreateTimestamp) {
        this.logCreateTimestamp = logCreateTimestamp;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(assetId)
                .append(severity)
                .append(facility)
                .append(msg)
                .append(hostname)
                .append(timestamp)
                .append(srcIpAddress)
                .append(logCreateTimestamp)
                .append(expirationTime)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CrudeLog){
            final CrudeLog other = (CrudeLog) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(assetId, other.assetId)
                    .append(severity, other.severity)
                    .append(facility, other.facility)
                    .append(msg, other.msg)
                    .append(hostname, other.hostname)
                    .append(timestamp, other.timestamp)
                    .append(srcIpAddress, other.srcIpAddress)
                    .append(logCreateTimestamp, other.logCreateTimestamp)
                    .append(expirationTime,other.expirationTime)
                    .isEquals();
        } else{
            return false;
        }
    }

}

