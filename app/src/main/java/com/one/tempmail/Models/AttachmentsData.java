package com.one.tempmail.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentsData {
    @SerializedName("filename")
    @Expose
    private String filename;

    @SerializedName("contentType")
    @Expose
    private String contentType;

    @SerializedName("size")
    @Expose
    private float size;

    public AttachmentsData(String filename, String contentType, float size) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
