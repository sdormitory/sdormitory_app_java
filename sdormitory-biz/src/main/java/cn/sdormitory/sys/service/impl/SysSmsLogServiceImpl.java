package cn.sdormitory.sys.service.impl;

import cn.sdormitory.sys.dao.SysSmsLogDao;
import cn.sdormitory.sys.enums.StatusEnums;
import cn.sdormitory.sys.vo.SmsTemplateDto;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.sdormitory.sys.entity.SysSmsLog;
import cn.sdormitory.sys.enums.SmsTypeEnums;
import cn.sdormitory.sys.param.SmsParam;
import cn.sdormitory.sys.service.SysSmsLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 短信日志表
 *
 */
@Service("sysSmsLogService")
public class SysSmsLogServiceImpl extends ServiceImpl<SysSmsLogDao, SysSmsLog> implements SysSmsLogService {

    private static final String SMS_CODE = "OK";

    @Override
    public Boolean addSmsLog(SmsParam smsParam, SmsTemplateDto smsTemplate, SendSmsResponse sendSmsResponse) {
        SysSmsLog smsLog = new SysSmsLog();
        if (SMS_CODE.equals(sendSmsResponse.getCode())) {
            smsLog.setStatus(StatusEnums.ENABLE.getKey());
        } else {
            smsLog.setStatus(StatusEnums.DISABLE.getKey());
        }
        smsLog.setMobiles(String.join(",", smsParam.getMobileList()));
        smsLog.setParam(JSON.toJSONString(smsParam.getParams()));
        if (smsParam.getMobileList().size() > 1) {
            smsLog.setType(SmsTypeEnums.BATCH.getValue());
        } else {
            smsLog.setType(SmsTypeEnums.SINGLE.getValue());
        }
        smsLog.setTypeName(smsTemplate.getName());
        smsLog.setSource(smsTemplate.getSource());
        smsLog.setTemplateCode(smsTemplate.getTemplateCode());
        smsLog.setContent(format(smsTemplate.getContent(), smsParam.getParams()));
        smsLog.setResult(JSON.toJSONString(sendSmsResponse));
        if (this.baseMapper.insert(smsLog) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * desc 遍历map,用value替换掉key
     *
     * @param input
     * @param map
     * @return
     * @author honghh
     * date 2019-08-30 14:43
     */
    private static String format(String input, Map<String, String> map) {
        // 遍历map,用value替换掉key
        for (Map.Entry<String, String> entry : map.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        return input;
    }

}