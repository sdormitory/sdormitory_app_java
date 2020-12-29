package cn.sdormitory.service.impl;

import cn.sdormitory.basedata.dao.BStudentDao;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.service.AppStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 11:08
 * @version：V1.0
 */
@Service
public class AppStudentServiceImpl extends ServiceImpl<BStudentDao, BStudent> implements AppStudentService {
    @Resource
    private BStudentDao bStudentDao;

    @Override
    public BStudent getStudentById(int id) {
        return bStudentDao.selectById(id);
    }

    @Override
    public boolean updateStuPassword(int id, String oldPassword, String newPassword) {
        String loginPassword = bStudentDao.getStuPassword(id);
        if(null != loginPassword && !"".equals(loginPassword)){
            System.out.println(loginPassword + "student---"+oldPassword);
            if(oldPassword.equals(loginPassword)){
                if(bStudentDao.updateStuPassword(id, newPassword)>0){
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

    @Override
    public boolean updateParPassword(int id, String oldPassword, String newPassword) {
        String loginPassword = bStudentDao.getParPassword(id);
        if(null != loginPassword && !"".equals(loginPassword)){
            System.out.println(loginPassword + "parent---"+oldPassword);
            if(oldPassword.equals(loginPassword)){
                if(bStudentDao.updateParPassword(id, newPassword)>0){
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
