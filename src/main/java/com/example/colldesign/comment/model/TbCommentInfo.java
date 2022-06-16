package com.example.colldesign.comment.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dragon
 * @since 2022-06-15
 */
public class TbCommentInfo extends Model<TbCommentInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String message;

    private String elementOccurrences;

    private String elementQuery;

    private String angle;

    private String cameraViewport;

    private String isPerspective;

    private String viewMatrix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getElementOccurrences() {
        return elementOccurrences;
    }

    public void setElementOccurrences(String elementOccurrences) {
        this.elementOccurrences = elementOccurrences;
    }

    public String getElementQuery() {
        return elementQuery;
    }

    public void setElementQuery(String elementQuery) {
        this.elementQuery = elementQuery;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getCameraViewport() {
        return cameraViewport;
    }

    public void setCameraViewport(String cameraViewport) {
        this.cameraViewport = cameraViewport;
    }

    public String getIsPerspective() {
        return isPerspective;
    }

    public void setIsPerspective(String isPerspective) {
        this.isPerspective = isPerspective;
    }

    public String getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(String viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}