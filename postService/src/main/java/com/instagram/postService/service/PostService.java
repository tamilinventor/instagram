package com.instagram.postService.service;

import com.instagram.postService.dto.PostResponseDto;
import com.instagram.postService.entity.Post;

import java.util.List;

public interface PostService {
    public Post get(String postId);
    public Post save(Post post);
    public Post update(Post post);
    public void delete(String postId);

    PostResponseDto dislike(String postId, String userId);

    PostResponseDto like(String postId, String userId);

    List<Post> getAllPosts(String userId);

    List<Post> findByUserId(String userId);
}
