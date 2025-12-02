package com.awesomelld.social.controller;

import com.awesomelld.social.dto.*;
import com.awesomelld.social.service.SocialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social")
@Validated
public class SocialController {

    private final SocialService service;

    public SocialController(SocialService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<UserResponse> listUsers() {
        return service.listUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        return service.createUser(request.username(), request.email(), request.displayName(), request.bio());
    }

    @PostMapping("/follows")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void follow(@Valid @RequestBody FollowRequest request) {
        service.follow(request.followerId(), request.followeeId());
    }

    @DeleteMapping("/follows")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@Valid @RequestBody FollowRequest request) {
        service.unfollow(request.followerId(), request.followeeId());
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid @RequestBody PostRequest request) {
        return service.createPost(request);
    }

    @GetMapping("/posts/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return service.getPost(id);
    }

    @PostMapping("/posts/{id}/likes")
    public LikeResponse like(@PathVariable Long id, @RequestParam Long userId) {
        return service.like(id, userId);
    }

    @DeleteMapping("/posts/{id}/likes")
    public LikeResponse unlike(@PathVariable Long id, @RequestParam Long userId) {
        return service.unlike(id, userId);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse comment(@Valid @RequestBody CommentRequest request) {
        return service.comment(request);
    }

    @GetMapping("/feed")
    public List<FeedItemResponse> getFeed(@RequestParam Long userId) {
        return service.getFeed(userId);
    }
}
