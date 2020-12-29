package cn.sdormitory.smartdor.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.smartdor.dao.SdLeaveDao;
import cn.sdormitory.smartdor.entity.SdLeave;
import cn.sdormitory.smartdor.service.SdLeaveService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @创建人：zhouyang
 * @创建时间：2020/11/27 17:44
 * @version：V1.0
 */
@Slf4j
@Service("sdLeaveService")
public class SdLeaveServiceImpl  extends ServiceImpl<SdLeaveDao, SdLeave> implements SdLeaveService {
    @Resource
    BStudentService bStudentService;

    @Override
    public IPage<SdLeave> getPage(Map<String, Object> params) {
        int pageSize = Integer.parseInt(String.valueOf(params.get("pageSize")));
        int pageNum = Integer.parseInt(String.valueOf(params.get("pageNum")));

        String studentNo = (String) params.get("studentNo");
        String studentPhone = (String) params.get("studentPhone");
        String leaveType = (String) params.get("leaveType");
        String status = (String) params.get("status");

        LambdaQueryWrapper<SdLeave> wrapper = new LambdaQueryWrapper<>();


        if (StrUtil.isNotEmpty(studentNo)) {
            wrapper.eq(SdLeave::getStudentNo, studentNo);
        }
        if (StrUtil.isNotEmpty(studentPhone)) {
            wrapper.eq(SdLeave::getStudentPhone, studentPhone);
        }
        if (StrUtil.isNotEmpty(leaveType)) {
            wrapper.eq(SdLeave::getLeaveType, leaveType);
        }
        if (StrUtil.isNotEmpty(status)) {
            wrapper.eq(SdLeave::getStatus, status);
        }

        wrapper.apply(params.get(CommonConstant.SQL_FILTER) != null, (String) params.get(CommonConstant.SQL_FILTER));
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public SdLeave getSdLeaveById(Long id) {
        return getById(id);
    }

    @Override
    public int delete(Long id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int update(Long id, SdLeave sdLeave) {
        sdLeave.setId(id);
        return this.baseMapper.updateById(sdLeave);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public int updateStatusApprove(Long id, String status) {
        SdLeave sdLeave= new SdLeave();
        sdLeave.setId(id);
        sdLeave.setStatus(status);
        return this.baseMapper.updateById(sdLeave);
    }

    @Override
    public SdLeave getLeaveByStuNoAndLeaDate(String studentNo, String leaveDate) {
        LambdaQueryWrapper<SdLeave> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SdLeave::getStudentNo, studentNo);
        wrapper.eq(SdLeave::getLeaveDate, leaveDate);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public int insert(SdLeave sdLeave) {
        //判断学号+姓名+班级是否存在
        BStudent bStudent=bStudentService.getByStuNoAndNameAndClassName(sdLeave.getStudentNo(),sdLeave.getStudentName(),sdLeave.getClassName());
        if(bStudent==null){
            return -2;
        }

        SdLeave sdLeave1=this.getLeaveByStuNoAndLeaDate(sdLeave.getStudentNo(),sdLeave.getLeaveDate());
        if(sdLeave1 == null) {
            sdLeave.setStatus(CommonConstant.PARENT_CONFIRE_PASS);
            sdLeave.setCreateTime(new Date());
            sdLeave.setStudentPhone(bStudent.getPhone());
            sdLeave.setClassId(bStudent.getClassId());
            if("".equals(sdLeave.getStudentName()) || sdLeave.getStudentName()==null){
                sdLeave.setStudentName(bStudent.getStudentName());
            }
            if("".equals(sdLeave.getClassName()) || sdLeave.getClassName()==null){
                sdLeave.setClassName(bStudent.getClassName());
            }
            return this.baseMapper.insert(sdLeave);
        } else {
            return -1;
        }
    }
}
