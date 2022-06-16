package com.example.colldesign.comment.enums;

public enum CommentType {

    MY_OPEN("1"),
    ALL_OPEN("2"),
    COMPLETED("3");

    private String type;

    CommentType(String type) {
        this.type = type;
    }
}
