package cn.sdormitory.smartdor.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/17 16:00
 * @version：V1.0
 * 考勤异常处理表
 */
@Data
@TableName("sd_attence_exception")
public class SdAttenceException {
    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    private String studentNo;

    /**
     * 所属班级ID
     */
    @ApiModelProperty(value = "所属班级ID")
    private Long classId;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    private String className;

    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 处理状态(1联系不上，2已联系上确认迟到、3已联系上确认回家、4已联系上确认擅自离校需进一步处理)
     */
    @ApiModelProperty(value = "处理状态")
    private String absenceProcessStatus;

    /**
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    private String processDesc;

    /**
     * 处理人(当前登录人)
     */
    @ApiModelProperty(value = "处理人")
    private String processPerson;

    /**
     * 启用状态：0->无效；1->有效
     */
    @ApiModelProperty(value = "启用状态：0->无效；1->有效")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    @DateTimeFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date modifyTime;

    //宿舍信息
    @TableField(exist = false)
    private String dorAddress;



}
