package cn.sdormitory.service.impl;

import cn.sdormitory.service.AppDictDetailService;
import cn.sdormitory.sys.dao.SysDictDetailDao;
import cn.sdormitory.sys.entity.SysDictDetail;
import cn.sdormitory.sys.enums.StatusEnums;
import cn.sdormitory.sys.service.SysDictDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
@Service
public class AppDictDetailServiceImpl extends ServiceImpl<SysDictDetailDao, SysDictDetail> implements AppDictDetailService {


    @Override
    public List<SysDictDetail> selectDictDataByType(String dictType) {
        return this.list(new LambdaQueryWrapper<SysDictDetail>()
                .eq(SysDictDetail::getDictType, dictType)
                .eq(SysDictDetail::getStatus, StatusEnums.ENABLE.getKey()));
    }
}