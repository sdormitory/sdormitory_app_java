package cn.sdormitory.sys.service.impl;

import cn.sdormitory.common.exception.ApiException;
import cn.sdormitory.config.SmsConfig;
import cn.sdormitory.sys.enums.SmsCodeTypeEnums;
import cn.sdormitory.sys.service.SmsStrategy;
import cn.sdormitory.sys.vo.SmsTemplateDto;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import cn.sdormitory.common.exption.SmsExceptionEnum;
import cn.sdormitory.common.utils.RedisKeys;
import cn.sdormitory.common.utils.RedisUtils;
import cn.sdormitory.sys.param.SmsParam;
import cn.sdormitory.sys.service.SmsService;
import cn.sdormitory.sys.service.SysSmsLogService;
import cn.sdormitory.sys.service.SysSmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * ClassName: SmsServiceImpl
 */
@Slf4j
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsConfig smsConfig;
    @Resource
    private SysSmsLogService sysSmsLogService;
    @Resource
    private SysSmsTemplateService sysSmsTemplateService;
    @Resource
    private SmsStrategy smsStrategy;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public String sendSmsCode(String mobile, String msgCodType) {
        // 1.看缓存中是否有短信验证码
        String key = RedisKeys.getMsgCodeKey(mobile, SmsCodeTypeEnums.getValueByType(msgCodType));
        String msgCode = redisUtils.get(key);

        if (StrUtil.isEmpty(msgCode)) {
            msgCode = RandomUtil.randomNumbers(6);
            //三分钟内有效
            long expireTime = 3 * 60;
            redisUtils.set(key, msgCode, expireTime);
        }
        // 2.发送短信
        SmsParam.SmsParamBuilder smsParam = SmsParam.builder()
                .mobile(mobile)
                .templateKey(SmsCodeTypeEnums.getCodeByType(msgCodType))
                //获取验证码 默认CODE
                .param("code", msgCode);
        sendSms(smsParam.build());
        return smsConfig.isEnabled() ? "0" : msgCode;
    }


    @Override
    public boolean verifySmsCode(String mobile, String msgCode, String msgCodType) {
        String key = RedisKeys.getMsgCodeKey(mobile, SmsCodeTypeEnums.getValueByType(msgCodType));
        String verifyCode = redisUtils.get(key);
        if (!Objects.equals(msgCode, verifyCode)) {
            return false;
        }
        // 验证通过后删除缓存信息
        redisUtils.delete(key);
        return true;
    }


    /**
     * desc 发送短信信息
     *
     * @param smsParam
     * @return
     * @author honghh
     * date 2019-08-30 14:46
     */
    @Override
    public Boolean sendSms(SmsParam smsParam) {
        log.info("发送短信开始：{}", JSON.toJSONString(smsParam));
        SmsTemplateDto smsTemplate = sysSmsTemplateService.geSmsTemplateByCode(Long.valueOf(smsParam.getTemplateKey()));
        if (smsTemplate == null) {
            throw new ApiException(SmsExceptionEnum.TEMPLATE_CODE_ERROR);
        }
        //调用第三方发送短信
        SendSmsResponse sendSmsResponse = smsStrategy.send(smsParam, smsTemplate);
        log.info("发送短信完成：{}", JSON.toJSONString(sendSmsResponse));
        return sysSmsLogService.addSmsLog(smsParam, smsTemplate, sendSmsResponse);
    }

}
