package cn.sdormitory.controller.report;

import cn.sdormitory.common.api.CommonPage;
import cn.sdormitory.common.api.CommonResult;
import cn.sdormitory.report.service.HygieneStatisticsService;
import cn.sdormitory.report.vo.HygieneStatisticsVO;
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
 * @创建时间：2020/12/5 12:31
 * @version：V1.0
 */
@RestController
@Api(tags = "Report-HygieneStatistics=> 优秀宿舍统计")
@RequestMapping("/report/hygienestatistics")
public class HygieneStatisticsController {
    @Autowired
    private HygieneStatisticsService hygieneStatisticsService;

    @ApiOperation("list => 查询优秀宿舍统计列表")
    @PreAuthorize("@ss.hasPermi('report:hygienestatistics:list')")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<HygieneStatisticsVO>> list(@RequestParam Map<String, Object> params) {
        String yearMonth=(String)params.get("inYearMonth");
        String inYearMonth="";
        if("".equals(yearMonth) || yearMonth==null){
            Date date = new Date();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
            String date1=fmt.format(date);
            inYearMonth=date1;
        }else{
            inYearMonth=yearMonth;
        }
        List<HygieneStatisticsVO> hygieneStatisticsVOList = hygieneStatisticsService.getHygieneStaList(inYearMonth,Integer.parseInt((String)params.get("pageNum")),Integer.parseInt((String)params.get("pageSize")));
        if(hygieneStatisticsVOList!=null && hygieneStatisticsVOList.size()>0){
            for (HygieneStatisticsVO hygieneStatisticsVO:hygieneStatisticsVOList){
                hygieneStatisticsVO.setInYearMonth(inYearMonth);
            }
        }
        CommonPage<HygieneStatisticsVO> commonPage = new CommonPage<HygieneStatisticsVO>();
        commonPage.setList(hygieneStatisticsVOList);
        commonPage.setTotal((long) hygieneStatisticsVOList.size());
        return CommonResult.success(commonPage);

    }
}
