package com.example.colldesign.comment.enums;

public enum CommentOperate {

    CREATE("create"),
    UPDATE("update"),
    RESOLVE("resolve"),
    REOPEN("reopen"),
    DELETE("delete");

    private String operate;

    CommentOperate(String operate) {
        this.operate = operate;
    }

    public String getOperate() {
        return operate;
    }
}
