package cn.sdormitory.smartdor.service.impl;

import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.entity.BDormitory;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BClassService;
import cn.sdormitory.basedata.service.BDormitoryService;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.basedata.vo.BStudentVo;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.utils.DateTimeUtils;
import cn.sdormitory.common.utils.PropertiesUtils;
import cn.sdormitory.common.utils.SmsSendTemplate;
import cn.sdormitory.request.HttpRequest;
import cn.sdormitory.smartdor.dao.OriginalRecordDao;
import cn.sdormitory.smartdor.entity.OriginalRecord;
import cn.sdormitory.smartdor.entity.SdAttence;
import cn.sdormitory.smartdor.service.OriginalRecordService;
import cn.sdormitory.smartdor.service.SdAttenceService;
import cn.sdormitory.sys.entity.SysUser;
import cn.sdormitory.sys.service.SysUserService;
import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import cn.sdormitory.sysset.service.SyssetAttenceRuleService;
import cn.sdormitory.sysset.service.SyssetSmsTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created By ruanteng
 * DateTime：2020/12/7
 */
@Service("originalRecordService")
public class OriginalRecordServiceImpl extends ServiceImpl<OriginalRecordDao, OriginalRecord> implements OriginalRecordService {


    @Autowired
    private BStudentService bStudentService;

    @Autowired
    private SyssetAttenceRuleService syssetAttenceRuleService;

    @Autowired
    private SdAttenceService sdAttenceService;

    @Autowired
    private SyssetSmsTemplateService syssetSmsTemplateService;

    @Autowired
    private BClassService bClassService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BDormitoryService bDormitoryService;

    @Override
    public CommonPage<OriginalRecord> getPage(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));
        String dateStr = String.valueOf(params.get("accessDate"));
        if ("null".equals(dateStr)) {
            dateStr = null;
        }
        List<OriginalRecord> list = this.baseMapper.getList(dateStr, (pageNum - 1) * pageSize, pageSize);
        CommonPage commonPage = new CommonPage();
        commonPage.setList(list);
        commonPage.setPageNum(pageNum);
        commonPage.setPageSize(pageSize);
        long i = Long.valueOf(this.baseMapper.getListCount(dateStr));
        commonPage.setTotal(i);
        return commonPage;


    }

    @Override
    public int create(BStudentVo vo) throws ParseException {
        BStudent bStudent = bStudentService.getByStudentNo(vo.getPersonId());
        if(bStudent==null){
            return 0;
        }
        OriginalRecord originalRecord = new OriginalRecord();
        originalRecord.setStudentNo(vo.getPersonId());
        originalRecord.setAccessDate(DateTimeUtils.dateTimeFormat(vo.getTs()));
        originalRecord.setDeviceNo(vo.getDeviceSn());
        originalRecord.setCreateTime(new Date());
        //originalRecord.setAttenceStatus(syssetAttenceRuleService.getByAttenceRuleByTime(DateTimeUtils.dateTimeFormat(vo.getTs())));
        if(new Date().getHours()>=22){
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String accessDate1=sdf.format(date);
            String accessDate=accessDate1+" "+CommonConstant.ATTENDANCE_DATE;
            SdAttence attence=sdAttenceService.getCombacklate(originalRecord.getStudentNo(),accessDate);
            if(attence==null){
                SdAttence sdAttence = new SdAttence();
                sdAttence.setDeviceNo(originalRecord.getDeviceNo());
                sdAttence.setAccessDate(originalRecord.getAccessDate());
                sdAttence.setStudentNo(originalRecord.getStudentNo());
                sdAttence.setAttenceStatus(CommonConstant.ATTENDANCE_COMEBACKLATE);
                sdAttence.setCreateTime(new Date());
                sdAttenceService.insert(sdAttence);
                BClass bClass = bClassService.getBClassById(bStudent.getClassId());
                BDormitory bDormitory = bDormitoryService.getBDormitoryById(Long.valueOf(bStudent.getBdormitoryId()));
                SysUser sysUser = sysUserService.getUserById(bClass.getClassTeacherId());
                SysUser sysUser1 =sysUserService.getUserById(bDormitory.getDormitoryTeacherId());
                SyssetSmsTemplate syssetSmsTemplate = syssetSmsTemplateService.getBySmsTypee(CommonConstant.SMS_TEMPLATE_TYPE_ATTENCE);
                String text = syssetSmsTemplate.getSmsContent().replace("{student}", bStudent.getStudentName());
                SmsSendTemplate.sms(bStudent.getParentPhone(), text);
                SmsSendTemplate.sms(sysUser.getPhone(),text);
                SmsSendTemplate.sms(sysUser1.getPhone(),text);
            }else{
                SdAttence sdAttence = new SdAttence();
                sdAttence.setAccessDate(originalRecord.getAccessDate());
                sdAttenceService.update(attence.getId(),sdAttence);
            }

        }
        return this.baseMapper.insert(originalRecord);

    }

    @Override
    public int delete(String[] id) {
        int count = 0;
        try {
            bStudentService.removePerson(id);
            count = this.baseMapper.deleteBatchIds(Arrays.asList(id));
        } catch (Exception e) {
            e.printStackTrace();
            count = 0;
        }
        return count;

    }

    @Override
    public int getCount(Map<String, Object> params) {
        String dateStr = String.valueOf(params.get("accessDate"));
        if ("null".equals(dateStr)) {
            dateStr = null;
        }
        int count = this.baseMapper.getListCount(dateStr);
        return count;
    }

    @Override
    public List<OriginalRecord> getListByDate() {
        return this.baseMapper.listAll();
    }

    @Override
    public void removeRecord(double ts) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
//        HttpRequest.sendPost(ip+"/removeRecord?key="+key+"&ts="+ts,null);
        //修复pairs直接传空报空指针问题
        List pairs = new ArrayList();
        pairs.add(new BasicNameValuePair("key",key));
        pairs.add(new BasicNameValuePair("ts",""+ts));
        HttpRequest.sendPost(ip+"/removeRecord",pairs);


    }

    @Override
    public String listRecordByNumber(Integer number, Integer offset, Integer dbtype) {
        String key = PropertiesUtils.get("device.properties", "sdormitory.device1.key");
        String ip = PropertiesUtils.get("device.properties","sdormitory.device1.ip");
        String object = HttpRequest.sendGet(ip+"/listRecordByNumber?key="+key+"&dbtype="+dbtype+"&number="+number+"&offset="+offset,null);
        return object;
    }


}
