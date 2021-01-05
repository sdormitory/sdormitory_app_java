package cn.sdormitory.service.impl;

import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.entity.BDormitory;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.utils.SmsSendTemplate;
import cn.sdormitory.service.*;
import cn.sdormitory.smartdor.dao.SdLeaveDao;
import cn.sdormitory.smartdor.entity.SdAttence;
import cn.sdormitory.smartdor.entity.SdLeave;
import cn.sdormitory.sys.entity.SysUser;
import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 17:14
 * @version：V1.0
 */
@Service
public class AppLeaveServiceImpl extends ServiceImpl<SdLeaveDao, SdLeave> implements AppLeaveService {
    @Resource
    private SdLeaveDao sdLeaveDao;

    @Autowired
    private AppStudentService appStudentService;

    @Autowired
    private AppSyssetSmsTemplateService appSyssetSmsTemplateService;

    @Autowired
    private AppClassService appClassService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AppDormitoryService appDormitoryService;

    @Autowired
    private AppAttenceService appAttenceService;


    @Override
    public List<SdLeave> getLeaveByStuNo(String studentNo) {
        return list(new LambdaQueryWrapper<SdLeave>().eq(SdLeave::getStudentNo, studentNo));
    }

    @Override
    public List<SdLeave> getLeaveAll() {
        return sdLeaveDao.selectList(new LambdaQueryWrapper<SdLeave>());
    }

    @Override
    public SdLeave getLeaveById(Long id) {
        return sdLeaveDao.selectOne(new LambdaQueryWrapper<SdLeave>().eq(SdLeave::getId, id));
    }

    @Override
    public int saveLeave(SdLeave sdLeave) {
        BStudent bStudent = appStudentService.getStudentByNo(sdLeave.getStudentNo());

        if(sdLeave.getStatus()!=null&&sdLeave.getStatus().equals(CommonConstant.TEACHER_CONFIRE_PASS)){
            sdLeave.setClassId(bStudent.getClassId());
            sdLeave.setStudentPhone(bStudent.getPhone());
        }else{
            sdLeave.setClassId(bStudent.getClassId());
            sdLeave.setStudentPhone(bStudent.getPhone());
            sdLeave.setStatus(CommonConstant.INIT_LEAVE_STATUS);
        }
        int insert = sdLeaveDao.insert(sdLeave);
        if (insert > 0) {
            if(sdLeave.getStatus().equals(CommonConstant.TEACHER_CONFIRE_PASS)){
                //向考勤表添加考勤信息
                SdAttence sdAttence = new SdAttence();
                sdAttence.setAccessDate(new Date());
                sdAttence.setStudentNo(bStudent.getStudentNo());
                sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_LEAVE);
                sdAttence.setCreateTime(new Date());
                insert= appAttenceService.insert(sdAttence);
            }
            SyssetSmsTemplate bySmsTypee = appSyssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_LEAVE);
            //发送短信给家长
            SmsSendTemplate.sms(bStudent.getParentPhone(), bySmsTypee.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName()));
        }
        return insert;
    }

    //请假审核
    @Override
    public int auditLeave(SdLeave sdLeave) {
        int i = sdLeaveDao.updateById(sdLeave);
        if (i > 0) {
            BStudent bStudent = appStudentService.getStudentByNo(sdLeave.getStudentNo());
            SyssetSmsTemplate bySmsTypee = appSyssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_LEAVE_PASS);
            if(sdLeave.getStatus().equals(CommonConstant.PARENT_CONFIRE_PASS)){ //家长审核通过发短信给班主任
                BClass bClass = appClassService.getBClassById(bStudent.getClassId());
                SysUser sysUser = appUserService.getUserById(bClass.getClassTeacherId().intValue());
                //发送短信给班主任
                SmsSendTemplate.sms(sysUser.getPhone(), bySmsTypee.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName()));
            } if (sdLeave.getStatus().equals(CommonConstant.TEACHER_CONFIRE_PASS)){ //班主任审核通过发短信给你宿管家长
                BDormitory bDormitory = appDormitoryService.getBDormitoryById(Long.valueOf(bStudent.getBdormitoryId()));
                SysUser sysUser1 = appUserService.getUserById(bDormitory.getDormitoryTeacherId().intValue());
                //发送短信给家长
                SmsSendTemplate.sms(bStudent.getParentPhone(), bySmsTypee.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName()));
                //发送短信给宿管
                SmsSendTemplate.sms(sysUser1.getPhone(), bySmsTypee.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName()));
                //发送短信给学生本人
                SmsSendTemplate.sms(bStudent.getPhone(), bySmsTypee.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName()));

                //添加一条信息到考勤表
                SdAttence sdAttence = new SdAttence();
                sdAttence.setStudentNo(bStudent.getStudentNo());
                sdAttence.setAccessDate(new Date());
                sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_LEAVE);
                sdAttence.setCreateTime(new Date());
                i=appAttenceService.insert(sdAttence);
            }
        }
        return i;

    }

    @Override
    public int modifyLeave(SdLeave sdLeave) {
        sdLeave.setModifyTime(new Date());
        int insert = sdLeaveDao.updateById(sdLeave);
        return insert;
    }


}
