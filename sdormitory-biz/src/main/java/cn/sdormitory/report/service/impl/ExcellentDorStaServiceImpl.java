package cn.sdormitory.report.service.impl;

import cn.sdormitory.report.dao.ExcellentDorStaDao;
import cn.sdormitory.report.service.ExcellentDorStaService;
import cn.sdormitory.report.vo.ExcellentDorStaVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/4 22:35
 * @version：V1.0
 */
@Slf4j
@Service("excellentDorStaService")
public class ExcellentDorStaServiceImpl extends ServiceImpl<ExcellentDorStaDao, ExcellentDorStaVO> implements ExcellentDorStaService {
    @Resource
    ExcellentDorStaDao excellentDorStaDao;
    @Override
    public List<ExcellentDorStaVO> getExcDorStaList(String inMonth, int pageNum, int pageSize) {
        int currIndex=(pageNum-1)*pageSize;
        return excellentDorStaDao.getExcDorStaList(inMonth,currIndex,pageSize);
    }
}
