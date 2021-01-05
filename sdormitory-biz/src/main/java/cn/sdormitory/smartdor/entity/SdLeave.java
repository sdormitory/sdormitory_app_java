package cn.sdormitory.smartdor.entity;

import cn.hutool.core.date.DatePattern;
import cn.sdormitory.common.utils.poi.ExcelTitle;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @创建人：zhouyang
 * @创建时间：2020/11/27 17:34
 * @version：V1.0
 * 请假表
 */
@Data
@TableName("sd_leave")
public class SdLeave implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    @ApiModelProperty(value = "ID")
    @ExcelIgnore
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号")
    @ExcelProperty("学号")
    @ExcelTitle(title = "学号")
    @ColumnWidth(10)
    private String studentNo;

    /**
     * 所属班级ID
     */
    @ExcelIgnore
    @ApiModelProperty(value = "所属班级ID")
    private Long classId;

    /**
     * 班级名称
     */
    @ApiModelProperty(value = "班级名称")
    @ExcelProperty("班级名称")
    @ExcelTitle(title = "班级名称")
    @ColumnWidth(15)
    private String className;


    /**
     * 学生姓名
     */
    @ApiModelProperty(value = "学生姓名")
    @ExcelProperty("学生姓名")
    @ExcelTitle(title = "学生姓名")
    @ColumnWidth(15)
    private String studentName;

    /**
     * 学生手机号
     */
    @ExcelIgnore
    @ApiModelProperty(value = "学生手机号")
    private String studentPhone;

    /**
     * 请假类型
     */
    @ApiModelProperty(value = "请假类型")
    @ExcelProperty("请假类型(1:事假 2:病假)")
    @ExcelTitle(title = "请假类型(1:事假 2:病假)")
    @ColumnWidth(30)
    private String leaveType;

    /**
     * 请假日期
     */
    @ApiModelProperty(value = "请假日期")
    @ExcelProperty("请假日期(yyyy-MM-dd)")
    @ExcelTitle(title = "请假日期(yyyy-MM-dd)")
    @ColumnWidth(30)
    private String leaveDate;



    /**
     * 请假原因
     */
    @ApiModelProperty(value = "请假原因")
    @ExcelProperty("请假原因")
    @ExcelTitle(title = "请假原因")
    @ColumnWidth(30)
    private String leaveReason;

    /**
     * 状态：1家长确认中-2家长确认通过-3班主任审核通过、4家长驳回、5作废
     */
    @ExcelIgnore
    @ApiModelProperty(value = "状态：1家长确认中-2家长确认通过-3班主任审核通过、4家长驳回、5作废")
    private String status;

    /**
     * 家长描述信息
     */
    @ExcelIgnore
    @ApiModelProperty(value = "家长描述信息")
    private String parentDesc;

    /**
     * 班主任描述信息
     */
    @ExcelIgnore
    @ApiModelProperty(value = "班主任描述信息")
    private String teacherDesc;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @ExcelIgnore
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date modifyTime;


}
