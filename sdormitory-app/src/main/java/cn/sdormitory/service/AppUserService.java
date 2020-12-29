package cn.sdormitory.service;

import cn.sdormitory.sys.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/25 10:51
 * @version：V1.0
 */
public interface AppUserService {
    String doLogin(String loginName, String loginPassword, HttpServletRequest request);

    SysUser getUserById(int id);

    boolean updatePassword(int id , String oldPassword , String newPassword);
}
