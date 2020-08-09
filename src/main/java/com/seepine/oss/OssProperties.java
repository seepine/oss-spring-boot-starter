package com.seepine.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Seepine
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssProperties {
    /**
     * 密钥id
     */
    private String secretId;
    /**
     * 密钥key
     */
    private String secretKey;
    /**
     * 地域
     * 腾讯云：https://cloud.tencent.com/document/product/436/6224
     */
    private String region;
    /**
     * 临时登录token
     */
    private String token;

    private String type;
}
