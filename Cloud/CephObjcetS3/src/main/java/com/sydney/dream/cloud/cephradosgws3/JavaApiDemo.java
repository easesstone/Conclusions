package com.sydney.dream.cloud.cephradosgws3;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;

public class JavaApiDemo {

    // 获取连接
    public static AmazonS3 getAmazonS3Connection(String accessKey, String secretKey, String endPoint) {
        // 认证服务accessKey: "YQ3K0RET31WN5T46W2D5",secretKey: "LDsXa0QdIE8meE52wN374Ai7Qsa6D6i51SNTOVOD"
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        // 客户端配置
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);
        // 连接对象
        AmazonS3 conn = new AmazonS3Client(credentials, clientConfiguration);
        conn.setEndpoint(endPoint);
         return  conn;
    }

    // 获取 Bucket 列表， 并且打印
    public static List<Bucket> getBuckets(AmazonS3 conn) {
        // 列出所有Buckets
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName() + "\t" +
                    StringUtils.fromDate(bucket.getCreationDate()));
        }
        return  buckets;
    }

    // 创建Bucket
    public static Bucket createBucket(AmazonS3 conn, String bucketNmae) {
        return  conn.createBucket(bucketNmae);
    }

    // 创建对象,
    public static PutObjectResult putObjectToCeph(AmazonS3 conn, Bucket bucket, String fileName, byte []data, String contentType) {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        return  conn.putObject(bucket.getName(), fileName, input, metadata);
    }

    // FTP 图片以及其他的内容，文件名字唯一
    public static PutObjectResult putObjectToCeph(AmazonS3 conn, Bucket bucket, String fileName, byte []data) {
        return  putObjectToCeph(conn, bucket, fileName, data, "image/jpeg");
    }

    //修改文件权限
    public static void chmodOfFile(AmazonS3 conn, Bucket bucket, String fileName,
                                   CannedAccessControlList cannedAccessControlList){
        conn.setObjectAcl(bucket.getName(), fileName, cannedAccessControlList);
    }

    // 列出Bucket 中的所有文件
    public static ObjectListing getAllObjectDeatails(AmazonS3 conn, Bucket bucket){
        ObjectListing objects = conn.listObjects(bucket.getName());
        long count = 0;
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println(objectSummary.getKey() + "\t" +
                        objectSummary.getSize() + "\t" +
                        StringUtils.fromDate(objectSummary.getLastModified()));
                count ++;
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        System.out.println("object 总数： " + count);
        return  objects;
    }

    public static long getAllObjectNumOfBucket(AmazonS3 conn, Bucket bucket){
        ObjectListing objects = conn.listObjects(bucket.getName());
        long count = 0;
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                count ++;
            }
            objects = conn.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());
        System.out.println("文件总的数量： " +  count);
        return  count;
    }

    //删除bucket,bucket 必须为空
    public void deleteBucket(AmazonS3 conn, Bucket bucket){
        conn.deleteBucket(bucket.getName());
    }

    //获取对象
    public static void getObject(AmazonS3 conn, Bucket bucket,
                                 String sourceFile, String desFileName) {
        conn.getObject(new GetObjectRequest(bucket.getName(), sourceFile), new File(desFileName));
    }

    //删除一个对象
    public static void deleteObject(AmazonS3 conn, Bucket bucket, String fileName) {
        conn.deleteObject(bucket.getName(), fileName);
    }

    // 生成带签名的url
    public static URL getURLOfObjectWithSighnature(AmazonS3 conn, Bucket bucket, String fileName) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), fileName);
        return conn.generatePresignedUrl(request);
    }

    // 生成带签名的url，记得设置超时时间
    public static String uploadToS3(AmazonS3 conn, Bucket bucket, File tempFile, String remoteFileName) throws IOException {
        String key = UUID.randomUUID() + ".apk";// ftp 并不用特意生成一个key ，因为ftp 里面的文件，文件名总是唯一的。
        try {
            //验证名称为bucketName的bucket是否存在，不存在则创建
            if (!checkBucketExists(conn, bucket.getName())) {
                conn.createBucket(bucket.getName());
            }
            //上传文件
            conn.putObject(new PutObjectRequest(bucket.getName(), key, tempFile));
            //获取一个request
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                    bucket.getName(), key);
            Date expirationDate = null;
            try {
                expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-31");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置过期时间
            urlRequest.setExpiration(expirationDate);    //******************************
            //生成公用的url
            URL url = conn.generatePresignedUrl(urlRequest);
            System.out.println("=========URL=================" + url + "============URL=============");
            return url.toString();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            return  null;
        } catch (AmazonClientException ace) {
            return null;
        }
    }

    public static boolean checkBucketExists (AmazonS3 conn, String bucketName) {
        List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (bucketName != null && bucketName.equals(bucket.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
       //获取连接
        AmazonS3 conn = getAmazonS3Connection("5Z2E27YG6Y8Y98LD1C9U",
                "NZiNOea7igxAOHu3Sd81tQNl8H5SGAmmtNQPmJcX", "172.18.18.139:8888");

         //列出所有的Bucket
//        getBuckets(conn);

        //在Ceph创建bucket
//        Bucket bucket = createBucket(conn, "my-new-bucket01");
        Bucket bucket = new Bucket("mydemo02");  // bucket 对象

        //创建对象，对象内容为hello,world!!,
//        putObjectToCeph(conn, bucket, "Tulips5.jps", Image2Byte2Image.image2byte("D:\\Tulips.jpg"));

         //修改文件权限：
//        chmodOfFile(conn, bucket, "hello.txt", CannedAccessControlList.PublicRead);

         //列出Bucket 中的所有文件
//        getAllObjectDeatails(conn, bucket);

        //列出文件总数
//        getAllObjectNumOfBucket(conn, bucket);

         //删除bucket
//         deleteBucket(conn, bucket);

        //获取object
        getObject(conn, bucket, "ebde2f62-95ba-46e4-a54e-2c8eb10000f9-2017_12_24_17_08_19_294_1.jpg", "D:\\hello1.jpg");

        //删除object
//        deleteObject(conn, bucket, "hello1.txt");

        //生成带签名的url
//        System.out.println(getURLOfObjectWithSighnature(conn, bucket, "Tulips.jps"));
//        System.out.println("/usr/niya/hello.txt".substring(0, "/usr/niya/hello.txt".lastIndexOf("/")));
    }
}
