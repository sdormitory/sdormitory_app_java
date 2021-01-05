package cn.sdormitory.service;

import cn.sdormitory.smartdor.entity.SdAttence;


/**
 * Created By ruanteng
 * DateTime：2020/11/27
 */
public interface AppAttenceService {



    /**
     * 添加考勤
     * @param sdAttence
     * @return
     */
    int insert(SdAttence sdAttence);


}
