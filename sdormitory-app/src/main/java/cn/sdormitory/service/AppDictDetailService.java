package cn.sdormitory.service;

import cn.sdormitory.sys.entity.SysDictDetail;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
public interface AppDictDetailService {

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType
     * @return
     */
    List<SysDictDetail> selectDictDataByType(String dictType);


}

