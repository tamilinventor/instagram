package com.kafka.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class DislikeDto {
    private String postId;
    private String userId;//the user who disliked the post
    private String platform;
    private Date createdTimeStamp;//time of the event dislike
    private String leadId;//business ownerId
    private Boolean isBusiness;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public Boolean getBusiness() {
        return isBusiness;
    }

    public void setBusiness(Boolean business) {
        isBusiness = business;
    }
}
