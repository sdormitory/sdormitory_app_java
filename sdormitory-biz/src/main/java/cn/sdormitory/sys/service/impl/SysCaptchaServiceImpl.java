package cn.sdormitory.sys.service.impl;

import cn.sdormitory.common.exception.ApiException;
import cn.sdormitory.common.exption.KaptchaEnum;
import cn.sdormitory.sys.dao.SysCaptchaDao;
import cn.sdormitory.sys.entity.SysCaptcha;
import cn.sdormitory.sys.service.SysCaptchaService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 图片验证码
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaDao, SysCaptcha> implements SysCaptchaService {
    @Resource
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new ApiException(KaptchaEnum.UUID_NOT_NULL);
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptcha captchaEntity = new SysCaptcha();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtil.offsetMinute(new Date(), 5));
        save(captchaEntity);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptcha captchaEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<SysCaptcha>()
                .eq(SysCaptcha::getUuid, uuid));
        if (captchaEntity == null) {
            return false;
        }
        //删除验证码
        removeById(uuid);

        if (captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()) {
            return true;
        }

        return false;
    }

}