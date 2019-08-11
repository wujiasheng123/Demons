package com.example.legendary.common.utils;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 消息订阅工具类
 * @Author: 吴嘉晟
 * @Date: 2018/1/1 18:39
 * @Version 1.0
 */
public class ReqContextUtils {

    /**
     * 超时时间
     */
    private static int DEFAULT_TIME_OUT = 60*60*1000;

    /**
     *     订阅列表，存储所有主题的订阅请求，每个topic对应一个ArrayList，ArrayList里该topic的所有订阅请求
     */
    private static HashMap<String, ArrayList<AsyncContext>> subscribeArray = new LinkedHashMap<>();

    /**
     * 添加订阅消息
     * @param topic 搜索字段
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public static void addSubscrib(String topic, HttpServletRequest request, HttpServletResponse response) {
        if (null == topic || "".equals(topic)) {
            return;
        }
        //设置响应头ContentType
        response.setContentType("text/event-stream");
        //设置响应编码类型
        response.setCharacterEncoding("UTF-8");
        //request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        //支持异步响应
        //异步这个概念很多地方都有，就像处理文件时，不是一直等待文件读完，而是让它去读，cpu做其它事情，读完通知cpu来处理即可。
        AsyncContext actx = request.startAsync(request, response);
        actx.setTimeout(DEFAULT_TIME_OUT);
        //添加一些监听函数
        actx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("推送结束");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("推送超时");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("推送错误");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("推送开始");
            }
        });
        //将异步请求存入列表
        ArrayList<AsyncContext> actxList = subscribeArray.computeIfAbsent(topic, k -> new ArrayList<AsyncContext>());
        actxList.add(actx);
    }

    //

    /**
     * 获取订阅列表
     * @param topic 搜索字段
     * @return 列表
     */
    public static ArrayList<AsyncContext> getSubscribList(String topic) {
        return subscribeArray.get(topic);
    }

    /**
     * 推送消息
     * @param topic 搜索字段
     * @param content 内容
     */
    public static void publishMessage(String topic, String content) {
        //获取对应topic的订阅列表
        ArrayList<AsyncContext> actxList = subscribeArray.get(topic);
        if (null != actxList) {
            for(AsyncContext actx :actxList) {
                try {
                    PrintWriter out = actx.getResponse().getWriter();
                    out.print(content);
                    actx.getResponse().flushBuffer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

