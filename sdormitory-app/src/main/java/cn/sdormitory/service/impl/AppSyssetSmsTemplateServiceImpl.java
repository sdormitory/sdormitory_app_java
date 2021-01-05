package cn.sdormitory.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.service.AppSyssetSmsTemplateService;
import cn.sdormitory.sysset.dao.SyssetSmsTemplateDao;
import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
@Slf4j
@Service
public class AppSyssetSmsTemplateServiceImpl extends ServiceImpl<SyssetSmsTemplateDao, SyssetSmsTemplate> implements AppSyssetSmsTemplateService {

    @Override
    public SyssetSmsTemplate getSyssetSmsTemplateById(Long id) {
        return getById(id);
    }

    @Override
    public SyssetSmsTemplate getBySmsTitle(String smsTitle) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<SyssetSmsTemplate>().eq(SyssetSmsTemplate::getSmsTitle, smsTitle));
    }

    @Override
    public SyssetSmsTemplate getBySmsTypee(int smsType) {
        return this.baseMapper.selectOne(new LambdaQueryWrapper<SyssetSmsTemplate>().eq(SyssetSmsTemplate::getSmsType, smsType));
    }
}
