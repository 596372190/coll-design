package com.example.colldesign.common.result;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
public class ApiResult<T> implements Response, Serializable {

    /**
     * 操作是否成功
     */
    boolean success = SUCCESS;

    /**
     * 操作代码
     */
    int code = SUCCESS_CODE;

    /**
     * 提示信息
     */
    String msg;

    /**
     * 额外信息
     */
    T data;

    /**
     * 额外信息，可使用add方法链式拼接数据
     */
    Map<String, Object> extend = Maps.newHashMap();

    public ApiResult add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public ApiResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public ApiResult(ResultCode resultCode, T data) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        this.data = data;
    }

    public static ApiResult SUCCESS() {
        return new ApiResult<>(CommonCode.SUCCESS);
    }

    public static <T> ApiResult<T> SUCCESS(T data) {
        return new ApiResult<>(CommonCode.SUCCESS, data);
    }

    public static ApiResult FAIL() {
        return new ApiResult<>(CommonCode.FAIL);
    }

    public static <T> ApiResult<T> FAIL(T data) {
        return new ApiResult<>(CommonCode.FAIL, data);
    }

    public ApiResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
