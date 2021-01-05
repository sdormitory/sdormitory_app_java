package cn.sdormitory.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.basedata.dao.BClassDao;
import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.service.BClassService;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.service.AppClassService;
import cn.sdormitory.sys.entity.SysUser;
import cn.sdormitory.sys.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
@Slf4j
@Service
public class AppClassServiceImpl extends ServiceImpl<BClassDao, BClass> implements AppClassService {
    @Resource
    private BClassDao bClassDao;
    @Resource
    private SysUserService sysUserService;

    @Override
    public BClass getBClassById(Long id) {
        return getById(id);
    }

}
