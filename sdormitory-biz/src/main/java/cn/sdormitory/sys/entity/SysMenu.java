package cn.sdormitory.sys.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台用户权限表
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long id;
    /**
     * 父级权限id
     */
    @ApiModelProperty(value = "父级权限id")
    private Long pid;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    private String value;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;
    /**
     * 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty(value = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;
    /**
     * 前端资源路径
     */
    @ApiModelProperty(value = "前端资源路径")
    private String uri;
    /**
     * 启用状态；0->禁用；1->启用
     */
    @ApiModelProperty(value = "启用状态；0->禁用；1->启用")
    private String status;
    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;
    /**
     * 是否为外链（1是 0否）
     */
    @ApiModelProperty(value = "是否为外链（1是 0否）")
    private String outerLink;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date createTime;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @TableField(exist = false)
    private List<?> list;

    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
}
