package com.awesomelld.social.service;

import com.awesomelld.social.dto.CommentRequest;
import com.awesomelld.social.dto.PostRequest;
import com.awesomelld.social.dto.PostResponse;
import com.awesomelld.social.dto.LikeResponse;
import com.awesomelld.social.enums.Visibility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SocialServiceTest {

    @Autowired
    private SocialService service;

    @Test
    void createPostLikeAndComment() {
        PostResponse post = service.createPost(new PostRequest(1L, "Integration test post", Visibility.PUBLIC));
        assertThat(post.id()).isNotNull();

        LikeResponse like = service.like(post.id(), 2L);
        assertThat(like.likeCount()).isGreaterThan(0);

        service.comment(new CommentRequest(post.id(), 2L, "Great!"));

        List<?> feed = service.getFeed(2L);
        assertThat(feed).isNotEmpty();
    }
}
