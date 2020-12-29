package cn.sdormitory.service.impl;


import cn.sdormitory.basedata.dao.BStudentDao;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.service.AppUserService;
import cn.sdormitory.service.ParentTokenService;
import cn.sdormitory.service.StudentTokenService;
import cn.sdormitory.service.UserTokenService;
import cn.sdormitory.sys.dao.SysUserDao;
import cn.sdormitory.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/25 11:15
 * @version：V1.0
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements AppUserService {
    @Resource
    private SysUserDao sysUserDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserTokenService userTokenService;
    @Resource
    private BStudentDao bStudentDao;
    @Resource
    private StudentTokenService studentTokenService;
    @Resource
    private ParentTokenService parentTokenService;

    @Override
    public String doLogin(String loginName, String loginPassword, HttpServletRequest request) {
        SysUser sysUser = sysUserDao.doLogin(loginName);
        BStudent bStudent=bStudentDao.doLoginStudent(loginName);
        BStudent bStudentParent=bStudentDao.doLoginParent(loginName);
        if(null != sysUser){
            if( !"".equals(sysUser.getPassword())  && null != sysUser.getPassword()){
                System.out.println("[用户]数据库中的密码："+sysUser.getPassword()+"----输入的密码："+loginPassword+"-----加密后的密码"+passwordEncoder.matches(loginPassword,sysUser.getPassword()));
                if(passwordEncoder.matches(loginPassword,sysUser.getPassword())){
                    String token = userTokenService.generateToken(request.getHeader("user-agent"),sysUser);
                    userTokenService.save(token,sysUser);
                    return token;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else if(null != bStudent){
            if( !"".equals(bStudent.getStudentPassword())  && null != bStudent.getStudentPassword()){
                System.out.println("[学员]数据库中的密码："+bStudent.getStudentPassword()+"----输入的密码："+loginPassword);
                if(loginPassword.equals(bStudent.getStudentPassword())){
                    String token = studentTokenService.generateToken(request.getHeader("user-agent"),bStudent);
                    studentTokenService.save(token,bStudent);
                    return token;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else if(null != bStudentParent){
            if( !"".equals(bStudentParent.getParentPassword())  && null != bStudentParent.getParentPassword()){
                System.out.println("[家长]数据库中的密码："+bStudentParent.getParentPassword()+"----输入的密码："+loginPassword);
                if(loginPassword.equals(bStudentParent.getParentPassword())){
                    String token = parentTokenService.generateToken(request.getHeader("user-agent"),bStudentParent);
                    parentTokenService.save(token,bStudentParent);
                    return token;
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    @Override
    public SysUser getUserById(int id) {
        return sysUserDao.selectById(id);
    }

    @Override
    public boolean updatePassword(int id, String oldPassword, String newPassword) {
        String loginPassword = sysUserDao.getLoginPassword(id);
        if(null != loginPassword && !"".equals(loginPassword)){
            System.out.println(loginPassword+oldPassword + "---"+passwordEncoder.matches(oldPassword,loginPassword));
            if(passwordEncoder.matches(oldPassword,loginPassword)){
                if(sysUserDao.updatePassword(id, passwordEncoder.encode(newPassword))>0){
                    return true;
                }
                return false;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
