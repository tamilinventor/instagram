package com.kafka.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class NotificationDto {


    private String appId;
    private String message;
    private String url;
    private String emailId;
    private LocalDateTime timestamp;
    private Boolean isViewed;

    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Boolean getViewed() {
        return isViewed;
    }
    public void setViewed(Boolean viewed) {
        isViewed = viewed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}