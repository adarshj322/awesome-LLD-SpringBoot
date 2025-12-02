package com.awesomelld.social.mapper;

import com.awesomelld.social.dto.CommentResponse;
import com.awesomelld.social.dto.FeedItemResponse;
import com.awesomelld.social.dto.PostResponse;
import com.awesomelld.social.dto.UserResponse;
import com.awesomelld.social.entity.CommentEntity;
import com.awesomelld.social.entity.FeedItemEntity;
import com.awesomelld.social.entity.PostEntity;
import com.awesomelld.social.entity.UserEntity;
import com.awesomelld.social.enums.Visibility;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:48:32+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class SocialMapperImpl implements SocialMapper {

    @Override
    public UserResponse toUserResponse(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String username = null;
        String displayName = null;
        String bio = null;

        id = entity.getId();
        username = entity.getUsername();
        displayName = entity.getDisplayName();
        bio = entity.getBio();

        UserResponse userResponse = new UserResponse( id, username, displayName, bio );

        return userResponse;
    }

    @Override
    public PostResponse toPostResponse(PostEntity postEntity, long likeCount) {
        if ( postEntity == null ) {
            return null;
        }

        Long authorId = null;
        String authorName = null;
        Long id = null;
        String content = null;
        Visibility visibility = null;
        Instant createdAt = null;
        if ( postEntity != null ) {
            authorId = postEntityAuthorId( postEntity );
            authorName = postEntityAuthorDisplayName( postEntity );
            id = postEntity.getId();
            content = postEntity.getContent();
            visibility = postEntity.getVisibility();
            createdAt = postEntity.getCreatedAt();
        }

        long likeCount1 = likeCount;

        PostResponse postResponse = new PostResponse( id, authorId, authorName, content, visibility, createdAt, likeCount1 );

        return postResponse;
    }

    @Override
    public CommentResponse toCommentResponse(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long postId = null;
        Long authorId = null;
        String authorName = null;
        Long id = null;
        String content = null;
        Instant createdAt = null;

        postId = entityPostId( entity );
        authorId = entityAuthorId( entity );
        authorName = entityAuthorDisplayName( entity );
        id = entity.getId();
        content = entity.getContent();
        createdAt = entity.getCreatedAt();

        CommentResponse commentResponse = new CommentResponse( id, postId, authorId, authorName, content, createdAt );

        return commentResponse;
    }

    @Override
    public FeedItemResponse toFeedItemResponse(FeedItemEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long postId = null;
        Long authorId = null;
        String authorName = null;
        String content = null;
        Instant deliveredAt = null;

        postId = entityPostId1( entity );
        authorId = entityPostAuthorId( entity );
        authorName = entityPostAuthorDisplayName( entity );
        content = entityPostContent( entity );
        deliveredAt = entity.getDeliveredAt();

        FeedItemResponse feedItemResponse = new FeedItemResponse( postId, authorId, authorName, content, deliveredAt );

        return feedItemResponse;
    }

    private Long postEntityAuthorId(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        UserEntity author = postEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String postEntityAuthorDisplayName(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        UserEntity author = postEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }

    private Long entityPostId(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        PostEntity post = commentEntity.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAuthorId(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity author = commentEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAuthorDisplayName(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity author = commentEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }

    private Long entityPostId1(FeedItemEntity feedItemEntity) {
        if ( feedItemEntity == null ) {
            return null;
        }
        PostEntity post = feedItemEntity.getPost();
        if ( post == null ) {
            return null;
        }
        Long id = post.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityPostAuthorId(FeedItemEntity feedItemEntity) {
        if ( feedItemEntity == null ) {
            return null;
        }
        PostEntity post = feedItemEntity.getPost();
        if ( post == null ) {
            return null;
        }
        UserEntity author = post.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityPostAuthorDisplayName(FeedItemEntity feedItemEntity) {
        if ( feedItemEntity == null ) {
            return null;
        }
        PostEntity post = feedItemEntity.getPost();
        if ( post == null ) {
            return null;
        }
        UserEntity author = post.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }

    private String entityPostContent(FeedItemEntity feedItemEntity) {
        if ( feedItemEntity == null ) {
            return null;
        }
        PostEntity post = feedItemEntity.getPost();
        if ( post == null ) {
            return null;
        }
        String content = post.getContent();
        if ( content == null ) {
            return null;
        }
        return content;
    }
}
