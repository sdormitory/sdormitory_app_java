package cn.sdormitory.sys.service;

import cn.sdormitory.sys.entity.SysDict;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 数据字典
 */
public interface SysDictService {

    /**
     * 根据关键字查询字典信息
     *
     * @param sysDict
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage<SysDict> getPage(SysDict sysDict, Integer pageSize, Integer pageNum);

    /**
     * 创建字典信息
     *
     * @param sysDict
     * @return
     */
    int create(SysDict sysDict);

    /**
     * 修改字典状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, String status);

    /**
     * 更新字典
     *
     * @param id
     * @param sysDict
     * @return
     */
    int update(Long id, SysDict sysDict);

    /**
     * 根据ID 获取字典信息
     *
     * @param id
     * @return
     */
    SysDict selectById(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 获取导出的数据
     *
     * @param sysDict
     * @return
     */
    List<SysDict> getExportList(SysDict sysDict);
}

