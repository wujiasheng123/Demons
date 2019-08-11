package com.example.legendary.common.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件工具类---上传本地
 * @Author: 吴嘉晟
 * @Date: 2018/11/5 19:53
 * @Version 1.0
 */
public class UploadUtils extends HttpServlet {

    /**
     * 文件上传
     * @param file 文件流
     * @param path 路径
     * @return 文件名称
     */
    public static String upload(MultipartFile file, String path) {
        if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "false";
        }
        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return fileName;
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }
    }

}
