package cn.sdormitory.controller.smartdor;

import cn.sdormitory.basedata.vo.BStudentVo;
import cn.sdormitory.common.annotation.IgnoreAuth;
import cn.sdormitory.common.annotation.SysLog;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.enums.BusinessType;
import cn.sdormitory.smartdor.entity.OriginalRecord;
import cn.sdormitory.smartdor.entity.SdAttence;
import cn.sdormitory.smartdor.service.OriginalRecordService;
import cn.sdormitory.smartdor.service.SdAttenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;


/**
 * @创建人：zhouyang
 * @创建时间：2020/12/8 23:37
 * @version：V1.0
 */
@RestController
@Api(tags = "Smartdor-OriginalRecord=> 过闸流水")
@RequestMapping("/report/originalrecord")
public class OriginalRecordController {
    @Autowired
    private OriginalRecordService originalRecordService;
    @Autowired
    private SdAttenceService sdAttenceService;

    @ApiOperation("list => 查询过闸流水人员列表")
    @PreAuthorize("@ss.hasPermi('report:originalrecord:query')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<OriginalRecord>> list(@RequestParam Map<String, Object> params) throws ParseException {
        return CommonResult.success(originalRecordService.getPage(params));
    }

    /**
     * 创建过闸流水
     * @param vo
     * @throws ParseException
     */
    @IgnoreAuth
    @ApiOperation("=> 创建过闸流水人员信息")
    //@PreAuthorize("@ss.hasPermi('smartdor:sdattence:add')")
    @PostMapping("/setRecordCallback")
    public void setRecordCallback(BStudentVo vo) throws ParseException {
        originalRecordService.create(vo);
    }

    @ApiOperation("deleteByIds/{ids} => 删除人员流水信息（数据库）")
    @PreAuthorize("@ss.hasPermi('report:originalrecord:remove')")
    @SysLog(title = "删除人员流水信息", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/deleteByIds/{ids}")
    public CommonResult<Integer> deleteByIds(@PathVariable String[] ids) {
        int count = originalRecordService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    /**
     * 定时删除过闸流水（闸机）
     */
    @Scheduled(cron = "0 59 23 * * * ")
    public void removeRecord(){
        //统计当天缺勤学员并将数据插入到考勤表
        sdAttenceService.statisticsLackStu();
        //删除设备中当天过闸流水记录
        originalRecordService.removeRecord(System.currentTimeMillis());
    }


}
