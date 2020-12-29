package cn.sdormitory.basedata.dao;

import cn.sdormitory.basedata.entity.BStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @创建人：zhouyang
 * @创建时间：2020/11/10 16:21
 * @version：V1.0
 */
@Mapper
public interface BStudentDao extends BaseMapper<BStudent> {
    //APP端学员登录接口
    BStudent doLoginStudent(@Param("phone")String phone);
    //APP端家长登录接口
    BStudent doLoginParent(@Param("parentPhone")String parentPhone);

    String getStuPassword(@Param("id")int id);

    String getParPassword(@Param("id")int id);

    int updateStuPassword(@Param("id") int id ,@Param("newPassword") String newPassword);

    int updateParPassword(@Param("id") int id ,@Param("newPassword") String newPassword);
}
