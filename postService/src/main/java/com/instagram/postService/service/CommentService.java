package com.instagram.postService.service;

import com.instagram.postService.entity.Comments;
import com.instagram.postService.entity.NestedComments;

public interface CommentService {
    public Comments get(String commentId);
    public Comments save(Comments comments);
    public Comments update(Comments comments);
    public void delete(String commentId);

    Comments addNestedComments(NestedComments nestedComments, String commentId);
}
