package cn.sdormitory.smartdor.entity;


import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *  Created By：ruanteng
 *  DateTime：2020/12/7
 *  过闸流水表
 */

@Data
@TableName("original_record")
public class OriginalRecord {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 设备号
     */
    private String deviceNo;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 出/入时间(刷脸时间)
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date accessDate;

    /**
     * 考勤状态，1正常、2缺勤、3晚归、4请假
     */
    private String attenceStatus;

    /**
     * 同步时间
     */
    private Date createTime;

    //学生名称
    @TableField(exist = false)
    private String studentName;


    //班级
    @TableField(exist = false)
    private String className;

    //设备出入类型
    @TableField(exist = false)
    private String deviceAccessType;
}
