package cn.sdormitory.controller.test;

import cn.sdormitory.common.annotation.IgnoreAuth;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.common.utils.PropertiesUtils;
import cn.sdormitory.oss.vo.OssPolicyResult;
import cn.sdormitory.request.HttpRequest;
import cn.sdormitory.sys.entity.SysDict;
import cn.sdormitory.sys.service.SysDictService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ClassName: TestController
 * Description:
 *
 * @author honghh
 * Date 2020/05/18 16:09
 * Copyright (C) 洛阳乾发供应链管理有限公司
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private SysDictService sysDictService;
    @IgnoreAuth
    @GetMapping(value = "/hello")
    public CommonResult<OssPolicyResult> hello() {

        return CommonResult.success();
    }
    @IgnoreAuth
    @GetMapping("/export")
    public void export(HttpServletResponse response, SysDict sysDict) {
        IPage<SysDict> page = sysDictService.getPage(sysDict, 10, 1);
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try {
            fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), SysDict.class).sheet("模板").doWrite(page.getRecords());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args){
//        double timea=1606397068.0;
//        long times = new Double(timea).longValue();
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTimeInMillis(times * 1000);
//        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.format(gc.getTime()));
//    }


    public  static void main(String[] args){
//        String url="http://192.168.0.104:9003/report/originalrecord/setRecordCallback";
//        List pairs = new ArrayList();
//        pairs.add(new BasicNameValuePair("key","abc"));
//        pairs.add(new BasicNameValuePair("url",url));
//        String object = HttpRequest.sendPost1("http://192.168.0.103:8088/setRecordCallback",pairs);
//        System.out.println(object);

//        List pairs = new ArrayList();
//        pairs.add(new BasicNameValuePair("key","abc"));
//        pairs.add(new BasicNameValuePair("id","T256009"));
//        String object = HttpRequest.sendPost1("http://192.168.0.103:8088/removePerson",pairs);
//        System.out.println(object);

       // System.out.println( new Date().getHours());




    }
}
