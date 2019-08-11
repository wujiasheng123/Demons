package com.example.legendary.config.loggin;

import com.alibaba.fastjson.JSONArray;
import com.example.legendary.config.jwt.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 扫描控制层打印冯文数据
 * @author 吴嘉晟
 * @Date 2018/1/31
 */
@Aspect
@Configuration
public class LoginAOP {
    /**
     * controller包或子包下任意方法
     */
    private static final String EXECUTION_CONTROLLER = "execution(* com.example.legendary.trunk.*.controller..*(..))";

    private Logger logger = LogManager.getLogger(LoginAOP.class);

    @Pointcut(EXECUTION_CONTROLLER)
    private void controller() {
    }


    @Before(value = "controller()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        logger.info("ip：" + request.getRemoteAddr());
        if(token != null) {
            logger.info("用户：" + JwtUtil.getUsername(token));
        }
        logger.info("正在访问：" + joinPoint.getSignature().getName() + "方法");
        Object args = joinPoint.getArgs();
        if (args != null) {
            logger.info("携带参数：" + JSONArray.toJSONString(args));
        }
    }
}
