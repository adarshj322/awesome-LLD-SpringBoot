package com.awesomelld.social.mapper;

import com.awesomelld.social.dto.CommentResponse;
import com.awesomelld.social.dto.FeedItemResponse;
import com.awesomelld.social.dto.PostResponse;
import com.awesomelld.social.dto.UserResponse;
import com.awesomelld.social.entity.CommentEntity;
import com.awesomelld.social.entity.FeedItemEntity;
import com.awesomelld.social.entity.PostEntity;
import com.awesomelld.social.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocialMapper {

    UserResponse toUserResponse(UserEntity entity);

    @Mapping(target = "authorId", source = "postEntity.author.id")
    @Mapping(target = "authorName", source = "postEntity.author.displayName")
    @Mapping(target = "likeCount", expression = "java(likeCount)")
    PostResponse toPostResponse(PostEntity postEntity, long likeCount);

    @Mapping(target = "postId", source = "entity.post.id")
    @Mapping(target = "authorId", source = "entity.author.id")
    @Mapping(target = "authorName", source = "entity.author.displayName")
    CommentResponse toCommentResponse(CommentEntity entity);

    @Mapping(target = "postId", source = "entity.post.id")
    @Mapping(target = "authorId", source = "entity.post.author.id")
    @Mapping(target = "authorName", source = "entity.post.author.displayName")
    @Mapping(target = "content", source = "entity.post.content")
    FeedItemResponse toFeedItemResponse(FeedItemEntity entity);
}
