package cn.waner.kafkaconsume.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TanChong
 * create date 2019\12\4 0004
 */
public enum Authority {


    WAS_CLOUD_USER("WAS云-租户用户"),

    DASHBOARD_DASHBOARD_READ("首页-仪表板-仪表板查看"),

    ASSET_ASSET_READ("资产管理-资产列表-资产查看"),
    ASSET_ASSET_CREATE("资产管理-资产列表-资产创建"),
    ASSET_ASSET_DELETE("资产管理-资产列表-资产删除"),
    ASSET_ASSET_UPDATE("资产管理-资产列表-资产更新"),
    ASSET_ASSET_LOG_IMPORT("资产管理-资产列表-资产日志导入"),

    ASSET_ASSET_GROUP_READ("资产管理-资产分组管理-分组查看"),
    ASSET_ASSET_GROUP_CREATE("资产管理-资产分组管理-分组创建"),
    ASSET_ASSET_GROUP_DELETE("资产管理-资产分组管理-分组删除"),
    ASSET_ASSET_GROUP_UPDATE("资产管理-资产分组管理-分组更新"),

    ASSET_ASSET_IDENTIFIER_READ("资产管理-资产类型库-资产类型查看"),
    ASSET_ASSET_IDENTIFIER_IMPORT("资产管理-资产类型库-资产类型导入"),

    IMPORT_LOG_READ("资产管理-导入管理-导入查看"),
    IMPORT_LOG_DELETE("资产管理-导入管理-导入删除"),

    LOG_LOG_READ("日志管理-日志列表-日志查看"),
    LOG_MANAGE_READ("日志管理-日志管理-日志列表"),
    LOG_CZY_READ("日志管理-存证云-存证查看"),


    SYSTEM_ROLE_READ("系统管理-角色管理-角色查看"),
    SYSTEM_ROLE_CREATE("系统管理-角色管理-角色创建"),
    SYSTEM_ROLE_DELETE("系统管理-角色管理-角色删除"),
    SYSTEM_ROLE_UPDATE("系统管理-角色管理-角色更新"),

    SYSTEM_USER_READ("系统管理-用户管理-用户查看"),
    SYSTEM_USER_CREATE("系统管理-用户管理-用户创建"),
    SYSTEM_USER_DELETE("系统管理-用户管理-用户删除"),
    SYSTEM_USER_UPDATE("系统管理-用户管理-用户更新");


    private String description;

    Authority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static List<Authority> getAllAuthority(){
        return new ArrayList<>(Arrays.asList(Authority.values()));
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
