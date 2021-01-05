package cn.sdormitory.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.basedata.dao.BDormitoryDao;
import cn.sdormitory.basedata.entity.BDormitory;
import cn.sdormitory.basedata.service.BDormitoryService;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.service.AppDormitoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
@Slf4j
@Service
public class AppDormitoryServiceImpl extends ServiceImpl<BDormitoryDao, BDormitory> implements AppDormitoryService {
    @Resource
    private BDormitoryDao bDormitoryDao;

    @Override
    public BDormitory getBDormitoryById(Long id) {
        return getById(id);
    }


    @Override
    public BDormitory getByDormitoryInfo(String buildingNo, String storey, String dormitoryNo) {
        LambdaQueryWrapper<BDormitory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BDormitory::getBuildingNo, buildingNo);
        wrapper.eq(BDormitory::getStorey, storey);
        wrapper.eq(BDormitory::getDormitoryNo, dormitoryNo);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public List<BDormitory> getListAll() {
        return list(new LambdaQueryWrapper<BDormitory>());
    }

    @Override
    public List<BDormitory> getBDormitoryListByCol(String buildingNo, String storey) {
        return bDormitoryDao.getBDormitoryListByCol(buildingNo,storey);
    }

    @Override
    public List<BDormitory> getBDormitoryList(String buildingNo, String storey, String dormitoryNo, String status, int pageNum,int pageSize) {
        int currIndex=(pageNum-1)*pageSize;
        return bDormitoryDao.getBDormitoryList(buildingNo,storey,dormitoryNo,status,currIndex,pageSize);
    }
}
