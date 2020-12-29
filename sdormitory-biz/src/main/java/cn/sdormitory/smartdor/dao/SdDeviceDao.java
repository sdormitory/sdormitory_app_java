package cn.sdormitory.smartdor.dao;

import cn.sdormitory.smartdor.entity.SdDevice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/7 12:54
 * @version：V1.0
 */
@Mapper
public interface SdDeviceDao extends BaseMapper<SdDevice> {

//    /**
//     * 查询IP是否已经存在
//     * @param deviceIpAddress
//     * @return
//     */
//    SdDevice getSdDeviceByIP(@Param("deviceIpAddress") String deviceIpAddress);


    /**
     * 根据ID查询设备详细信息
     * @param id
     * @return
     */
    SdDevice getSdDeviceByID(@Param("id") Long id);


    /**
     * 根据设备IP查询设备号
     * @param deviceIpAddress
     * @return
     */
    String getDeviceNoByIp(@Param("deviceIpAddress") String deviceIpAddress);


    /**
     * 根据IP或者设备号查询对应设备
     * @param deviceIpAddress,deviceNo
     * @return
     */
    SdDevice getSdDeviceByIP(@Param("deviceIpAddress") String deviceIpAddress,@Param("deviceNo") String deviceNo);
}
