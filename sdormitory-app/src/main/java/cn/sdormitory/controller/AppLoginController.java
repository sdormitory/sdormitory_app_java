package cn.sdormitory.controller;

import cn.sdormitory.common.dto.ResultMsg;
import cn.sdormitory.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/25 10:46
 * @version：V1.0
 */
@RestController
@RequestMapping("/app/User")
@Api("APP端登录接口")
public class AppLoginController {
    @Resource
    private AppUserService appUserService;

    @ApiOperation(value = "登录")
    @PostMapping("/doLogin")
    public ResultMsg doLogin(String loginName, String loginPassword, HttpServletRequest request){
        System.out.println(loginName+loginPassword+"loginController-doLogin()");
        String token = appUserService.doLogin(loginName,loginPassword,request);
        if(null != token && !"".equals(token)){
            System.out.println("token是"+token);
            return ResultMsg.BY_SUCCESS("成功",token);
        }else{
            return ResultMsg.BY_FAIL("失败");
        }
    }

}
