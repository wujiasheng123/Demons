package com.example.legendary.common.utils;

import com.example.legendary.config.jwt.JwtUtil;
import io.undertow.servlet.attribute.ServletRequestAttribute;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * HttpServletResponse工具类
 * @Author: 吴嘉晟
 * @Date: 2019/8/5 17:57
 * @Version 1.0
 */
@Component
public class HttpServletUtils {

    @Resource
    RedisUtil redisUtil;

    /**
     * 将token缓存在Redis中并放在请求头中
     * @param httpServletResponse HttpServletResponse
     * @param token token
     */
    public void setHeader(HttpServletResponse httpServletResponse,String token){
        long userId = JwtUtil.getId(token);
        //将token放入Redis中并设置超时时间
        redisUtil.set("PREFIX_USER_TOKEN" + userId,token,JwtUtil.EXPIRE_TIME);
        httpServletResponse.setHeader("token",token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "token");
    }

    /**
     * 从请求头中读取token
     * @param httpServletRequest HttpServletRequest
     * @return token
     */
    public String getHeader(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader("token");
    }

    /**
     * 由于需要做日志收集，打印携带参数时候HttpServletResponse打印失败会抛出异常所有使用此方法
     * 创建返回HttpServletResponse对象
     * @return HttpServletResponse对象
     */
    public HttpServletResponse getHttpServletResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 由于需要做日志收集，打印携带参数时候HttpServletRequest打印失败会抛出异常所有使用此方法
     * 创建返回HttpServletRequest对象
     * @return HttpServletRequest对象
     */
    public HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

}
