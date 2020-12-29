package cn.sdormitory.basedata.dao;

import cn.sdormitory.basedata.entity.BDormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/11/7 20:16
 * @version：V1.0
 */
@Mapper
public interface BDormitoryDao extends BaseMapper<BDormitory> {
    List<BDormitory> getBDormitoryListByCol(@Param("buildingNo")String buildingNo, @Param("storey")String storey);
    List<BDormitory> getBDormitoryList(@Param("buildingNo")String buildingNo,@Param("storey")String storey,@Param("dormitoryNo")String dormitoryNo, @Param("status")String status,@Param("currIndex")int currIndex,@Param("pageSize")int pageSize);
}
