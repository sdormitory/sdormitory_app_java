package cn.sdormitory.report.service;

import cn.sdormitory.report.vo.HygieneStatisticsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/5 12:25
 * @version：V1.0
 */
public interface HygieneStatisticsService extends IService<HygieneStatisticsVO> {
    List<HygieneStatisticsVO> getHygieneStaList(String inYearMonth, int pageNum, int pageSize);
}
