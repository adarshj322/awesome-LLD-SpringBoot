INSERT INTO users (id, display_name, reputation, created_at) VALUES (1, 'Alice', 1200, CURRENT_TIMESTAMP());
INSERT INTO users (id, display_name, reputation, created_at) VALUES (2, 'Bob', 850, CURRENT_TIMESTAMP());
INSERT INTO users (id, display_name, reputation, created_at) VALUES (3, 'Carol', 450, CURRENT_TIMESTAMP());

INSERT INTO tags (id, name) VALUES (1, 'java');
INSERT INTO tags (id, name) VALUES (2, 'spring');
INSERT INTO tags (id, name) VALUES (3, 'lld');

INSERT INTO questions (id, title, body, created_at, author_id) VALUES (
    1,
    'How do I design a parking lot system?',
    'Looking for guidelines to model entrances, tickets, and fee calculation.',
    CURRENT_TIMESTAMP(),
    1
);

INSERT INTO question_tag (question_id, tag_id) VALUES (1, 1);
INSERT INTO question_tag (question_id, tag_id) VALUES (1, 3);

INSERT INTO answers (id, body, created_at, author_id, question_id, accepted) VALUES (
    1,
    'Break it into floors, spots, and assign tickets to vehicles. Use strategies for fees.',
    CURRENT_TIMESTAMP(),
    2,
    1,
    TRUE
);
INSERT INTO answers (id, body, created_at, author_id, question_id, accepted) VALUES (
    2,
    'Model entry/exit gates and keep commands idempotent. Caching availability helps.',
    CURRENT_TIMESTAMP(),
    3,
    1,
    FALSE
);

INSERT INTO comments (id, body, created_at, author_id, question_id, target_type) VALUES (
    1,
    'Do you also need motorcycle spots?',
    CURRENT_TIMESTAMP(),
    2,
    1,
    'QUESTION'
);

INSERT INTO comments (id, body, created_at, author_id, answer_id, target_type) VALUES (
    2,
    'Great point on idempotency!',
    CURRENT_TIMESTAMP(),
    1,
    2,
    'ANSWER'
);

INSERT INTO votes (id, type, target_type, voter_id, question_id, created_at) VALUES (
    1,
    'UPVOTE',
    'QUESTION',
    2,
    1,
    CURRENT_TIMESTAMP()
);

INSERT INTO votes (id, type, target_type, voter_id, answer_id, created_at) VALUES (
    2,
    'UPVOTE',
    'ANSWER',
    1,
    1,
    CURRENT_TIMESTAMP()
);
