package cn.sdormitory.report.vo;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/5 8:52
 * @version：V1.0
 */
public class HygieneStatisticsVO {
    //卫生统计按月统计(传入年月)
    private String inYearMonth;
    //宿舍ID
    private Long bdormitoryId;
    //宿舍栋号
    private String buildingNo;
    //楼层
    private String storey;
    //宿舍号
    private String dormitoryNo;
    //宿舍卫生平均总得分
    private String avgScore;

    public String getInYearMonth() {
        return inYearMonth;
    }

    public void setInYearMonth(String inYearMonth) {
        this.inYearMonth = inYearMonth;
    }

    public Long getBdormitoryId() {
        return bdormitoryId;
    }

    public void setBdormitoryId(Long bdormitoryId) {
        this.bdormitoryId = bdormitoryId;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getStorey() {
        return storey;
    }

    public void setStorey(String storey) {
        this.storey = storey;
    }

    public String getDormitoryNo() {
        return dormitoryNo;
    }

    public void setDormitoryNo(String dormitoryNo) {
        this.dormitoryNo = dormitoryNo;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }
}
