package com.example.colldesign.common.result;


/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(true,200, "操作成功"),
    FAILED(false,500, "操作失败"),
    VALIDATE_FAILED(false,404, "参数检验失败"),
    UNAUTHORIZED(false,401, "暂未登录或token已经过期"),
    AUTHORIZATION_HEADER_IS_EMPTY(false,600,"请求头中的token为空"),
    GET_TOKEN_KEY_ERROR(false,601,"远程获取TokenKey异常"),
    GEN_PUBLIC_KEY_ERROR(false,602,"生成公钥异常"),
    JWT_TOKEN_EXPIRE(false,603,"token校验异常"),
    TOMANY_REQUEST_ERROR(false,429,"后端服务触发流控"),
    BACKGROUD_DEGRADE_ERROR(false,604,"后端服务触发降级"),
    BAD_GATEWAY(false,502,"网关服务异常"),
    FORBIDDEN(false,403, "没有相关权限");

    private boolean success;
    private long code;
    private String message;


    ResultCode(boolean success, long code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
