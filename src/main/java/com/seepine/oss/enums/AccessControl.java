package com.seepine.oss.enums;

/**
 * @author Seepine
 */
public enum AccessControl {
    /**
     * 私有读写权限
     */
    Private,
    /**
     * 公开读,私有写
     */
    PublicRead,
    /**
     * 公开读写
     */
    PublicReadWrite,
    /**
     * 默认
     */
    Default;
}
