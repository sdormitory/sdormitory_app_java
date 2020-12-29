package cn.sdormitory.controller.smartdor;


import cn.sdormitory.common.annotation.IgnoreAuth;
import cn.sdormitory.common.annotation.SysLog;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.enums.BusinessType;
import cn.sdormitory.smartdor.entity.SdAttence;
import cn.sdormitory.smartdor.entity.SdAttenceException;
import cn.sdormitory.smartdor.entity.SdLeave;
import cn.sdormitory.smartdor.service.SdAttenceExceptionService;
import cn.sdormitory.smartdor.service.SdAttenceService;
import cn.sdormitory.smartdor.service.SdLeaveService;
import cn.sdormitory.smartdor.vo.DormitoryAttenceVo;
import cn.sdormitory.smartdor.vo.SdAttenceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/8 23:39
 * @version：V1.0
 */
@RestController
@Api(tags = "Smartdor-sdAttence=> 考勤管理")
@RequestMapping("/smartdor/sdAttence")
public class SdAttenceController {
    @Autowired
    private SdAttenceService sdAttenceService;

    @Autowired
    private SdLeaveService sdLeaveService;

    @Autowired
    private SdAttenceExceptionService sdAttenceExceptionService;


    @ApiOperation("list => 查询考勤人员列表")
    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:query')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SdAttence>> list(@RequestParam Map<String, Object> params) throws ParseException {
        return CommonResult.success(sdAttenceService.getPage(params));
    }


    /**
     * 创建考勤信息信息
     * @throws ParseException
     */
    @IgnoreAuth
    @ApiOperation("=> 创建考勤信息")
    //@PreAuthorize("@ss.hasPermi('smartdor:sdattence:add')")
    @PostMapping("/setRecordCallback")
    public void setRecordCallback() throws ParseException {
        sdAttenceService.create();
    }


    @ApiOperation("deleteByIds/{ids} => 删除考勤信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:remove')")
    @SysLog(title = "删除考勤信息", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/deleteByIds/{ids}")
    public CommonResult<Integer> deleteByIds(@PathVariable String[] ids) {
        int count = sdAttenceService.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    /**
     * 定时考勤
     */
    @Scheduled(cron = "0 0 22 * * ? ")
    public void attendance() throws ParseException {
        sdAttenceService.create();
    }

    @IgnoreAuth
    @ApiOperation("listAbsenceStudent => 查询缺勤人员列表")
//    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:query')")
    @GetMapping(value = "/listAbsenceStudent")
    public CommonResult<CommonPage<SdAttenceVo>> listAbsenceStudent(@RequestParam Map<String,Object> map)  {
        return CommonResult.success(sdAttenceService.listAbsenceStudent(map));
    }


    @IgnoreAuth
    @ApiOperation("listAbsenceDormitory => 查询宿舍考勤信息列表")
//    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:query')")
    @GetMapping(value = "/listAbsenceDormitory")
    public CommonResult<CommonPage<DormitoryAttenceVo>> listAbsenceDormitory(@RequestParam Map<String,Object> map)  {
        return CommonResult.success(sdAttenceService.listAbsenceDormitory(map));
    }



    @ApiOperation("queryleave => 查询考勤数据对应请假信息")
    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:query')")
    @GetMapping(value = "/queryleave")
    public CommonResult<SdLeave> queryleave(@RequestParam Map<String, Object> params) throws ParseException {
        String studentNo=(String)params.get("studentNo");
        String leaveDate=(String) params.get("leaveDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date leaDateSdf=sdf.parse(leaveDate);
        String leaDate=sdf.format(leaDateSdf);
        SdLeave sdLeave = sdLeaveService.getLeaveByStuNoAndLeaDate(studentNo,leaDate);
        return CommonResult.success(sdLeave);
    }


    @ApiOperation("queryexception => 查询考勤数据对应异常处理信息(晚归/缺勤)")
    @PreAuthorize("@ss.hasPermi('smartdor:sdattence:query')")
    @GetMapping(value = "/queryexception")
    public CommonResult<SdAttenceException> queryexception(@RequestParam Map<String, Object> params) throws ParseException {
        String studentNo=(String)params.get("studentNo");
        String createTime=(String) params.get("createTime");
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = fmt.parse(createTime);

        SdAttenceException sdAttenceException = sdAttenceExceptionService.getExcByStuNoAndCreTime(studentNo,date);
        return CommonResult.success(sdAttenceException);
    }
}
