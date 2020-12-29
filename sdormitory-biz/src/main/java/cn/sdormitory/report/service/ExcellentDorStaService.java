package cn.sdormitory.report.service;

import cn.sdormitory.report.vo.ExcellentDorStaVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/4 22:34
 * @version：V1.0
 */
public interface ExcellentDorStaService {
    List<ExcellentDorStaVO> getExcDorStaList(String inMonth, int pageNum, int pageSize);
}
