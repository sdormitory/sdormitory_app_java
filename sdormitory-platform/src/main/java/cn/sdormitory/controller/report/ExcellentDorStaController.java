package cn.sdormitory.controller.report;

import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.report.service.ExcellentDorStaService;
import cn.sdormitory.report.vo.ExcellentDorStaVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/4 22:38
 * @version：V1.0
 */
@RestController
@Api(tags = "Report-ExcellentDorSta=> 优秀宿舍统计")
@RequestMapping("/report/excellentdorsta")
public class ExcellentDorStaController {
    @Autowired
    private ExcellentDorStaService excellentDorStaService;

    @ApiOperation("list => 查询优秀宿舍统计列表")
    @PreAuthorize("@ss.hasPermi('report:excellentdorsta:list')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<ExcellentDorStaVO>> list(@RequestParam Map<String, Object> params) {
        String inMonth=(String)params.get("inMonth");
        String incomMonth="";
        if("".equals(inMonth) || inMonth==null){
            Date date = new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
            String date1=fmt.format(date);
            incomMonth=date1;
        }else{
            incomMonth=inMonth;
        }
        List<ExcellentDorStaVO> excellentDorStaVOList = excellentDorStaService.getExcDorStaList(incomMonth,Integer.parseInt((String)params.get("pageNum")),Integer.parseInt((String)params.get("pageSize")));
        CommonPage<ExcellentDorStaVO> commonPage = new CommonPage<ExcellentDorStaVO>();
        commonPage.setList(excellentDorStaVOList);
        commonPage.setTotal((long) excellentDorStaVOList.size());
        return CommonResult.success(commonPage);

    }


}
