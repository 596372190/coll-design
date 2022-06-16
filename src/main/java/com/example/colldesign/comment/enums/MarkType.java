package com.example.colldesign.comment.enums;

public enum MarkType {

    FEATURE("feature"),
    PART("part"),
    SOLID("solid");

    private String type;

    MarkType(String type) {
        this.type = type;
    }
}
