package com.seepine.oss.service;

import cn.hutool.core.io.IoUtil;
import com.seepine.oss.OssProperties;
import com.seepine.oss.constant.CommonConstant;
import com.seepine.oss.constant.TenCentRegion;
import com.seepine.oss.entity.BucketObject;
import com.seepine.oss.entity.OssObject;
import com.seepine.oss.enums.AccessControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;


/**
 * @author Seepine
 */
@Slf4j
@RequiredArgsConstructor
public class OssTemplate implements InitializingBean {

    private final OssProperties ossProperties;
    private CosService cosService;

    @Override
    public void afterPropertiesSet() {
        if (ossProperties.getRegion() == null) {
            ossProperties.setRegion(TenCentRegion.DEFAULT_REGION);
        }
        if (ossProperties.getType() == null) {
            ossProperties.setType("cos");
        }
        cosService = new CosService(ossProperties);
        log.debug("afterPropertiesSet OssTemplate");
    }

    public BucketObject createBucket(String bucketName) throws Exception {
        if (CommonConstant.COS.equals(ossProperties.getType())) {
            return cosService.createBucket(bucketName);
        }
        return null;
    }

    public BucketObject createBucket(String bucketName, AccessControl accessControl) {
        if (CommonConstant.COS.equals(ossProperties.getType())) {
            return cosService.createBucket(bucketName, accessControl);
        }
        return null;
    }

    public String upload(String bucket, String fileName, File file) {
        if (CommonConstant.COS.equals(ossProperties.getType())) {
            return cosService.upload(bucket, fileName, file);
        }
        return null;
    }

    public String upload(String bucket, String fileName, InputStream inputStream) throws IOException {
        if (CommonConstant.COS.equals(ossProperties.getType())) {
            File tempFile = File.createTempFile("template_logo_copy", fileName);
            OutputStream os = new FileOutputStream(tempFile);
            IoUtil.copy(inputStream, os);
            return cosService.upload(bucket, fileName, tempFile);
        }
        return null;
    }

    public OssObject download(String bucketName, String fileName) {
        if (CommonConstant.COS.equals(ossProperties.getType())) {
            return cosService.download(bucketName, fileName);
        }
        return null;
    }

}
