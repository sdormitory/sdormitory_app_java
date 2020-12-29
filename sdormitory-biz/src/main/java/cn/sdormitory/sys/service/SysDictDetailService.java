package cn.sdormitory.sys.service;

import cn.sdormitory.sys.entity.SysDictDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 数据字典详情
 */
public interface SysDictDetailService {

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType
     * @return
     */
    List<SysDictDetail> selectDictDataByType(String dictType);

    /**
     * 分页查询字典数据
     *
     * @param dictDetail
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage<SysDictDetail> getPage(SysDictDetail dictDetail, Integer pageSize, Integer pageNum);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    SysDictDetail selectById(Long id);

    /**
     * 创建
     *
     * @param dictDetail
     * @return
     */
    int create(SysDictDetail dictDetail);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, String status);

    /**
     * 修改
     *
     * @param id
     * @param dictDetail
     * @return
     */
    int update(Long id, SysDictDetail dictDetail);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 导出
     * @param dictDetail
     * @return
     */
    List<SysDictDetail> getExportList(SysDictDetail dictDetail);

    /**
     * 获取对应label
     *
     * @param dictType
     * @param dictValue
     * @return
     */
    SysDictDetail getDetailByTypeAndValue(String dictType, String dictValue);
}

