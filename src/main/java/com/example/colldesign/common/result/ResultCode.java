package com.example.colldesign.common.result;

public interface ResultCode {

    /**
     * 操作是否成功
     *
     * @return 成功-true 失败-false
     */
    boolean success();

    /**
     * 操作代码
     *
     * @return int
     */
    int code();

    /**
     * 提示信息
     *
     * @return String
     */
    String msg();
}
