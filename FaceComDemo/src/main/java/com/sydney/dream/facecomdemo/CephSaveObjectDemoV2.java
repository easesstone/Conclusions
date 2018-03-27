package com.sydney.dream.facecomdemo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CephSaveObjectDemoV2 {

    // 先进性多进程下的单线程测试，然后进行多线程测试
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        if (args.length != 5) {
            System.out.print("输入参数有误， args[0]: path, 图片路径。 \n" +
                    "args[1]: cephGWBalanceNode ceph 对象网关负载均衡节点，haproxy 节点。 \n" +
                    "args[2]: accessKey 用于用户认证。 \n" +
                    "args[3]: secretKey 用于用户认证。 \n" +
                    "args[4]: bucketName bucketName。 \n");
            System.exit(0);
        }
        String path = args[0];
        String cephGWBalanceNode = args[1];
        String accessKey = args[2];
        String secretKey = args[3];
        String bucketName = args[4];

        AmazonS3 conn = AmazonS3Util.getAmazonS3Connection(accessKey, secretKey, cephGWBalanceNode);
        Bucket bucket = new Bucket(bucketName);
        if (!AmazonS3Util.checkBucketExists(conn, bucketName)) {
            AmazonS3Util.createBucket(conn, bucketName);
        }
        File []files = FileUtil.getFileList(path);
        if (files == null) {
            System.out.println("所给目录没有文件....");
            System.exit(0);
        }
        for (int i = 0;i < files.length; i++) {
            String key = UUID.randomUUID().toString() + "-" + files[i].getName();
            System.out.println("多进程，单线程情况下：第 " + i + " 个对象存储---> keys : " +  key);
            byte []data = Image2Byte2Image.image2byte(files[i].getAbsolutePath());
            if (key.contains(".jpg")) {
                AmazonS3Util.putObjectToCeph(conn, bucket, key, data);
            } else {
                AmazonS3Util.putObjectToCeph(conn, bucket, key, data, null);
            }
        }
        System.out.println(files.length + "个数据花费时间为： " + (System.currentTimeMillis() - start));
    }
}
