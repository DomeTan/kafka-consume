package cn.waner.kafkaconsume.handler.bean;

import cn.waner.kafkaconsume.handler.enumeration.AssetBackupStrategy;
import cn.waner.kafkaconsume.handler.enumeration.AssetSource;
import cn.waner.kafkaconsume.handler.enumeration.AssetWeight;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
public class Asset {

    /**
     * Id。
     */
    private ObjectId id;

    /**
     * 资产名字。
     */
    private String name;

    /**
     * IP地址。
     */
    private String ipAddress;

    /**
     * 资产类型类型。
     */
    private String typeCategory;

    /**
     * 资产类型。
     */
    private String type;

    /**
     * 资产厂商。
     */
    private String company;

    /**
     * 资产型号。
     */
    private String model;

    /**
     * 是否开启采集。
     */
    private boolean isEnable = true;

    /**
     * 资产权重。
     */
    private AssetWeight weight;

    /**
     * 资产来源。
     */
    private AssetSource assetSource;

    /**
     * 资产内外网区分。
     */
    private int assetClass;

    /**
     * 资产备份策略。
     */
    private AssetBackupStrategy assetBackupStrategy = AssetBackupStrategy.NULL;

    /**
     * 创建人。
     */
    private ObjectId createUser;

    /**
     * 创建时间。
     */
    private Timestamp createTime;

    /**
     * 更新时间。
     */
    private Timestamp updateTime;

    /**
     * 供应商。
     */
    private ObjectId provider;

    /**
     * 备注。
     */
    private String note;

    /**
     * 收集方式。
     */
    private String gatherMethod;

    /**
     * 备注。
     */
    private String assetLabel;

    /**
     * 日志结构。
     */
    private List<Integer> msgStruct;

    /**
     * 工程师Id。
     */
    private ObjectId engineerId;

    /**
     * 工程师姓名。
     */
    private String engineerName = "";

    /**
     * 工程师邮箱。
     */
    private String engineerMail = "";

    /**
     * 工程师电话。
     */
    private String engineerPhone;

    /**
     * 所属分组
     */
    private String groupName;

    /**
     * 官网地址
     */
    private String url;

    /**
     * ftp日志
     */
//    private FtpLog ftpLog;

    /**
     * windows共享路径
     */
    private String windowsSharePath;

    /**
     * windows共享用户名
     */
    private String windowsShareUsername;

    /**
     * windows共享密码
     */
    private String windowsSharePassword;

    public Asset() {}

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(ipAddress)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Asset){
            final Asset other = (Asset) obj;
            return new EqualsBuilder()
                    .append(id, other.id)
                    .append(name, other.name)
                    .append(ipAddress, other.ipAddress)
                    .isEquals();
        } else{
            return false;
        }
    }

    public Asset(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTypeCategory() {
        return typeCategory;
    }

    public void setTypeCategory(String typeCategory) {
        this.typeCategory = typeCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public AssetWeight getWeight() {
        return weight;
    }

    public void setWeight(AssetWeight weight) {
        this.weight = weight;
    }

    public AssetSource getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(AssetSource assetSource) {
        this.assetSource = assetSource;
    }

    public int getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(int assetClass) {
        this.assetClass = assetClass;
    }

    public AssetBackupStrategy getAssetBackupStrategy() {
        return assetBackupStrategy;
    }

    public void setAssetBackupStrategy(AssetBackupStrategy assetBackupStrategy) {
        this.assetBackupStrategy = assetBackupStrategy;
    }

    public ObjectId getCreateUser() {
        return createUser;
    }

    public void setCreateUser(ObjectId createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public ObjectId getProvider() {
        return provider;
    }

    public void setProvider(ObjectId provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Integer> getMsgStruct() {
        return msgStruct;
    }

    public void setMsgStruct(List<Integer> msgStruct) {
        this.msgStruct = msgStruct;
    }

    public ObjectId getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(ObjectId engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getEngineerMail() {
        return engineerMail;
    }

    public void setEngineerMail(String engineerMail) {
        this.engineerMail = engineerMail;
    }

    public String getEngineerPhone() {
        return engineerPhone;
    }

    public void setEngineerPhone(String engineerPhone) {
        this.engineerPhone = engineerPhone;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public FtpLog getFtpLog() {
//        return ftpLog;
//    }
//
//    public void setFtpLog(FtpLog ftpLog) {
//        this.ftpLog = ftpLog;
//    }

    public String getGatherMethod() {
        return gatherMethod;
    }

    public void setGatherMethod(String gatherMethod) {
        this.gatherMethod = gatherMethod;
    }

    public String getAssetLabel() {
        return assetLabel;
    }

    public void setAssetLabel(String assetLabel) {
        this.assetLabel = assetLabel;
    }

    public String getWindowsSharePath() {
        return windowsSharePath;
    }

    public void setWindowsSharePath(String windowsSharePath) {
        this.windowsSharePath = windowsSharePath;
    }

    public String getWindowsShareUsername() {
        return windowsShareUsername;
    }

    public void setWindowsShareUsername(String windowsShareUsername) {
        this.windowsShareUsername = windowsShareUsername;
    }

    public String getWindowsSharePassword() {
        return windowsSharePassword;
    }

    public void setWindowsSharePassword(String windowsSharePassword) {
        this.windowsSharePassword = windowsSharePassword;
    }
}
