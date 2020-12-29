package cn.sdormitory.controller;

import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.dto.ResultMsg;
import cn.sdormitory.service.AppLeaveService;
import cn.sdormitory.service.AppStudentService;
import cn.sdormitory.service.AppUserService;
import cn.sdormitory.smartdor.entity.SdLeave;
import cn.sdormitory.sys.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 17:07
 * @version：V1.0
 */
@RestController
@RequestMapping("/app/Leave")
@Api("APP端请假管理接口")
public class AppLeaveController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppStudentService appStudentService;
    @Autowired
    private AppLeaveService appLeaveService;

    @ApiOperation(value = "请假列表")
    @GetMapping("/getLeaveByAccId")
    public ResultMsg getLeaveByAccId(int accountId, String tokenPrefix) {
        if(!"".equals(tokenPrefix) && tokenPrefix != null){
            if(tokenPrefix.equals(CommonConstant.TOKEN_USER)){
                List<SdLeave> leaveList=appLeaveService.getLeaveAll();
                return  ResultMsg.BY_SUCCESS("获取成功",leaveList);
            }else{
                BStudent bStudent=appStudentService.getStudentById(accountId);
                List<SdLeave> leaveList=appLeaveService.getLeaveByStuNo(bStudent.getStudentNo());
                return  ResultMsg.BY_SUCCESS("获取成功",leaveList);
            }
        }else{
            return  ResultMsg.BY_ERROR("token不能为空!!!");
        }
    }

    //根据请假ID获取请假详情
    @ApiOperation(value = "请假详情")
    @GetMapping("/getLeaveById")
    public ResultMsg getLeaveById(Long id){
        SdLeave sdLeave = appLeaveService.getLeaveById(id);
        if(sdLeave != null){
            return ResultMsg.BY_SUCCESS("查询请假详情成功", sdLeave);
        }else{
            return ResultMsg.BY_FAIL("查询请假详情失败");
        }
    }
}
