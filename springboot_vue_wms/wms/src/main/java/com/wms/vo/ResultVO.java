package com.wms.vo;

/**
 * 统一返回结果封装类
 */
public class ResultVO {
    private Integer code;
    private String msg;
    private Object data;

    public ResultVO() {}

    public ResultVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功静态方法
    public static ResultVO success(String msg) {
        return new ResultVO(200, msg, null);
    }

    public static ResultVO success(String msg, Object data) {
        return new ResultVO(200, msg, data);
    }

    // 错误静态方法
    public static ResultVO error(Integer code, String msg) {
        return new ResultVO(code, msg, null);
    }

    // getter和setter
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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