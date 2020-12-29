package cn.sdormitory.common.exption;


import cn.sdormitory.common.api.IErrorCode;

/**
 * ClassName: KaptchaEnum
 */
public enum KaptchaEnum implements IErrorCode {
    /**
     * 参数不可以为空
     */
    UUID_NOT_NULL(90100, "uuid不能为空"),
    MSG_CODE_ERROR(90101, "验证码错误"),
    ;

    private long code;
    private String msg;

    KaptchaEnum(long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
