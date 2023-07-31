package com.lirui.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.lirui.oss.service.OssService;
import com.lirui.oss.utlis.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author ：xxx
 * @description：整合OSS上传头像
 * @date ：2023/5/16 14:19
 */
@Service
public class OssServiceImpl implements OssService {

    //上传头像功能
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POIND;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            //创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //获取文件的名称
            String fileName = file.getOriginalFilename();
            //获取随机数
            String s = UUID.randomUUID().toString();
            //获取当前时间，并进行将时间作为文件名提交到OSS中
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //调用oss方法实现上传
            fileName = datePath + "/" + s +fileName;
            ossClient.putObject(bucketName,fileName,inputStream);
            //关闭OSSClient
            ossClient.shutdown();
            //https://edu-717718.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20230510163116.png
            String url = "https://"+bucketName+'.'+endpoint+"/"+fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
