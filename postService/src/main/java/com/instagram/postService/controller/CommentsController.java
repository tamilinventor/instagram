package com.instagram.postService.controller;

import com.instagram.postService.entity.Comments;
import com.instagram.postService.entity.NestedComments;
import com.instagram.postService.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{commentId}")
    public Comments get(@PathVariable String commentId){
        return commentService.get(commentId);
    }

    @PostMapping
    public Comments save(@RequestBody Comments comments){
        return commentService.save(comments);
    }

    @PutMapping
    public Comments update(@RequestBody Comments comments){
        return commentService.update(comments);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable String commentId){
        commentService.delete(commentId);
    }

    @PostMapping("/nestedComment/{commentId}")
    public Comments makeNestedComment(@RequestBody NestedComments nestedComments, @PathVariable String commentId){
        return commentService.addNestedComments(nestedComments, commentId);
    }


}
