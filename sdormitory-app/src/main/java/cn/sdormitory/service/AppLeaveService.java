package cn.sdormitory.service;

import cn.sdormitory.smartdor.entity.SdLeave;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 17:13
 * @version：V1.0
 */
public interface AppLeaveService {
    List<SdLeave> getLeaveByStuNo(String studentNo);

    List<SdLeave> getLeaveAll();

    SdLeave getLeaveById(Long id);

    int saveLeave(SdLeave sdLeave);

    int auditLeave(SdLeave sdLeave);

    int modifyLeave(SdLeave sdLeave);
}
