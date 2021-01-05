package cn.sdormitory.controller;

import cn.sdormitory.basedata.entity.BStudent;
import cn.sdormitory.common.dto.ResultMsg;
import cn.sdormitory.service.AppStudentService;
import cn.sdormitory.smartdor.entity.SdLeave;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruanteng
 * @date 2020/12/30 14:32
 */
@RestController
@RequestMapping("/app/Student")
@Api("学生操作接口")
public class AppStudentController {

    @Autowired
    private AppStudentService appStudentService;

    //根据请假ID获取学生详情
    @ApiOperation(value = "学生详细信息")
    @GetMapping("/getStudentById")
    public ResultMsg getLeaveById(int id){
        BStudent bStudent = appStudentService.getStudentById(id);
        if(bStudent != null){
            return ResultMsg.BY_SUCCESS("查询学生信息成功", bStudent);
        }else{
            return ResultMsg.BY_FAIL("查询学生信息失败");
        }
    }

}
