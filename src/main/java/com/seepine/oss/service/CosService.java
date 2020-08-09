package com.seepine.oss.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.seepine.oss.OssProperties;
import com.seepine.oss.constant.CommonConstant;
import com.seepine.oss.entity.BucketObject;
import com.seepine.oss.entity.OssObject;
import com.seepine.oss.enums.AccessControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.util.List;

/**
 * @author Seepine
 */
@Slf4j
public class CosService {
    private COSClient cosClient;
    private List<Bucket> buckets;
    private OssProperties ossProperties;

    /**
     * CosService
     *
     * @param ossProperties ossProperties
     */
    public CosService(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
        COSCredentials cred;
        if (ossProperties.getToken() != null) {
            cred = new BasicSessionCredentials(ossProperties.getSecretId(), ossProperties.getSecretKey(), ossProperties.getSecretKey());
        } else {
            cred = new BasicCOSCredentials(ossProperties.getSecretId(), ossProperties.getSecretKey());
        }
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        ClientConfig clientConfig = new ClientConfig(new Region(ossProperties.getRegion()));
        this.cosClient = new COSClient(cred, clientConfig);

        buckets = cosClient.listBuckets();
    }

    /**
     * createBucket
     * 默认创建私有桶
     *
     * @param bucketName 桶名
     * @return boolean
     */
    public BucketObject createBucket(String bucketName) {
        return createBucket(bucketName, AccessControl.Default);
    }

    /**
     * createBucket
     *
     * @param bucketName    桶名
     * @param accessControl 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
     * @return boolean
     */
    public BucketObject createBucket(String bucketName, AccessControl accessControl) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        switch (accessControl) {
            case Private:
                createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
                break;
            case PublicRead:
                createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
                break;
            case PublicReadWrite:
                createBucketRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
                break;
            default:
                createBucketRequest.setCannedAcl(CannedAccessControlList.Default);
        }
        try {
            BucketObject bucket = new BucketObject();
            for (Bucket bk : buckets) {
                if (bk.getName().equals(bucketName)) {
                    BeanUtils.copyProperties(bk, bucket);
                    return bucket;
                }
            }
            BeanUtils.copyProperties(cosClient.createBucket(createBucketRequest), bucket);
            return bucket;
        } catch (CosClientException cosClientException) {
            cosClientException.printStackTrace();
            log.error(cosClientException.getMessage());
        }
        return null;
    }

    public String upload(String bucket, String fileName, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return putObjectResult == null ? null : String.format(CommonConstant.COS_URL_FORMAT, bucket, ossProperties.getRegion(), fileName);
    }

    public OssObject download(String bucketName, String fileName) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
        return new OssObject(cosObjectInputStream, cosObjectInputStream.getHttpRequest());
    }
}
