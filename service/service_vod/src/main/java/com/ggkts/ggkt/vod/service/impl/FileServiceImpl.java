package com.ggkts.ggkt.vod.service.impl;

import com.ggkts.ggkt.vod.service.FileService;
import com.ggkts.ggkt.vod.utils.ConstantPropertiesUtil;
import com.ggkts.ggkt.vod.utils.UploadAndDownloadUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    //文件上传
    @Override
    public String upload(MultipartFile file) {
        return UploadAndDownloadUtil.upload(file);
    }
}
