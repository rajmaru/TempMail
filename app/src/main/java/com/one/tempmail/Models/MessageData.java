package com.one.tempmail.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MessageData {
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

    @SerializedName("attachments")
    @Expose
    ArrayList<AttachmentsData> attachments = new ArrayList<AttachmentsData>();

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("textBody")
    @Expose
    private String textBody;

    @SerializedName("htmlBody")
    @Expose
    private String htmlBody;

    public MessageData(int id, String from, String subject, String date, ArrayList<AttachmentsData> attachments, String body, String textBody, String htmlBody) {
        this.id = id;
        this.from = from;
        this.subject = subject;
        this.date = date;
        this.attachments = attachments;
        this.body = body;
        this.textBody = textBody;
        this.htmlBody = htmlBody;
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

    public ArrayList<AttachmentsData> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<AttachmentsData> attachments) {
        this.attachments = attachments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(String htmlBody) {
        this.htmlBody = htmlBody;
    }
}
