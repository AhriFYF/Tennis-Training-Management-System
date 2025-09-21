package com.wms.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    private int code; // 编码 200/400
    private String msg; // 成功/失败
    private Long total; // 总记录数
    private Object data; // 数据
    private Map<String, Object> extraData; // 额外数据存储

    public Result() {
        this.extraData = new HashMap<>();
    }

    // 快速创建方法
    public static Result fail() {
        return result(400, "失败", 0L, null);
    }

    public static Result fail(String msg) {
        return result(400, msg, 0L, null);
    }

    public static Result suc() {
        return result(200, "成功", 0L, null);
    }

    public static Result suc(Object data) {
        return result(200, "成功", 0L, data);
    }

    public static Result suc(Object data, Long total) {
        return result(200, "成功", total, data);
    }

    private static Result result(int code, String msg, Long total, Object data) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        res.setData(data);
        return res;
    }

    // 添加额外数据的方法
    public Result put(String key, Object value) {
        this.extraData.put(key, value);
        return this;
    }

    // 合并数据到data字段
    public Result combineToData() {
        if (this.data != null) {
            this.extraData.put("data", this.data);
        }
        this.data = this.extraData;
        this.extraData = new HashMap<>();
        return this;
    }
}