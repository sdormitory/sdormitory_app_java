package cn.sdormitory.controller;

import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.common.constant.CommonConstant;
import cn.sdormitory.common.dto.ResultMsg;
import cn.sdormitory.service.AppStudentService;
import cn.sdormitory.service.AppUserService;
import cn.sdormitory.sys.entity.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/28 11:02
 * @version：V1.0
 */
@RestController
@RequestMapping("/app/Account")
@Api("APP端个人中心接口")
public class AppAccountController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppStudentService appStudentService;

    @ApiOperation(value = "个人信息")
    @GetMapping("/getAccountById")
    public ResultMsg getAccountById(int id,String tokenPrefix) {
        if(!"".equals(tokenPrefix) && tokenPrefix != null){
            if(tokenPrefix.equals("appuser")){
                SysUser sysUser = appUserService.getUserById(id);
                return  ResultMsg.BY_SUCCESS("获取成功",sysUser);
            }else{
                BStudent bStudent=appStudentService.getStudentById(id);
                return  ResultMsg.BY_SUCCESS("获取成功",bStudent);
            }
        }else{
            return  ResultMsg.BY_ERROR("token不能为空!!!");
        }

    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassord")
    public ResultMsg updatePassword(int id , String oldPassword , String newPassword,String tokenPrefix){
        if(!"".equals(tokenPrefix) && tokenPrefix != null){
            if(tokenPrefix.equals(CommonConstant.TOKEN_USER)){
                if(appUserService.updatePassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }else if(tokenPrefix.equals(CommonConstant.TOKEN_STUDENT)){
                if(appStudentService.updateStuPassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }else{
                if(appStudentService.updateParPassword(id,oldPassword,newPassword)){
                    return ResultMsg.BY_SUCCESS("修改成功","");
                }else{
                    return ResultMsg.BY_FAIL("修改失败，请检查密码!!!");
                }
            }

        }else{
            return ResultMsg.BY_FAIL("失败!!!");
        }

    }
}
