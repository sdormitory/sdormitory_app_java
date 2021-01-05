package cn.sdormitory.service;

import cn.sdormitory.basedata.entity.BDormitory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
public interface AppDormitoryService {

    /**
     * 根据id 获取宿舍信息
     * @param id
     * @return
     */
    public BDormitory getBDormitoryById(Long id);



    /**
     * 根据几栋+楼层+宿舍号获取宿舍信息
     *
     * @param buildingNo 几栋
     * @param storey 楼层
     * @param dormitoryNo 宿舍号
     */
    BDormitory getByDormitoryInfo(String buildingNo,String storey,String dormitoryNo);

    /**
     * 获取所有的宿舍列表
     *
     * @return
     */
    List<BDormitory> getListAll();

    //根据宿舍栋号和楼层查询宿舍信息
    List<BDormitory> getBDormitoryListByCol(@Param("buildingNo")String buildingNo, @Param("storey")String storey);

    //宿舍表中新增宿管老师ID字段，查询列表数据
    List<BDormitory> getBDormitoryList(String buildingNo,String storey,String dormitoryNo, String status,int pageNum,int pageSize);
}
