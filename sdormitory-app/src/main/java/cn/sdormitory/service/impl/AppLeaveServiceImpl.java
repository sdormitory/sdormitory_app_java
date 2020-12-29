package cn.sdormitory.service.impl;

import cn.sdormitory.service.AppLeaveService;
import cn.sdormitory.smartdor.dao.SdLeaveDao;
import cn.sdormitory.smartdor.entity.SdLeave;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 17:14
 * @version：V1.0
 */
@Service
public class AppLeaveServiceImpl extends ServiceImpl<SdLeaveDao, SdLeave> implements AppLeaveService {
    @Resource
    private SdLeaveDao sdLeaveDao;

    @Override
    public List<SdLeave> getLeaveByStuNo(String studentNo) {
        return list(new LambdaQueryWrapper<SdLeave>().eq(SdLeave::getStudentNo,studentNo));
    }

    @Override
    public List<SdLeave> getLeaveAll() {
        return sdLeaveDao.selectList(new LambdaQueryWrapper<SdLeave>());
    }

    @Override
    public SdLeave getLeaveById(Long id) {
        return sdLeaveDao.selectOne(new LambdaQueryWrapper<SdLeave>().eq(SdLeave::getId,id));
    }


}
