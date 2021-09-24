package com.instagram.postService.entity;

import java.time.LocalDateTime;

public class NestedComments {
    private String userId;
    private String comment;
    private LocalDateTime timestamp;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NestedComments{" +
                "userId='" + userId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
