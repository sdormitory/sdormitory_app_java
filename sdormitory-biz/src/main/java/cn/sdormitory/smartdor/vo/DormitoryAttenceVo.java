package cn.sdormitory.smartdor.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/10 16:50
 * @version：V1.0
 */
@Data
public class DormitoryAttenceVo implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 宿舍总人数
     */
    private Long total;

    /**
     * 宿舍详细地址
     */
    private String roomName;

    /**
     * 考勤正常人数
     */
    private Long normal;

    /**
     * 请假人数
     */
    private Long leaveCount;

    /**
     * 晚归人数
     */
    private Long comebacklate;

    /**
     * 缺勤人数
     */
    private Long absence;
}
