package cn.sdormitory.controller.smartdor;

import cn.sdormitory.common.annotation.IgnoreAuth;
import cn.sdormitory.common.annotation.SysLog;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.enums.BusinessType;
import cn.sdormitory.smartdor.entity.SdDevice;
import cn.sdormitory.smartdor.service.SdDeviceService;
import cn.sdormitory.sysset.entity.SyssetAttenceRule;
import cn.sdormitory.sysset.service.SyssetAttenceRuleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


/**
 * @创建人：zhouyang
 * @创建时间：2020/12/7 13:29
 * @version：V1.0
 */
@RestController
@Api(tags = "Smartdor-sddevice=> 设备管理")
@RequestMapping("/smartdor/sddevice")
public class SdDeviceController {

    @Autowired
    private SdDeviceService sdDeviceService;
    @Autowired
    private SyssetAttenceRuleService syssetAttenceRuleService;

    @ApiOperation("list => 查询设备列表信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:list')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SdDevice>> list(@RequestParam Map<String, Object> params) {
        IPage<SdDevice> page = sdDeviceService.getPage(params);

        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("info/{id} => 设备信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:query')")
    @GetMapping("/info/{id}")
    public CommonResult<SdDevice> info(@PathVariable("id") Long id) {
        SdDevice sdDevice = sdDeviceService.getSdDeviceById(id);

        if(sdDevice == null){
            return CommonResult.failed("操作失败!未能获取到设备信息");
        }

        return CommonResult.success(sdDevice);
    }

    @ApiOperation("getinfo/{deviceIpAddress} => 设备信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:query')")
    @GetMapping("/getinfo")
    public CommonResult<JSONObject> getInfo() {
        String result = sdDeviceService.getDeviceInfo();
        return CommonResult.success(JSONObject.fromObject(result));
    }



    @ApiOperation("update/{id} => 修改指定设备信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:edit')")
    @SysLog(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@PathVariable("id") Long id,@RequestBody SdDevice sdDevice) {
        //如果考勤规则不为空则根据考勤规则ID查询对应的考勤规则名称
        if(!"".equals(sdDevice.getAttenceRuleId()) && sdDevice.getAttenceRuleId()>0){
            SyssetAttenceRule syssetAttenceRule=syssetAttenceRuleService.getSyssetAttenceRuleById(sdDevice.getAttenceRuleId());
            sdDevice.setAttenceRuleName(syssetAttenceRule.getAttenceRuleName());
        }
        int count = sdDeviceService.update(sdDevice);

        if (count > 0) {
            sdDeviceService.setDeviceInfo(sdDevice);
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("create => 创建设备信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:add')")
    @SysLog(title = "设备管理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/create")
    public CommonResult<Integer> create(@RequestBody SdDevice sdDevice, Principal principal) {

        if(sdDeviceService.getSdDeviceByIP(sdDevice.getDeviceIpAddress())) {
            //如果考勤规则不为空则根据考勤规则ID查询对应的考勤规则名称
            if(sdDevice.getAttenceRuleId()!=null && sdDevice.getAttenceRuleId()>0 ){
                SyssetAttenceRule syssetAttenceRule=syssetAttenceRuleService.getSyssetAttenceRuleById(sdDevice.getAttenceRuleId());
                sdDevice.setAttenceRuleName(syssetAttenceRule.getAttenceRuleName());
            }
            int count = sdDeviceService.create(sdDevice);
            //sdDeviceService.update(sdDevice);
            sdDeviceService.setDeviceInfo(sdDevice);

            return CommonResult.success(count);
        } else {
            return CommonResult.failed("该IP已经存在不可重复添加");
        }
    }

    @ApiOperation("update/status/{id} => 修改设备状态")
    @PreAuthorize("@ss.hasPermi('basedata:bclass:edit')")
    @SysLog(title = "设备管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/status/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, String status) {
        int count = sdDeviceService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("getAttenceRuleList => 获取有效的正常考勤规则信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sddevice:query')")
    @GetMapping("/getAttenceRuleList")
    public CommonResult<List> getAttenceRuleList() {
        List<SyssetAttenceRule> list = syssetAttenceRuleService.getNomalListAll();
        return CommonResult.success(list);
    }

}
