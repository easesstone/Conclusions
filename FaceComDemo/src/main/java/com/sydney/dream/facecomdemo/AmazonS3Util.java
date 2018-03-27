package com.sydney.dream.facecomdemo;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

public class AmazonS3Util {

    // 获取连接
    public static AmazonS3 getAmazonS3Connection(String accessKey, String secretKey, String endPoint) {
        // 认证服务accessKey: "YQ3K0RET31WN5T46W2D5",secretKey: "LDsXa0QdIE8meE52wN374Ai7Qsa6D6i51SNTOVOD"
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        // 客户端配置
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);
//        AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials));
        // 连接对象
        AmazonS3 conn = new AmazonS3Client(credentials, clientConfiguration);
        conn.setEndpoint(endPoint);
        return  conn;
    }

    // 创建Bucket
    public static Bucket createBucket(AmazonS3 conn, String bucketNmae) {
        return  conn.createBucket(bucketNmae);
    }

    // 存储其他对象
    public static PutObjectResult putObjectToCeph(AmazonS3 conn, Bucket bucket, String fileName, byte []data, String contentType) {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ObjectMetadata metadata = new ObjectMetadata();
        if (contentType != null) {
            metadata.setContentType(contentType);
        }
        return  conn.putObject(bucket.getName(), fileName, input, metadata);
    }

    // 存储图片对象
    public static PutObjectResult putObjectToCeph(AmazonS3 conn, Bucket bucket, String fileName, byte []data) {
        return  putObjectToCeph(conn, bucket, fileName, data, "image/jpeg");
    }

    // 检查Bucket 是否存在
    public static boolean checkBucketExists (AmazonS3 conn, String bucketName) {
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (bucketName != null && bucketName.equals(bucket.getName())) {
                return true;
            }
        }
        return false;
    }

    // 获取对象
    public static void getObject(AmazonS3 conn, Bucket bucket,
                                 String sourceFile, String desFileName) {
        conn.getObject(new GetObjectRequest(bucket.getName(), sourceFile), new File(desFileName));
    }

}
