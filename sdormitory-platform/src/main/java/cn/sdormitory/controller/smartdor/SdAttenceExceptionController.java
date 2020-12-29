package cn.sdormitory.controller.smartdor;

import cn.sdormitory.basedata.entity.BClass;
import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.basedata.service.BClassService;
import cn.sdormitory.basedata.service.BStudentService;
import cn.sdormitory.common.annotation.SysLog;
import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.enums.BusinessType;
import cn.sdormitory.smartdor.entity.SdAttenceException;
import cn.sdormitory.smartdor.service.SdAttenceExceptionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/17 17:20
 * @version：V1.0
 */
@RestController
@Api(tags = "Smartdor-exception=> 考勤异常处理")
@RequestMapping("/smartdor/exception")
public class SdAttenceExceptionController {
    @Autowired
    private SdAttenceExceptionService sdAttenceExceptionService;
    @Autowired
    private BStudentService bStudentService;
    @Autowired
    private BClassService bClassService;

    @ApiOperation("list => 获取考勤异常处理列表")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:list')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<SdAttenceException>> list(@RequestParam Map<String, Object> params) {
        List<SdAttenceException> attenceExceptionList=sdAttenceExceptionService.getAttenceExceptionList((String)params.get("studentNo"),(String)params.get("studentName"),(String)params.get("className"),(String)params.get("absenceProcessStatus"),(String)params.get("status"),Integer.parseInt((String)params.get("pageNum")),Integer.parseInt((String)params.get("pageSize")));
        IPage<SdAttenceException> page = sdAttenceExceptionService.getPage(params);
        page.setRecords(attenceExceptionList);
        return CommonResult.success(CommonPage.restPage(page));
    }

    @ApiOperation("/info/{id} =>考勤异常信息")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:query')")
    @GetMapping("/info/{id}")
    public CommonResult<SdAttenceException> info(@PathVariable("id") Long id) {
        return CommonResult.success(sdAttenceExceptionService.getAttenceExceptionById(id));
    }

    @ApiOperation("create => 新建考勤异常处理")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:add')")
    @SysLog(title = "考勤异常处理", businessType = BusinessType.INSERT)
    @PostMapping(value = "/create")
    public CommonResult<Integer> create(@RequestBody SdAttenceException sdAttenceException,Principal principal) {
        //判断学号是否存在
        BStudent bStudent=bStudentService.getByStudentNo(sdAttenceException.getStudentNo());
        if("".equals(bStudent) || bStudent==null){
            return CommonResult.failed("此学号不存在!!!");
        }
        //判断学号+学生姓名+班级是否匹配

        BStudent student=bStudentService.getByStuNoAndNameAndClassId(sdAttenceException.getStudentNo(),sdAttenceException.getStudentName(),sdAttenceException.getClassId());
        if(student==null){
            return CommonResult.failed("输入的学号、学生姓名、班级不匹配!!!");
        }

        if("".equals(sdAttenceException.getStudentName()) || sdAttenceException.getStudentName()==null){
            sdAttenceException.setStudentName(bStudent.getStudentName());
        }
        if("".equals(sdAttenceException.getClassId()) || sdAttenceException.getClassId()==null){
            sdAttenceException.setClassId(bStudent.getClassId());
            sdAttenceException.setClassName(bStudent.getClassName());
        }

        if(!"".equals(bStudent) && bStudent!=null){
            //每个学员每天应只有一条考勤异常处理记录
            SdAttenceException exceptionInfo = sdAttenceExceptionService.getByStuNoAndDate(sdAttenceException.getStudentNo());
            if (exceptionInfo != null && !Objects.equals(exceptionInfo.getId(), sdAttenceException.getId())) {
                return CommonResult.failed("该学生当天考勤异常已处理!!!");
            }
            //根据班级ID查询班级名称
            BClass bClass=bClassService.getBClassById(sdAttenceException.getClassId());
            sdAttenceException.setClassName(bClass.getClassName());
            sdAttenceException.setProcessPerson(principal.getName());
            int count = sdAttenceExceptionService.create(sdAttenceException);
            if (count > 0) {
                return CommonResult.success(count);
            }
            return CommonResult.failed();
        }else{
            return CommonResult.failed("该学号不存在!!!");
        }


    }

    @ApiOperation("update/{id} => 修改考勤异常处理")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:edit')")
    @SysLog(title = "考勤异常处理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, @RequestBody SdAttenceException sdAttenceException) {
        //判断学号是否存在
        BStudent bStudent=bStudentService.getByStudentNo(sdAttenceException.getStudentNo());
        if("".equals(bStudent) || bStudent==null){
            return CommonResult.failed("此学号不存在!!!");
        }
        //判断学号+学生姓名+班级是否匹配

        BStudent student=bStudentService.getByStuNoAndNameAndClassId(sdAttenceException.getStudentNo(),sdAttenceException.getStudentName(),sdAttenceException.getClassId());
        if(student==null){
            return CommonResult.failed("输入的学生信息(学号、姓名、班级)不匹配!!!");
        }

        if("".equals(sdAttenceException.getStudentName()) || sdAttenceException.getStudentName()==null){
            sdAttenceException.setStudentName(bStudent.getStudentName());
        }
        if("".equals(sdAttenceException.getClassId()) || sdAttenceException.getClassId()==null){
            sdAttenceException.setClassId(bStudent.getClassId());
            sdAttenceException.setClassName(bStudent.getClassName());
        }
        if(!"".equals(bStudent) && bStudent!=null){
            SdAttenceException exceptionInfo = sdAttenceExceptionService.getByStuNoAndDate(sdAttenceException.getStudentNo());
            if (exceptionInfo != null && !Objects.equals(exceptionInfo.getId(), sdAttenceException.getId())) {
                return CommonResult.failed("该学生当天考勤异常已处理!!!");
            }
            //根据班级ID查询班级名称
            BClass bClass=bClassService.getBClassById(sdAttenceException.getClassId());
            sdAttenceException.setClassName(bClass.getClassName());
            int count = sdAttenceExceptionService.update(id, sdAttenceException);
            if (count > 0) {
                return CommonResult.success(count);
            }
            return CommonResult.failed();
        }else{
            return CommonResult.failed("该学号不存在!!!");
        }
    }

    @ApiOperation("deleteByIds/{ids} => 删除指定考勤异常处理")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:remove')")
    @SysLog(title = "考勤异常处理", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/deleteByIds/{ids}")
    public CommonResult<Integer> deleteByIds(@PathVariable Long[] ids) {
        int count = sdAttenceExceptionService.deleteByIds(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("update/status/{id} => 修改考勤异常处理")
    @PreAuthorize("@ss.hasPermi('smartdor:exception:edit')")
    @SysLog(title = "考勤异常处理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update/status/{id}")
    public CommonResult<Integer> update(@PathVariable Long id, String status) {
        int count = sdAttenceExceptionService.updateStatus(id, status);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
