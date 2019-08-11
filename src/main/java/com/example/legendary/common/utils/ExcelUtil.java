package com.example.legendary.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 导出Excel
 * @Author: 吴嘉晟
 * @Date: 2019/6/10 16:26
 * @Version 1.0
 */
public class ExcelUtil {

    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return 表格
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * 调起客户端
     * @param response response
     * @param fileName 文件名称
     */
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                //写入文件 设置字符集为utf-8
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //响应浏览器
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 响应客户端
     * @param response response
     * @param fileName 文件名称
     * @param wb 工作蒲对象
     */
    public void execl(HttpServletResponse response, String fileName,HSSFWorkbook wb){
        //响应到客户端
        try {
            //得到文件
            this.setResponseHeader(response, fileName);
            assert response != null;
            //使用字节流输出
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main(String[] args) throws Exception {
//        //使用方式
//        //1.创建response
//        HttpServletResponse response= ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
//        //2.获取对象数据集合
//        List<UserUser> list = new ArrayList<>();
//        list.add(new UserUser());
//        //excel标题
//        String[] title = {"名称","电话","性别"};
//
//        //excel文件名
//        String fileName = "用户信息表"+System.currentTimeMillis()+".xls";
//
//        //sheet名
//        String sheetName = "用户信息表";
//
//        //设置二维数组数据
//        String[][] content = new String[list.size()][];
//        for (int i = 0; i < list.size(); i++) {
//            //设置每一个标题对应的数据
//            content[i] = new String[title.length];
//            UserUser obj = list.get(i);
//            content[i][0] = obj.getUsername();
//            content[i][1] = obj.getPassword();
//        }
//        //创建HSSFWorkbook
//        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
//
//        this.execl(response,fileName,wb);
    }

}
