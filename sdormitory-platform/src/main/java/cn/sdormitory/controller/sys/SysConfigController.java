package cn.sdormitory.controller.sys;

import cn.sdormitory.common.annotation.SysLog;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.enums.BusinessType;
import cn.sdormitory.sys.entity.SysConfig;
import cn.sdormitory.sys.service.SysConfigService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ClassName: SysConfigController
 * Description: 系统配置信息表/枚举值表
 */
@RestController
@Api(tags = "Sys-config => 系统配置信息")
@RequestMapping("/sys/config")
public class SysConfigController {
    @Resource
    private SysConfigService sysConfigService;

    @ApiOperation("list => 根据关键字获取配置列表")
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SysConfig>> list(SysConfig sysConfig,
                                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage<SysConfig> page = sysConfigService.getPage(sysConfig, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("{id} => 获取系统配置信息")
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{id}")
    public CommonResult<SysConfig> getInfo(@PathVariable Long id) {
        return CommonResult.success(sysConfigService.selectById(id));
    }

    @ApiOperation("create => 新建配置参数")
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @SysLog(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/create")
    public CommonResult<Integer> create(@RequestBody SysConfig sysConfig) {
        int count = sysConfigService.create(sysConfig);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("update/status/{id} => 修改配置参数状态")
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @SysLog(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/status/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, String status) {
        int count = sysConfigService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("update/{id} => 修改指定配置参数")
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @SysLog(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, @RequestBody SysConfig sysConfig) {
        int count = sysConfigService.update(id, sysConfig);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("deleteByIds/{ids} => 删除指定配置参数")
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @SysLog(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/deleteByIds/{ids}")
    public CommonResult<Integer> deleteByIds(@PathVariable Long[] ids) {
        int count = sysConfigService.deleteByIds(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("export => 按条件导出（不分页）")
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @SysLog(title = "参数管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public CommonResult export(HttpServletResponse response, SysConfig sysConfig) {
        List<SysConfig> list = sysConfigService.getExportList(sysConfig);
        try {
            EasyExcel.write(response.getOutputStream(), SysConfig.class).sheet("参数管理").doWrite(list);
            return CommonResult.success();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonResult.failed();

    }
}

