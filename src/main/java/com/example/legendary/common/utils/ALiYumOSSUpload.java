package com.example.legendary.common.utils;

import com.aliyun.oss.OSSClient;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传阿里云OSS存储
 * @Author: 吴嘉晟
 * @Date: 2019/3/1 18:39
 * @Version 1.0
 */
public class ALiYumOSSUpload {

    /**
     * 文件批量上传--阿里云
     * @param uploadFile 文件集合
     * @return 路径集合
     * @throws Exception io流异常
     */
    public static List<String> picOSS(List<MultipartFile> uploadFile) throws Exception {
        List<String> list = new ArrayList<>();
        // 阿里云终端节点地址
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
        // 阿里云accessKeyId，根据本身进行修改。
        String accessKeyId = "LTAIG0MUpzbDxCiV";
        // 阿里云accessKeySecret，根据本身进行修改。
        String accessKeySecret = "kAt0mzRoBah4q6ToENcrMSl87NHuEk";
        // bucket由自己在阿里云设置，根据本身进行修改。
        String bucketName = "wujiasheng";
        for (MultipartFile anUploadFile : uploadFile) {
            //文件是否存在
            if (anUploadFile.isEmpty()) {
                return null;
            }
            //获取文件名称
            String fileName = anUploadFile.getOriginalFilename();
            // 文件名不为空
            if (fileName != null) {
                // 修改文件名称
                fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            } else {
                return null;
            }
            // 创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传
            long time = System.currentTimeMillis();
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(anUploadFile.getBytes()));
            // 关闭client
            ossClient.shutdown();
            // 获取时间节点
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            // 获取图片路径
            String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
            list.add(url);
        }
        return list;
    }
}
