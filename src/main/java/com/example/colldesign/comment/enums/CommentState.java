package com.example.colldesign.comment.enums;


public enum CommentState {

    CREATE(0),
    RESOLV(1),
    REOPEN(2);

    private Integer state;

    CommentState(Integer state) {
        this.state = state;
    }
}
