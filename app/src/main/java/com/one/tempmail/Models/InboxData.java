package com.one.tempmail.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InboxData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("date")
    @Expose
    private String date;

    public InboxData(int id, String from, String subject, String date) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
