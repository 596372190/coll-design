package com.example.colldesign.common.exceptionHandler.exception;

public class UserNotExistException extends Exception {
    String mes;

    public UserNotExistException(String mes) {
        this.mes = mes;
    }

    public UserNotExistException() {
        this.mes = "该用户不存在";
    }


    @Override
    public String getMessage() {
        return mes;
    }
}
