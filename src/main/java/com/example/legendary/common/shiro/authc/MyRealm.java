package com.example.legendary.common.shiro.authc;

import com.example.legendary.common.utils.AESUtil;
import com.example.legendary.common.utils.RedisUtil;
import com.example.legendary.config.jwt.JwtToken;
import com.example.legendary.config.jwt.JwtUtil;
import com.example.legendary.config.jwt.LoginToken;
import com.example.legendary.config.jwt.RegisterToken;
import com.example.legendary.trunk.users.mapper.UsersMapper;
import com.example.legendary.trunk.users.model.SysUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 吴嘉晟
 * @Date 2019/07/10 17:20:20
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证 [ roles、permissions]————");

        SysUser userUser = (SysUser) principalCollection.getPrimaryPrincipal();

        String userName = userUser.getUserName();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

//        //设置角色
//        List<String> roles = null;
//        //从redis缓存中查询权限角色
//        String roleStr = stringRedisTemplate.opsForValue().get("PREFIX_USER_ROLE" + userName);
//        if (roleStr != null){
//            roles = JSON.parseArray(roleStr.toString(),String.class);
//        }else {
//            //从数据库查询权限放到redis中
////            roles = sysUserService.getRole(userName);
//            stringRedisTemplate.opsForValue().set("PREFIX_USER_ROLE" + userName, JSON.toJSONString(roles));
//        }
//
//        //设置超时时间
//        stringRedisTemplate.expire("PREFIX_USER_ROLE" + userName,3600,TimeUnit.SECONDS);
        return info;
    }

    /**
     * 获取身份验证信息 Shiro中，默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *
     * @param auth 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String)auth.getCredentials();
        if (token == null) {
            throw new AuthenticationException("token为空!");
        }
        // 解密获得username，用于和数据库进行对比
        Long userId = JwtUtil.getId(token);
        if (userId == null) {
            throw new AuthenticationException("token非法无效!");
        }
        //查询用户信息
        SysUser userUser = usersMapper.selectUserInfoById(userId);
        if (userUser == null){
            throw new AuthenticationException("用户不存在");
        }
        if (!jwtTokenRefresh(token,userUser)){
            throw new AuthenticationException("非法访问");
        }
        return new SimpleAuthenticationInfo(userUser,token,getName());
    }


    /**
     * 校验token
     * @param token token
     * @param userUser 生成token对象
     * @return 成功与否
     */
    private boolean jwtTokenRefresh(String token,SysUser userUser) {
        token = AESUtil.decode(token);
        String cacheToken = AESUtil.decode((String) redisUtil.get("PREFIX_USER_TOKEN" + userUser.getUserId()));
        if ("".equals(cacheToken)){
            return false;
        }
        assert token != null;
        if (token.equals(cacheToken)) {
            //校验token有效性
            return JwtUtil.isVerify(cacheToken, userUser);
        }
        return false;
    }
}
