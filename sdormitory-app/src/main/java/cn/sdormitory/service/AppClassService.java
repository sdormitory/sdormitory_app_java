package cn.sdormitory.service;

import cn.sdormitory.basedata.entity.BClass;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
public interface AppClassService {

    /**
     * 根据id 获取班级信息
     * @param id
     * @return
     */
    public BClass getBClassById(Long id);



}
