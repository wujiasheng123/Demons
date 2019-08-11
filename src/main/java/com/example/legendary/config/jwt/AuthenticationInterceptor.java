package com.example.legendary.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.legendary.common.utils.AESUtil;
import com.example.legendary.trunk.users.model.SysUser;
import com.example.legendary.trunk.users.service.UsersService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 吴嘉晟
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UsersService usersService;

    @Override
    public synchronized boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {

        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有LoginToken注释，有则跳过认证
        if (method.isAnnotationPresent(LoginToken.class)) {
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()) {
                return true;
            }
        }
        //检查是否有RegisterToken，有则跳过认证
        if (method.isAnnotationPresent(RegisterToken.class)){
            RegisterToken registerToken = method.getAnnotation(RegisterToken.class);
            if (registerToken.required()){
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                long userId;
                try {
                    // 解密token
                    token = AESUtil.decode(token);
                    if(token == null) {
                        throw new RuntimeException("Token异常,非法访问！");
                    }
                    userId = JWT.decode(token).getClaim("id").asLong();
                    if (userId == 0) {
                        throw new RuntimeException("用户不存在，请重新登录");
                    }
                    SysUser user = usersService.selectUserInfoById(userId);
                    if (user == null) {
                        throw new RuntimeException("用户不存在，请重新登录");
                    }
                    Boolean verify = JwtUtil.isVerify(token, user);
                    if (!verify) {
                        throw new RuntimeException("非法访问！");
                    }
                    // 用户权限
                    List<String> userPermissions = JWT.decode(Objects.requireNonNull(token)).getClaim("permissions").asList(String.class);
                    //方法所属权限
                    String[] permissions = checkToken.value().split(",");
                    // 权限匹配
                    boolean perFlag = true;
                    // 方法是否开放
                    if(StringUtils.equals("",permissions[0])) {
                        return true;
                    }
                    // 用户权限为null，分配内存空间
                    if(userPermissions == null) {
                        userPermissions = new ArrayList<>();
                    }
                    for (String userPermission :
                            userPermissions) {
                        for (String permission:
                                permissions) {
                            if (StringUtils.equals(userPermission,permission)) {
                                perFlag = false;
                            }
                        }
                    }
                    if(perFlag) {
                        throw new RuntimeException("没有权限！");
                    }
                    return true;
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("访问异常！");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

}
