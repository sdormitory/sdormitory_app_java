package cn.sdormitory.sys.dao;

import cn.sdormitory.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户和角色关系表
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {
    /**
     * 根据用户id 批量添加用户角色关系
     * @param userId
     * @param roleIdList
     */
    void insertUserAndUserRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);


    /**
     * 根据用户ID 获取角色数据权限标识
     * @param userId
     * @return
     */
    List<Integer> listDataScopesByUserId(Long userId);
}
