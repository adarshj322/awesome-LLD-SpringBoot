package com.awesomelld.stackoverflow.support;

import com.awesomelld.stackoverflow.entity.UserEntity;
import com.awesomelld.stackoverflow.enums.PostType;
import com.awesomelld.stackoverflow.enums.VoteType;
import org.springframework.stereotype.Component;

@Component
public class ReputationPolicy {

    public void handleVoteChange(UserEntity target, VoteType previous, VoteType next, PostType postType) {
        int delta = delta(postType, next) - delta(postType, previous);
        target.setReputation(target.getReputation() + delta);
    }

    public void handleAcceptedAnswer(UserEntity answerAuthor) {
        answerAuthor.setReputation(answerAuthor.getReputation() + 15);
    }

    private int delta(PostType postType, VoteType type) {
        if (type == null) {
            return 0;
        }
        return switch (postType) {
            case QUESTION -> type == VoteType.UPVOTE ? 5 : -2;
            case ANSWER -> type == VoteType.UPVOTE ? 10 : -2;
        };
    }
}
