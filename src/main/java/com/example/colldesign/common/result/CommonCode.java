package com.example.colldesign.common.result;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode {

    SUCCESS(true, 0, "操作成功！"),
    FAIL(false, 1, "操作失败！"),
    NO_LOGIN(false, 101, "此操作需要登陆系统！"),
    NO_AUTH(false, 102, "权限不足，无权操作！"),
    INVALID_PARAM(false, 103, "参数异常！"),
    SERVER_ERROR(false, 500, "系统繁忙，请刷新或联系管理员！");

    // private static ImmutableMap<Integer, CommonCode> codes;

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String msg;

    private CommonCode(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }

}
