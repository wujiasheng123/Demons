package com.example.legendary.common.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

/**
 * ftp上传工具类
 * @Author: 吴嘉晟
 * @Date: 2018/3/1 18:39
 * @Version 1.0
 */
public class FTPUploadUtils {

    /**
     * ftp连接
     */
    private FTPClient ftpClient = new FTPClient();

    /**
     * ftp文件上传路径
     */
    private String path;

    /**
     * 输入流
     */
    private InputStream is = null;

    /**
     * 输出流
     */
    private OutputStream os = null;

    /**
     * 初始化ftp
     * @param ftpUrl 路径
     * @param port port大小
     * @param username 名称
     * @param password 密码
     * @param path 路径
     */
    public FTPUploadUtils(String ftpUrl, int port, String username, String password, String path) throws IOException {
        // 连接ftp服务器
        ftpClient.connect(ftpUrl, port);
        // 登陆ftp服务器
        ftpClient.login(username, password);
        // 检查路径是否存在
        boolean pathIsNull = ftpClient.changeWorkingDirectory(path);
        if(!pathIsNull) {
            throw new IOException("上传路径不存在");
        }
        // 选择ftp上传路径
        ftpClient.changeWorkingDirectory(path);
        // 上传文件类型：二进制
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        // 兼容中文
        ftpClient.setControlEncoding("UTF-8");
        // 设置文件上传路径
        this.path = path;
    }

    /**
     * 文件上传
     * @param file 需要上传的文件
     * @return 成功与否
     */
    public boolean uploadFile(File file) {
        try {
            // 初始化文件输入流
            is = new FileInputStream(file);
            // 上传文件
            return ftpClient.storeFile(file.getName(),is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 下载文件
     * @param file 需要下载的文件
     * @return 成功与否
     */
    public boolean downFile(File file) {
        try {
            os = new FileOutputStream(file);
            ftpClient.retrieveFile(file.getName(),os);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 关闭ftp
     */
    private void ftpClose() {
        try {
            // 退出登陆
            ftpClient.logout();
            // 关闭ftp连接
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
