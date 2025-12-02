package com.awesomelld.social.service;

import com.awesomelld.social.dto.*;
import com.awesomelld.social.entity.*;
import com.awesomelld.social.enums.Visibility;
import com.awesomelld.social.exception.InvalidStateException;
import com.awesomelld.social.exception.ResourceNotFoundException;
import com.awesomelld.social.mapper.SocialMapper;
import com.awesomelld.social.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SocialService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final FeedItemRepository feedItemRepository;
    private final SocialMapper mapper;

    @Transactional
    public UserResponse createUser(String username, String email, String displayName, String bio) {
        UserEntity user = new UserEntity(username, email, displayName, bio);
        return mapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream().map(mapper::toUserResponse).toList();
    }

    @Transactional
    public void follow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new InvalidStateException("Cannot follow yourself");
        }
        UserEntity follower = getUser(followerId);
        UserEntity followee = getUser(followeeId);
        followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseGet(() -> followRepository.save(newFollow(follower, followee)));
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        UserEntity follower = getUser(followerId);
        UserEntity followee = getUser(followeeId);
        followRepository.findByFollowerAndFollowee(follower, followee)
                .ifPresent(followRepository::delete);
    }

    @Transactional
    public PostResponse createPost(PostRequest request) {
        UserEntity author = getUser(request.authorId());
        PostEntity post = new PostEntity();
        post.setAuthor(author);
        post.setContent(request.content());
        post.setVisibility(request.visibility() != null ? request.visibility() : Visibility.PUBLIC);
        post.setCreatedAt(Instant.now());
        PostEntity saved = postRepository.save(post);
        fanoutToFollowers(saved);
        long likeCount = likeRepository.countByPost(saved);
        return mapper.toPostResponse(saved, likeCount);
    }

    public PostResponse getPost(Long postId) {
        PostEntity post = getPostEntity(postId);
        long likes = likeRepository.countByPost(post);
        return mapper.toPostResponse(post, likes);
    }

    @Transactional
    public CommentResponse comment(CommentRequest request) {
        PostEntity post = getPostEntity(request.postId());
        UserEntity author = getUser(request.authorId());
        CommentEntity comment = new CommentEntity();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(request.content());
        comment.setCreatedAt(Instant.now());
        return mapper.toCommentResponse(commentRepository.save(comment));
    }

    @Transactional
    public LikeResponse like(Long postId, Long userId) {
        PostEntity post = getPostEntity(postId);
        UserEntity user = getUser(userId);
        likeRepository.findByPostAndUser(post, user)
                .orElseGet(() -> likeRepository.save(newLike(post, user)));
        long count = likeRepository.countByPost(post);
        return new LikeResponse(post.getId(), count);
    }

    @Transactional
    public LikeResponse unlike(Long postId, Long userId) {
        PostEntity post = getPostEntity(postId);
        UserEntity user = getUser(userId);
        likeRepository.findByPostAndUser(post, user).ifPresent(likeRepository::delete);
        long count = likeRepository.countByPost(post);
        return new LikeResponse(post.getId(), count);
    }

    public List<FeedItemResponse> getFeed(Long userId) {
        UserEntity user = getUser(userId);
        return feedItemRepository.findByUserOrderByDeliveredAtDesc(user).stream()
                .map(mapper::toFeedItemResponse)
                .collect(Collectors.toList());
    }

    private UserEntity getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private PostEntity getPostEntity(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    private FollowEntity newFollow(UserEntity follower, UserEntity followee) {
        FollowEntity f = new FollowEntity();
        f.setFollower(follower);
        f.setFollowee(followee);
        f.setCreatedAt(Instant.now());
        return f;
    }

    private LikeEntity newLike(PostEntity post, UserEntity user) {
        LikeEntity like = new LikeEntity();
        like.setPost(post);
        like.setUser(user);
        like.setCreatedAt(Instant.now());
        return like;
    }

    private void fanoutToFollowers(PostEntity post) {
        List<FollowEntity> followers = followRepository.findByFollowee(post.getAuthor());
        for (FollowEntity f : followers) {
            FeedItemEntity feed = new FeedItemEntity();
            feed.setUser(f.getFollower());
            feed.setPost(post);
            feed.setDeliveredAt(Instant.now());
            feedItemRepository.save(feed);
        }
    }
}
