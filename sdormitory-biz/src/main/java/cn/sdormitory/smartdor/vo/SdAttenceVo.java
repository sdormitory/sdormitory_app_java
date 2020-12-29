package cn.sdormitory.smartdor.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/10 16:52
 * @version：V1.0
 */
@Data
public class SdAttenceVo implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 宿舍详细地址
     */
    private String dorAddress;
}
