package cn.sdormitory.smartdor.service;

import cn.sdormitory.smartdor.entity.SdAttenceException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/17 16:46
 * @version：V1.0
 */
public interface SdAttenceExceptionService {
    /**
     * 分页获取数据
     *
     * @param params
     * @return
     */
    public IPage<SdAttenceException> getPage(Map<String, Object> params);

    /**
     * 根据id 获取考勤异常处理
     * @param id
     * @return
     */
    public SdAttenceException getAttenceExceptionById(Long id);

    /**
     * 新建考勤异常
     *
     * @param sdAttenceException
     * @return
     */
    int create(SdAttenceException sdAttenceException);

    /**
     * 删除指定考勤异常
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改指定考勤异常
     *
     * @param id
     * @param sdAttenceException
     * @return
     */
    int update(Long id, SdAttenceException sdAttenceException);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 修改考勤异常处理状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, String status);

    /**
     * 根据学号+当天时间获取考勤异常处理信息
     *
     * @param studentNo 学号
     */
    SdAttenceException getByStuNoAndDate(String studentNo);

    /**
     * 获取所有的考勤异常处理列表
     *
     * @return
     */
    List<SdAttenceException> getAttenceExceptionList(String studentNo,String studentName,String className,String absenceProcessStatus,String status,int pageNum,int pageSize);

    SdAttenceException getExcByStuNoAndCreTime(String studentNo,Date createTime);
}
