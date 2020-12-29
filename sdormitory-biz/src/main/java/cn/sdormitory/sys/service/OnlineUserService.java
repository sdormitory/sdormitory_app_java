package cn.sdormitory.sys.service;

import cn.sdormitory.sys.entity.SysUser;
import cn.sdormitory.sys.vo.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: OnlineUserService
 */
public interface OnlineUserService {
    /**
     * 保存在线信息
     *
     * @param sysUser
     * @param token
     * @param expiration
     * @param request
     */
    void save(SysUser sysUser, String token, String expiration, HttpServletRequest request);

    /**
     * 踢掉之前已经登录的token
     *
     * @param username
     * @param token
     */
    void checkLoginOnUser(String username, String token);

    /**
     * 踢出用户
     *
     * @param key
     * @throws Exception
     */
    void kickOut(String key) throws Exception;

    /**
     * 退出登录
     *
     * @param token
     */
    void logout(String token);

    /**
     * 根据关键字获取所有 在线用户
     *
     * @param filter
     * @return
     */
    List<OnlineUser> getAll(String filter);


//    void save(JwtUser jwtUser, String token, HttpServletRequest request);
}
