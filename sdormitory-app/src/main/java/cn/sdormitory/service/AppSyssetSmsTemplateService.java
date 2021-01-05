package cn.sdormitory.service;

import cn.sdormitory.sysset.entity.SyssetSmsTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
public interface AppSyssetSmsTemplateService {

    /**
     * 根据id 获取短信模板信息
     * @param id
     * @return
     */
    public SyssetSmsTemplate getSyssetSmsTemplateById(Long id);


    /**
     * 根据短信模板标题获取短信模板信息
     *
     * @param smsTitle 短信模板标题
     */
    SyssetSmsTemplate getBySmsTitle(String smsTitle);

    /**
     * 根据模板类型获取短信模板信息
     * @param smsType
     * @return
     */
    SyssetSmsTemplate getBySmsTypee(int smsType);
}
