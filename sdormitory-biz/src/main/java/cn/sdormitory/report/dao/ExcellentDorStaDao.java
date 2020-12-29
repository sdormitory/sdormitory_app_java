package cn.sdormitory.report.dao;

import cn.sdormitory.report.vo.ExcellentDorStaVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/4 22:21
 * @version：V1.0
 */
@Mapper
public interface ExcellentDorStaDao extends BaseMapper<ExcellentDorStaVO> {
    List<ExcellentDorStaVO> getExcDorStaList(@Param("inMonth") String inMonth, @Param("currIndex")int currIndex, @Param("pageSize")int pageSize);
}
