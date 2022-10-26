package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.ggkt.vod.service.FileService;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/25 16:17
 */
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file){
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String endPoint = ConstantPropertiesUtil.END_POINT;
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);

        // 2 设置 bucket 的地域
        // clientConfig 中包含了设置 region, https(默认 http),超时, 代理等 set 方法
        Region region = new Region(ConstantPropertiesUtil.END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        InputStream stream = null;
        try {
            stream = file.getInputStream();
            String key = UUID.randomUUID().toString().replaceAll("-","")+
                    file.getOriginalFilename();
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            key = dateUrl+"/"+key;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, key, stream,objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(JSON.toJSONString(putObjectResult));

            //拼接得到图片的url地址
            //https://ggkt-atguigu-1310644373.cos.ap-beijing.myqcloud.com/01.jpg
            String url = "https://"+bucketName+"."+"cos"+"."+endPoint+".myqcloud.com"+"/"+key;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
