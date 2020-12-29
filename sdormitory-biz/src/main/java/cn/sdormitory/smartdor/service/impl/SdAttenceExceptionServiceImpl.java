package cn.sdormitory.smartdor.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.smartdor.dao.SdAttenceExceptionDao;
import cn.sdormitory.smartdor.entity.SdAttenceException;
import cn.sdormitory.smartdor.service.SdAttenceExceptionService;
import cn.sdormitory.sys.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/17 16:55
 * @version：V1.0
 */
@Slf4j
@Service("sdAttenceExceptionService")
public class SdAttenceExceptionServiceImpl extends ServiceImpl<SdAttenceExceptionDao, SdAttenceException> implements SdAttenceExceptionService {
    @Resource
    private SdAttenceExceptionDao sdAttenceExceptionDao;
    @Override
    public IPage<SdAttenceException> getPage(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));

        String studentNo = (String) params.get("studentNo");
        String status = (String) params.get("status");
        String className = (String) params.get("className");
        String studentName = (String) params.get("studentName");
        String absenceProcessStatus = (String) params.get("absenceProcessStatus");

        LambdaQueryWrapper<SdAttenceException> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotEmpty(className)) {
            wrapper.eq(SdAttenceException::getClassName, className);
        }
        if (StrUtil.isNotEmpty(status)) {
            wrapper.eq(SdAttenceException::getStatus, status);
        }
        if (StrUtil.isNotEmpty(studentNo)) {
            wrapper.eq(SdAttenceException::getStudentNo, studentNo);
        }
        if (StrUtil.isNotEmpty(studentName)) {
            wrapper.eq(SdAttenceException::getStudentName, studentName);
        }
        if (StrUtil.isNotEmpty(absenceProcessStatus)) {
            wrapper.eq(SdAttenceException::getAbsenceProcessStatus, absenceProcessStatus);
        }


        wrapper.apply(params.get(CommonConstant.SQL_FILTER) != null, (String) params.get(CommonConstant.SQL_FILTER));
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public SdAttenceException getAttenceExceptionById(Long id) {
        return getById(id);
    }

    @Override
    public int create(SdAttenceException sdAttenceException) {
        return this.baseMapper.insert(sdAttenceException);
    }

    @Override
    public int delete(Long id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int update(Long id, SdAttenceException sdAttenceException) {
        sdAttenceException.setId(id);
        sdAttenceException.setModifyTime(new Date());
        return this.baseMapper.updateById(sdAttenceException);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public int updateStatus(Long id, String status) {
        SdAttenceException sdAttenceException = new SdAttenceException();
        sdAttenceException.setId(id);
        sdAttenceException.setStatus(status);
        return this.baseMapper.updateById(sdAttenceException);
    }

    @Override
    public SdAttenceException getByStuNoAndDate(String studentNo) {
        return sdAttenceExceptionDao.getByStuNoAndDate(studentNo);
    }

    @Override
    public List<SdAttenceException> getAttenceExceptionList(String studentNo, String studentName, String className, String absenceProcessStatus, String status, int pageNum, int pageSize) {
        int currIndex=(pageNum-1)*pageSize;
        return sdAttenceExceptionDao.getAttenceExceptionList(studentNo,studentName,className,absenceProcessStatus,status,currIndex,pageSize);
    }

    @Override
    public SdAttenceException getExcByStuNoAndCreTime(String studentNo, Date createTime) {
        return sdAttenceExceptionDao.getExcByStuNoAndCreTime(studentNo,createTime);
    }
}
