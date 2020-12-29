package cn.sdormitory.smartdor.service.impl;

import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.entity.BDormitory;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BClassService;
import cn.sdormitory.basedata.service.BDormitoryService;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.utils.DateTimeUtils;
import cn.sdormitory.common.utils.SmsSendTemplate;
import cn.sdormitory.smartdor.dao.SdAttenceDao;
import cn.sdormitory.smartdor.entity.OriginalRecord;
import cn.sdormitory.smartdor.entity.SdAttence;
import cn.sdormitory.smartdor.entity.SdDevice;
import cn.sdormitory.smartdor.service.OriginalRecordService;
import cn.sdormitory.smartdor.service.SdAttenceService;
import cn.sdormitory.smartdor.service.SdDeviceService;
import cn.sdormitory.smartdor.vo.DormitoryAttenceVo;
import cn.sdormitory.smartdor.vo.SdAttenceVo;
import cn.sdormitory.sys.entity.SysDictDetail;
import cn.sdormitory.sys.entity.SysUser;
import cn.sdormitory.sys.service.SysDictDetailService;
import cn.sdormitory.sys.service.SysUserService;
import cn.sdormitory.sysset.entity.SyssetAttenceRule;
import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import cn.sdormitory.sysset.service.SyssetAttenceRuleService;
import cn.sdormitory.sysset.service.SyssetSmsTemplateService;
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created By ruanteng
 * DateTime：2020/11/27
 */
@Slf4j
@Service("sdAttenceServiceImpl")
public class SdAttenceServiceImpl extends ServiceImpl<SdAttenceDao, SdAttence> implements SdAttenceService {

    @Autowired
    private BStudentService bStudentService;

    @Autowired
    private OriginalRecordService originalRecordService;

    @Autowired
    private BClassService bClassService;

    @Autowired
    private SyssetSmsTemplateService syssetSmsTemplateService;

    @Autowired
    private SyssetAttenceRuleService syssetAttenceRuleService;

    @Autowired
    private BDormitoryService bDormitoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SdDeviceService sdDeviceService;
    @Autowired
    private SysDictDetailService sysDictDetailService;

    @Override
    public CommonPage<SdAttence> getPage(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        String dateStr = String.valueOf(params.get("checkDate"));
        if ("null".equals(dateStr)) {
            dateStr = null;
        }
        List<SdAttence> list = this.baseMapper.getList(dateStr, (pageNum - 1) * pageSize, pageSize);

        CommonPage commonPage = CommonPage.getCommonPage(pageNum, pageSize, this.baseMapper.getListCount(dateStr), list);

        return commonPage;


    }

    @Override
    public void create() throws ParseException {
        List<OriginalRecord> list = originalRecordService.getListByDate();
        System.out.println("-----------------   "+list+"   ----------------"+list.size());
//        list.stream().forEach(a -> {
//            SdDevice sdDevice = sdDeviceService.getDeviceNo(a.getDeviceNo());
//            try {
//                //判断今天是否需要考勤
//                if (syssetAttenceRuleService.getByAttenceRuleByTime(new Date(),sdDevice.getAttenceRuleId().intValue()) != null)
//                    return;
//            } catch (ParseException exception) {
//                exception.printStackTrace();
//            }
//            if (sdDevice.getAttenceRuleId()==null || sdDevice.getAttenceRuleId()<=0) {
//                //如果考勤规则为空则不考勤
//            } else {
//                SyssetAttenceRule syssetAttenceRule = syssetAttenceRuleService.getSyssetAttenceRuleById(sdDevice.getAttenceRuleId());
//                //按考勤规则来判断今天是否考勤
//                if (!syssetAttenceRule.getAttenceDay().contains(String.valueOf(DateTimeUtils.dateTimeFormat(System.currentTimeMillis()).getDay()))){
//                    return;
//                }
//                BStudent bStudent = bStudentService.getByStudentNo(a.getStudentNo());
//                if (bStudent == null)
//                    return;
//                SdAttence sdAttence = new SdAttence();
//                if (a.getAccessDate() == null) {
//                    //没有考勤信息则发送短信给家长，班主任，宿管
//                    //查询对应的短信模板
//                    SyssetSmsTemplate syssetSmsTemplate = syssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_ATTENCE);
//                   //获取对应的宿舍信息
//                    SysDictDetail sysDictDetail=sysDictDetailService.getDetailByTypeAndValue("sys_building",bStudent.getBuildingNo());
//                    SysDictDetail sysDictDetail1=sysDictDetailService.getDetailByTypeAndValue("sys_storey",bStudent.getStorey());
//                    // if (CommonConstant.ATTENDANCE_COMEBACKLATE.equals(a.getAttenceStatus())) {
//                        String text = syssetSmsTemplate.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getClassName()+"学号："+bStudent.getStudentNo()+"姓名："+bStudent.getStudentName()+"宿舍："+sysDictDetail.getDictLabel()+sysDictDetail1.getDictLabel()+bStudent.getDormitoryNo());
//                        BClass bClass = bClassService.getBClassById(bStudent.getClassId());
//                        BDormitory bDormitory = bDormitoryService.getBDormitoryById(Long.valueOf(bStudent.getBdormitoryId()));
//                        SysUser sysUser = sysUserService.getUserById(bClass.getClassTeacherId());
//                        SysUser sysUser1 = sysUserService.getUserById(bDormitory.getId());
//                        SmsSendTemplate.sms(bStudent.getPhone(), text);
//                        SmsSendTemplate.sms(sysUser.getPhone(), text);
//                        SmsSendTemplate.sms(sysUser1.getPhone(), text);
//                        SmsSendTemplate.sms(bStudent.getParentPhone(), text);
//                   // }
//                } else {
//                    sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_NOMAL);
//                    sdAttence.setCreateTime(new Date());
//                    sdAttence.setStudentNo(a.getStudentNo());
//                    sdAttence.setDeviceNo(a.getDeviceNo());
//                    sdAttence.setAccessDate(a.getAccessDate());
//                    this.baseMapper.insert(sdAttence);
//                }
//
//            }
        list.stream().forEach(a -> {
            BStudent bStudent = bStudentService.getByStudentNo(a.getStudentNo());
            if (a.getAccessDate() == null) {
                //没有考勤信息则发送短信给家长，班主任，宿管
                //查询对应的短信模板
                SyssetSmsTemplate syssetSmsTemplate = syssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_ATTENCE);
                //获取对应的宿舍信息
                SysDictDetail sysDictDetail=sysDictDetailService.getDetailByTypeAndValue("sys_building",bStudent.getBuildingNo());
                SysDictDetail sysDictDetail1=sysDictDetailService.getDetailByTypeAndValue("sys_storey",bStudent.getStorey());
                String text = syssetSmsTemplate.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getClassName()+"学号："+bStudent.getStudentNo()+"姓名："+bStudent.getStudentName()+"宿舍："+sysDictDetail.getDictLabel()+sysDictDetail1.getDictLabel()+bStudent.getDormitoryNo());
                BClass bClass = bClassService.getBClassById(bStudent.getClassId());
                BDormitory bDormitory = bDormitoryService.getBDormitoryById(Long.valueOf(bStudent.getBdormitoryId()));
                SysUser sysUser = sysUserService.getUserById(bClass.getClassTeacherId());
                SysUser sysUser1 = sysUserService.getUserById(bDormitory.getDormitoryTeacherId());
                SmsSendTemplate.sms(bStudent.getPhone(), text);
                SmsSendTemplate.sms(sysUser.getPhone(), text);
                SmsSendTemplate.sms(sysUser1.getPhone(), text);
                SmsSendTemplate.sms(bStudent.getParentPhone(), text);
            } else {
                SdDevice sdDevice = sdDeviceService.getDeviceNo(a.getDeviceNo());
                try {
                    //判断今天是否需要考勤
//                    if (syssetAttenceRuleService.getByAttenceRuleByTime(new Date(),sdDevice.getAttenceRuleId().intValue()) != null)
//                        return;
                    if (syssetAttenceRuleService.getByAttenceRuleByTime(a.getAccessDate(),sdDevice.getAttenceRuleId().intValue()) == null) {
                        return;
                    }
                } catch (ParseException exception) {
                    exception.printStackTrace();
                }
//                if (sdDevice.getAttenceRuleId()==null || sdDevice.getAttenceRuleId()<=0) {
//                    //如果考勤规则为空则不考勤
//                } else {
//                    SyssetAttenceRule syssetAttenceRule = syssetAttenceRuleService.getSyssetAttenceRuleById(sdDevice.getAttenceRuleId());
//                    //按考勤规则来判断今天是否考勤
//                    if (!syssetAttenceRule.getAttenceDay().contains(String.valueOf(DateTimeUtils.dateTimeFormat(System.currentTimeMillis()).getDay()))){
//                        return;
//                    }
                    if (bStudent == null) {
                        return;
                    }
                    SdAttence sdAttence = new SdAttence();
                    sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_NOMAL);
                    sdAttence.setCreateTime(new Date());
                    sdAttence.setStudentNo(a.getStudentNo());
                    sdAttence.setDeviceNo(a.getDeviceNo());
                    sdAttence.setAccessDate(a.getAccessDate());
                    this.baseMapper.insert(sdAttence);
                }
//            }
        });
    }

    @Override
    public int delete(String[] id) {
        int count = 0;
        try {
            count = this.baseMapper.deleteBatchIds(Arrays.asList(id));
        } catch (Exception e) {
            e.printStackTrace();
            count = 0;
        }
        return count;

    }

    @Override
    public int getCount(Map<String, Object> params) {
        String dateStr = String.valueOf(params.get("checkDate"));
        if ("null".equals(dateStr)) {
            dateStr = null;
        }
        int count = this.baseMapper.getListCount(dateStr);
        return count;
    }


    @Override
    public int insert(SdAttence sdAttence) {
        return this.baseMapper.insert(sdAttence);
    }


    @Override
    public CommonPage<SdAttenceVo> listAbsenceStudent(Map<String,Object> map) {
        if(new Date().getHours()<22&& (map.get("checkDate") == null||map.get("checkDate") == "")){
            Calendar cal= Calendar.getInstance();
            cal.add(Calendar.DATE,-1);
            Date d=cal.getTime();
            map.put("checkDate",d);
        }
        List<SdAttenceVo> list = this.baseMapper.listAbsenceStudent(map);
        CommonPage<SdAttenceVo> commonPage = new CommonPage<>();
        commonPage.setList(list);
        return  commonPage;
    }

    @Override
    public CommonPage<DormitoryAttenceVo> listAbsenceDormitory(Map<String, Object> map) {
        if (new Date().getHours() < CommonConstant.ATTENDANCE_TIME_INT && (map.get("checkDate") == null || map.get("checkDate") == "")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date d = cal.getTime();
            map.put("checkDate", d);

        }
        List<DormitoryAttenceVo> list = this.baseMapper.dormitoryAttenceVos(map);
        CommonPage<DormitoryAttenceVo> commonPage = new CommonPage<>();
        commonPage.setList(list);
        return commonPage;
    }

    @Override
    public void statisticsLackStu() {
        SyssetSmsTemplate syssetSmsTemplate = syssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_ATTENCE);
        List<SdAttenceVo> list = this.getLackStu();
        list.stream().forEach(a -> {
            BStudent bStudent = bStudentService.getByStudentNo(a.getStudentNo());
            SdAttence sdAttence = new SdAttence();
            sdAttence.setStudentNo(a.getStudentNo());
            sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_ABSENCE);
            //增加缺勤时间(当天)
            Date date = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            try {
                sdAttence.setAccessDate(sdf.parse(sdf.format(date)));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            this.baseMapper.insert(sdAttence);
            String text = syssetSmsTemplate.getSmsContent().replace(CommonConstant.SMS_TEMPLATE_STR, bStudent.getStudentName());
            BClass bClass = bClassService.getBClassById(bStudent.getClassId());
            BDormitory bDormitory = bDormitoryService.getBDormitoryById(Long.valueOf(bStudent.getBdormitoryId()));
            SysUser sysUser = sysUserService.getUserById(bClass.getClassTeacherId());
            SysUser sysUser1 = sysUserService.getUserById(bDormitory.getDormitoryTeacherId());

            //学生本人
            SmsSendTemplate.sms(bStudent.getPhone(), text);
            //班主任
            SmsSendTemplate.sms(sysUser.getPhone(), text);
            //宿管
            SmsSendTemplate.sms(sysUser1.getPhone(), text);
            //家长
            SmsSendTemplate.sms(bStudent.getParentPhone(), text);
        });
    }

    @Override
    public List<SdAttenceVo> getLackStu() {
        return this.baseMapper.getLackStu();
    }

    @Override
    public SdAttence getCombacklate(String studentNo, String accessDate) {
        return this.baseMapper.getCombacklate(studentNo,accessDate);
    }

    @Override
    public int update(Long id, SdAttence sdAttence) {
        sdAttence.setId(id);
        return this.baseMapper.updateById(sdAttence);
    }


}
