package cn.sdormitory.smartdor.dao;

import cn.sdormitory.smartdor.entity.SdAttenceException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/17 16:06
 * @version：V1.0
 */
@Mapper
public interface SdAttenceExceptionDao extends BaseMapper<SdAttenceException> {
    SdAttenceException getByStuNoAndDate(@Param("studentNo")String studentNo);
    List<SdAttenceException> getAttenceExceptionList(@Param("studentNo")String studentNo,@Param("studentName")String studentName, @Param("className")String className, @Param("absenceProcessStatus")String absenceProcessStatus, @Param("status")String status, @Param("currIndex")int currIndex,@Param("pageSize")int pageSize);
    SdAttenceException getExcByStuNoAndCreTime(@Param("studentNo")String studentNo,@Param("createTime") Date createTime);
}
