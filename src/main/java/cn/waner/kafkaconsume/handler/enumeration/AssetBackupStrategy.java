package cn.waner.kafkaconsume.handler.enumeration;

/**
 * @author TanChong
 * create date 2019\10\30 0030
 */
public enum  AssetBackupStrategy {

    /**
     * 无。
     */
    NULL,

    /**
     * 即时备份。
     */
    IMMEDIATE,

    /**
     * 按时备份。
     */
    HOUR,

    /**
     * 按天备份。
     */
    DAY,

    /**
     * 按月备份。
     */
    MONTH
}
