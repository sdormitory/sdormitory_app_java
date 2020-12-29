package cn.sdormitory.sys.dao;

import cn.sdormitory.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户表
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {
    List<SysUser> getClassTeacherList();

    List<SysUser> getDorTeacherList();

    //APP端用户登录接口
    SysUser doLogin(@Param("username")String username);

    String getLoginPassword(@Param("id")int id);

    int updatePassword(@Param("id") int id ,@Param("newPassword") String newPassword);

}
