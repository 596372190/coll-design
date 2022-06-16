package com.example.colldesign.comment.vo;

import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private String company;
    private String documentationName;
    private String documentationNameOverride;
    private String email;
    private String firstName;
    private String globalPermissions;
    private String href;
    private String id;
    private String image;
    private boolean isGuest;
    private boolean isLight;
    private Date lastLoginTime;
    private String lastName;
    private String name;
    private boolean personalMessageAllowed;
    private int source;
    private int state;
}
