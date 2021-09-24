package com.instagram.postService.service.impl;

import com.instagram.postService.dto.PostResponseDto;
import com.kafka.dto.DislikeDto;
import com.instagram.postService.entity.Post;
import com.instagram.postService.repository.PostRepository;
import com.instagram.postService.service.PostService;
import com.kafka.dto.NotificationDto;
import com.kafka.dto.PostEventDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;


    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Post get(String postId) {
        return postRepository.findById(postId).get();
    }

    @Override
    public Post save(Post post) {
        post.setTimestamp(LocalDateTime.now());
//        HttpEntity<Post> postHttpEntity = new HttpEntity<>(post);HttpEntity
//        restTemplate.postForObject("http://localhost:8070/", postHttpEntity, Post.class);

        //here one kafka topic for notification
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void delete(String postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostResponseDto dislike(String postId, String userId) {
        Post post = get(postId);
        post.getLikes().remove(userId);
        Set<String> dislikes = post.getDislikes();
        if(!dislikes.contains(userId)){
            DislikeDto dislikeDTO  = new DislikeDto();
            dislikeDTO.setPostId(postId);
            dislikeDTO.setCreatedTimeStamp(new Date(System.currentTimeMillis()));
            dislikeDTO.setPlatform("Instagram");
            dislikeDTO.setUserId(userId); // the user who disliked the post
            dislikeDTO.setLeadId(post.getUserId()); // in which account the post disliked
//            if(post.getAccountType() == "Business")
                dislikeDTO.setBusiness(true);
//            else
//                dislikeDTO.setBusiness(false);
            kafkaTemplate.send("dislikes", dislikeDTO);

            PostEventDto postEventDto = new PostEventDto();
            postEventDto.setPostId(postId);
            postEventDto.setPlatform("Instagram");
            postEventDto.setType("dislike");
            postEventDto.setUserId(userId);
            kafkaTemplate.send("posts", postEventDto);

        }
        dislikes.add(userId);
        post.setDislikes(dislikes);
        postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto();
        BeanUtils.copyProperties(post, postResponseDto);
        postResponseDto.setDislikeCount(post.getDislikes().size());
        postResponseDto.setLikeCount(post.getLikes().size());
        postResponseDto.setDislike(true);
        postResponseDto.setLike(false);
        return postResponseDto;



    }

    @Override
    public PostResponseDto like(String postId, String userId) {
        Post post = get(postId);
        post.getDislikes().remove(userId);

        Set<String> likes = post.getLikes();
        if(!likes.contains(userId)){

            //here like kafka topic -> to be get from tejas
            PostEventDto postEventDto = new PostEventDto();
            postEventDto.setPostId(postId);
            postEventDto.setType("like");
            postEventDto.setPlatform("Instagram");
            postEventDto.setUserId(userId);
            kafkaTemplate.send("posts", postEventDto);

            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setAppId("Instagram");
            notificationDto.setEmailId(post.getUserId());
            notificationDto.setViewed(false);
            notificationDto.setMessage(userId + " liked your post");
            notificationDto.setUrl("dummy url");
            notificationDto.setTimestamp(LocalDateTime.now());
            kafkaTemplate.send("notification", notificationDto);

        }
        likes.add(userId);
        post.setLikes(likes);

        postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto();
        BeanUtils.copyProperties(post, postResponseDto);
        postResponseDto.setLikeCount(post.getLikes().size());
        postResponseDto.setDislikeCount(post.getDislikes().size());
        postResponseDto.setLike(true);
        postResponseDto.setDislike(false);

        return postResponseDto;
    }

    @Override
    public List<Post> getAllPosts(String userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        return postRepository.findByUserId(userId);
    }
}
