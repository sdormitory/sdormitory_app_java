package cn.sdormitory.report.service.impl;

import cn.sdormitory.report.dao.HygieneStatisticsDao;
import cn.sdormitory.report.service.HygieneStatisticsService;
import cn.sdormitory.report.vo.HygieneStatisticsVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/5 12:26
 * @version：V1.0
 */
@Slf4j
@Service("hygieneStatisticsService")
public class HygieneStatisticsServiceImpl extends ServiceImpl<HygieneStatisticsDao, HygieneStatisticsVO> implements HygieneStatisticsService {
    @Resource
    HygieneStatisticsDao hygieneStatisticsDao;
    @Override
    public List<HygieneStatisticsVO> getHygieneStaList(String inYearMonth, int pageNum, int pageSize) {
        int currIndex=(pageNum-1)*pageSize;
        return hygieneStatisticsDao.getHygieneStaList(inYearMonth,currIndex,pageSize);
    }

}
