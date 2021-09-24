package com.instagram.postService.controller;

import com.kafka.dto.DislikeDto;
import com.instagram.postService.dto.PostResponseDto;
import com.instagram.postService.entity.Post;
import com.instagram.postService.service.PostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public Post get(@PathVariable String postId){
        return postService.get(postId);
    }

    @PostMapping("/makePost/{userId}")
    public Post save(@RequestBody Post post, @PathVariable String userId){
        post.setTimestamp(LocalDateTime.now());
        post.setUserId(userId);
        return postService.save(post);
    }

    @PutMapping
    public Post update(@RequestBody Post post){
        return postService.update(post);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable String postId){
        postService.delete(postId);
    }

//    @GetMapping("/{userIdList}")
//    public List[]

    @GetMapping("/dislike/{postId}/{userId}")
    public PostResponseDto dislike(@PathVariable String postId, @PathVariable String userId){
        return postService.dislike(postId, userId);
    }

    @GetMapping("/like/{postId}/{userId}")
    public PostResponseDto like(@PathVariable String postId, @PathVariable String userId){
        return postService.like(postId, userId);
    }

    @PostMapping("/getFriendsPost/{userId}")
    public List<PostResponseDto> getFriendsPost(@RequestBody List<String> friends, @PathVariable String userId){
        List<Post> friendsPost;
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        friends.forEach(s -> {
//            System.out.println("Response size" + postResponseDtos.size());
//            System.out.println("friends " + s);
            List<Post> posts = postService.findByUserId(s);
            posts.forEach(post -> {
                PostResponseDto postResponseDto = new PostResponseDto();
//                System.out.println("post" + post.getPostId());
                BeanUtils.copyProperties(post, postResponseDto);
                if(post.getLikes().contains(userId)){
                    postResponseDto.setLike(true);
                    postResponseDto.setDislike(false);
                }else if(post.getDislikes().contains(userId)){
                    postResponseDto.setLike(false);
                    postResponseDto.setDislike(true);
                }else{
                    postResponseDto.setLike(false);
                    postResponseDto.setDislike(false);
                }
//                System.out.println(postResponseDto);
                postResponseDto.setLikeCount(post.getLikes().size());
                postResponseDto.setDislikeCount(post.getDislikes().size());
                postResponseDtos.add(postResponseDto);

            });

        });

        return postResponseDtos;
    }

    @GetMapping("/getAllPosts/{userId}")
    public List<PostResponseDto> getAllPosts(@PathVariable String userId){
        List<Post> posts =  postService.getAllPosts(userId);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        PostResponseDto postResponseDto = new PostResponseDto();
        posts.forEach(post -> {
            BeanUtils.copyProperties(postResponseDto, post);
            if(post.getLikes().contains(userId)) {
                postResponseDto.setLike(true);
                postResponseDto.setDislike(false);
            }else if(post.getDislikes().contains(userId)){
                postResponseDto.setLike(false);
                postResponseDto.setDislike(true);
            }else{
                postResponseDto.setLike(false);
                postResponseDto.setDislike(false);
            }
            postResponseDto.setLikeCount(post.getLikes().size());
            postResponseDto.setDislikeCount(post.getDislikes().size());
            postResponseDtos.add(postResponseDto);
        });
        return postResponseDtos;

    }
}
