package com.example.colldesign.comment.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户对象")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
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
