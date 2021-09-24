package com.instagram.postService.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document
public class Comments {
    @Id
    private String commentId;
    private String postId;
    private String userId;
    private String comment;
    private LocalDateTime timestamp;
    private List<NestedComments> nestedCommentsList;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

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

    public List<NestedComments> getNestedCommentsList() {
        return nestedCommentsList;
    }

    public void setNestedCommentsList(List<NestedComments> nestedCommentsList) {
        this.nestedCommentsList = nestedCommentsList;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
