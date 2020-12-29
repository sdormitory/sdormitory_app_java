package cn.sdormitory.report.dao;

import cn.sdormitory.report.vo.HygieneStatisticsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/5 8:55
 * @version：V1.0
 */
@Mapper
public interface HygieneStatisticsDao extends BaseMapper<HygieneStatisticsVO> {
    List<HygieneStatisticsVO> getHygieneStaList(@Param("inYearMonth") String inYearMonth, @Param("currIndex")int currIndex, @Param("pageSize")int pageSize);
}
