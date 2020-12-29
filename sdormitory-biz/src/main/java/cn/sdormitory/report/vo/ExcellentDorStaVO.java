package cn.sdormitory.report.vo;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/4 22:15
 * @version：V1.0
 */
public class ExcellentDorStaVO {
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
    //宿舍正常考勤平均率
    private String avgNormalAttenceRate;
    //优秀宿舍按月统计(传入年月)
    private String inMonth;
    //最终排名
    private String ranking;

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

    public String getAvgNormalAttenceRate() {
        return avgNormalAttenceRate;
    }

    public void setAvgNormalAttenceRate(String avgNormalAttenceRate) {
        this.avgNormalAttenceRate = avgNormalAttenceRate;
    }

    public String getInMonth() {
        return inMonth;
    }

    public void setInMonth(String inMonth) {
        this.inMonth = inMonth;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
