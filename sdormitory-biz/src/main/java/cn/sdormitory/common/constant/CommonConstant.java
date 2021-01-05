package cn.sdormitory.common.constant;


/**
 * 公共常量
 */
public class CommonConstant {

    public static final String AJAX_REQUEST_HEADER_KEY = "x-rjft-request";

    public static final String AJAX_REQUEST_HEADER_VALUE = "ajax";

    public static final String AJAX_REQUEST_TOKEN_KEY = "Authorization";

    /**
     * app原生页请求
     */
    public static final String AJAX_NATIVE_HEADER_KEY = "x-rjft-native";

    public static final String AJAX_NATIVE_HEADER_VALUE = "native";


    /**
     * 超级管理员ID
     */
    public static final long SUPER_ADMIN_ID = 1L;

    /**
     * 系统菜单最大id
     */
    public static final int SYS_MENU_MAX_ID = 1;
    /**
     * 默认角色
     */
    public static final long DEFAULT_ROLE = 1L;

    /**
     * 匿名用户
     */
    public static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 订单生成规则
     */
    public static final String NUMBER_GEN_KEY_ORDER = "number_gen_key_order";

    /**
     * 退货单生成规则
     */
    public static final String NUMBER_GEN_KEY_ORDER_RETURN = "number_gen_key_order_return";

    /**
     * 业务类型前缀 【支付宝支付唯一编码前缀】
     */
    public static final String ALI_PAY_PREFIX = "ALI";
    /**
     * 业务类型前缀 【微信支付唯一编码前缀】
     */
    public static final String WEIXIN_PAY_PREFIX = "WX";


    /** 数据权限过滤 */
    public static final String SQL_FILTER = "sql_filter";

    /** 有效状态 */
    public static final String VALID_STATUS = "1";

    /** 总扣分 */
    public static final double TOTALPDEDUCT_VALUE = 0;

    /** 总得分 */
    public static final double TOTALSCORE_VALUE = 100;

    /** 每天考勤时间*/
    public static final String ATTENDANCE_TIME ="22:00:00";

    /** 考勤状态:1正常、2缺勤、3晚归、4请假*/
    public static final String ATTENDANCE_NOMAL ="1";

    public static final String ATTENDANCE_ABSENCE ="2";

    public static final String ATTENDANCE_COMEBACKLATE ="3";

    public static final String ATTENDANCE_LEAVE ="4";


    /**
     * 短信模换板中需要替的字符串
     */
    public static final String SMS_TEMPLATE_STR = "{student}";

    /**
     * 考勤时间整数
     */
    public static final int ATTENDANCE_TIME_INT = 22;

    /**短信模板类型----->考勤异常*/
    public static final int SMS_TEMPLATE_TYPE_ATTENCE=0;

    /**短信模板类型----->申请请假*/
    public static final int SMS_TEMPLATE_TYPE_LEAVE=1;

    /**短信模板类型----->申请请假通过*/
    public static final int SMS_TEMPLATE_TYPE_LEAVE_PASS=3;

    /**考勤规则类型----->正常1*/
    public static final String ATTENCE_NOMAL="1";

    /**请假状态----->家长确认中1*/
    public static final String PARENT_CONFIRE="1";

    /**请假状态----->家长确认通过2*/
    public static final String PARENT_CONFIRE_PASS="2";

    /**请假状态----->班主任确认通过2*/
    public static final String TEACHER_CONFIRE_PASS="3";

    /**
     * 考勤时间整数22:00:00
     */
    public static final String ATTENDANCE_DATE = "22:00:00";

    /**
     * APP端token前缀(用户：appuser、学员：appstudent、家长：appparent)
     */
    public static final String TOKEN_USER = "appuser";
    public static final String TOKEN_STUDENT = "appstudent";
    public static final String TOKEN_PARENT = "appparent";

    //请假申请初始状态为家长确认中
    public static final String  INIT_LEAVE_STATUS= "1";

}
