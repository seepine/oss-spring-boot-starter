package com.seepine.oss.config;

import com.seepine.oss.OssProperties;
import com.seepine.oss.service.OssTemplate;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Seepine
 */
@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({OssProperties.class})
public class OssAutoConfiguration {
    private final OssProperties ossProperties;

    @Bean
    @ConditionalOnMissingBean(OssProperties.class)
    public OssTemplate ossTemplate() {
        return new OssTemplate(ossProperties);
    }
}
