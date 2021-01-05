package cn.sdormitory.common.dto;

import java.io.Serializable;

/**
 * @创建人：zhouyang
 * @创建时间：2020/9/12 12:56
 * @version：V1.0
 */
public class ResultMsg implements Serializable {
    private final static Integer EX_SUCCESS=200;//成功

    private final static Integer EX_ERROR=400;//错误

    private final static Integer EX_FAIL=300;//失败

    private final static Integer EX_NULL=100;//空值


    private Integer status;//状态码

    private String msg;//消息

    private Object data;//数据

    public ResultMsg(){

    }

    public ResultMsg(int status, String msg, Object data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }

    public ResultMsg(String msg, Object data){
        this.msg=msg;
        this.data=data;
    }

    public static ResultMsg BY_SUCCESS(String msg,Object data){
        return new ResultMsg(ResultMsg.EX_SUCCESS,msg,data);
    }
    public static ResultMsg BY_SUCCESS(String msg){
        return new ResultMsg(ResultMsg.EX_SUCCESS,msg,null);
    }

    public static ResultMsg BY_ERROR(String msg,Object data){
        return new ResultMsg(ResultMsg.EX_ERROR,msg,data);
    }

    public static ResultMsg BY_ERROR(String msg){
        return new ResultMsg(ResultMsg.EX_ERROR,msg,null);
    }

    public static ResultMsg BY_FAIL(String msg,Object data){
        return new ResultMsg(ResultMsg.EX_FAIL,msg,data);
    }

    public static ResultMsg BY_FAIL(String msg){
        return new ResultMsg(ResultMsg.EX_ERROR,msg,null);
    }
    public static ResultMsg BY_NULL(String msg,Object data){
        return new ResultMsg(ResultMsg.EX_NULL,msg,data);
    }

    public static ResultMsg BY_NULL(String msg){
        return new ResultMsg(ResultMsg.EX_NULL,msg,null);
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
