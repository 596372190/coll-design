package com.example.colldesign.websocket.vo;

import javax.websocket.Session;

public class UserVo {

    private String userId;
    private String name;
    private String image;
    private Session session;
    private String color;


    public UserVo() {
    }


    public UserVo(String userId, String name, String image, Session session, String color) {
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.session = session;
        this.color = color;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
