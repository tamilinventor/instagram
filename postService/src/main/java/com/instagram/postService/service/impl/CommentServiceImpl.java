package com.instagram.postService.service.impl;

import com.instagram.postService.entity.Comments;
import com.instagram.postService.entity.NestedComments;
import com.instagram.postService.entity.Post;
import com.instagram.postService.repository.CommentRepository;
import com.instagram.postService.repository.PostRepository;
import com.instagram.postService.service.CommentService;
import com.instagram.postService.service.PostService;
import com.kafka.dto.NotificationDto;
import com.kafka.dto.PostEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private PostService postService;

    public CommentRepository getCommentRepository() {
        return commentRepository;
    }

    @Override
    public Comments get(String commentId) {
        return commentRepository.findById(commentId).get();
    }

    @Override
    public Comments save(Comments comments) {

        comments.setTimestamp(LocalDateTime.now());

        //here comments kafka topic
        PostEventDto postEventDto = new PostEventDto();
        postEventDto.setUserId(comments.getUserId());
        postEventDto.setPostId(comments.getPostId());
        postEventDto.setType("comments");
        postEventDto.setPlatform("Instagram");
        kafkaTemplate.send("posts", postEventDto);

        NotificationDto notificationDto = new NotificationDto();
        Post post = postService.get(comments.getPostId());
        notificationDto.setEmailId(post.getUserId());
        notificationDto.setViewed(false);
        notificationDto.setAppId("Instagram");
        notificationDto.setTimestamp(LocalDateTime.now());
        notificationDto.setMessage(comments.getUserId() + " commented on your post");
        notificationDto.setUrl("dummy url");
        kafkaTemplate.send("notification", notificationDto);

        return commentRepository.save(comments);
    }

    @Override
    public Comments update(Comments comments) {
        return commentRepository.save(comments);
    }

    @Override
    public void delete(String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comments addNestedComments(NestedComments nestedComments, String commentId) {
        Comments comments = get(commentId);
        List<NestedComments> nestedCommentsList =  comments.getNestedCommentsList();

        System.out.println(nestedComments);
        System.out.println(nestedCommentsList);
        nestedComments.setTimestamp(LocalDateTime.now());
        nestedCommentsList.add(nestedComments);

        System.out.println(nestedCommentsList);

        comments.setNestedCommentsList(nestedCommentsList);

        //here comments kafka topic
        PostEventDto postEventDto = new PostEventDto();
        postEventDto.setUserId(nestedComments.getUserId());
        postEventDto.setPostId(comments.getPostId());
        postEventDto.setType("comments");
        postEventDto.setPlatform("Instagram");
        kafkaTemplate.send("posts", postEventDto);

        return update(comments);
    }
}
