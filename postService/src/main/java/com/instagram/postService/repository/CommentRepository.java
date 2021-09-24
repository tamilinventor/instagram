package com.instagram.postService.repository;

import com.instagram.postService.entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends MongoRepository<Comments, String> {
}
