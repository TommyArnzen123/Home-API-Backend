package com.arnzen.home_api_backend.model.notification;

public class EmailSetup {

    private String toAddress;
    private String ccAddress;
    private String subject;
    private String body;
    private boolean isHtml;

    public EmailSetup() {}

    public EmailSetup(String toAddress, String ccAddress, String subject, String body, boolean isHtml) {
        this.toAddress = toAddress;
        this.ccAddress = ccAddress;
        this.subject = subject;
        this.body = body;
        this.isHtml = isHtml;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }
}
