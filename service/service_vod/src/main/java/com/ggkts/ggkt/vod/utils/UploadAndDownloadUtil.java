package com.ggkts.ggkt.vod.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Api("文件上传下载的工具类")
public class UploadAndDownloadUtil {

    @ApiOperation("文件上传方法")
    public static String upload(MultipartFile file){
        // 1 初始化用户身份信息（secretId, secretKey）。
// SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


        // 指定要上传的文件
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();

//        File localFile = new File(localFilePath);
// 指定文件将要存放的存储桶
            String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String timefile = new DateTime().toString();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String key = timefile+'/'+uuid+file.getOriginalFilename();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,objectMetadata);
            cosClient.putObject(putObjectRequest);
            String url = "https://"+bucketName+"."+"cos"+"."+ConstantPropertiesUtil.END_POINT+".myqcloud.com"+"/"+key;

            return url;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
