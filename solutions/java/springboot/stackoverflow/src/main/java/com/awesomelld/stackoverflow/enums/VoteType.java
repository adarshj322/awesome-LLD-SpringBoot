package com.awesomelld.stackoverflow.enums;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1);

    private final int delta;

    VoteType(int delta) {
        this.delta = delta;
    }

    public int delta() {
        return delta;
    }
}
