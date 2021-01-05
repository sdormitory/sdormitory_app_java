package cn.sdormitory.service.impl;

import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.entity.BDormitory;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BClassService;
import cn.sdormitory.basedata.service.BDormitoryService;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.utils.SmsSendTemplate;
import cn.sdormitory.service.AppAttenceService;
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
import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import cn.sdormitory.sysset.service.SyssetAttenceRuleService;
import cn.sdormitory.sysset.service.SyssetSmsTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Service
public class AppAttenceServiceImpl extends ServiceImpl<SdAttenceDao, SdAttence> implements AppAttenceService {



    @Override
    public int insert(SdAttence sdAttence) {
        return this.baseMapper.insert(sdAttence);
    }






}
