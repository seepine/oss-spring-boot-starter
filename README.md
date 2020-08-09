## oss-spring-boot-starter

集成对象存储，项目使用腾讯云因此目前只加了cos，
后续有时间会添加阿里云、七牛云等对象存储，
如有需要可提issue提前适配

- cos(腾讯云)

...

## spring boot starter依赖
```xml
<dependency>
    <groupId>com.seepine</groupId>
    <artifactId>oss-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 使用方法

### 1.配置文件

```
oss:
  # 必填
  secret-id: ${your secretId}
  # 必填
  secret-key: ${your secretKey}
  # 选填
  token： ${your token}
  # 选填，默认ap-chengdu即成都
  region: ${yoor region}
  # 选填，默认cos
  type: ${your type}
```

### 2.代码使用
注入template
```java
@Autowire
private OssTemplate ossTemplate;
```
方法使用
```java
BucketObject bucketObject = ossTemplate.createBucket(bucketName,accessControl);

String fileUrl = ossTemplate.upload(bucket,fileName,inputStream);

OssObject ossObject = ossTemplate.download(bucketName,fileName);
...
```
