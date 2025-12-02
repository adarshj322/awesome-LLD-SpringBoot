INSERT INTO app_user (username, email, display_name, bio, created_at) VALUES ('alice', 'alice@example.com', 'Alice', 'Coffee lover', CURRENT_TIMESTAMP());
INSERT INTO app_user (username, email, display_name, bio, created_at) VALUES ('bob', 'bob@example.com', 'Bob', 'Hiker', CURRENT_TIMESTAMP());
INSERT INTO app_user (username, email, display_name, bio, created_at) VALUES ('carol', 'carol@example.com', 'Carol', 'Reader', CURRENT_TIMESTAMP());

INSERT INTO follow (follower_id, followee_id, created_at) VALUES (2, 1, CURRENT_TIMESTAMP());
INSERT INTO follow (follower_id, followee_id, created_at) VALUES (3, 1, CURRENT_TIMESTAMP());

INSERT INTO post (author_id, content, visibility, created_at) VALUES (1, 'Hello world from Alice', 'PUBLIC', CURRENT_TIMESTAMP());
INSERT INTO post (author_id, content, visibility, created_at) VALUES (1, 'Another day, another coffee.', 'PUBLIC', CURRENT_TIMESTAMP());

-- feed items seeded based on the posts above
INSERT INTO feed_item (user_id, post_id, delivered_at) VALUES (2, 1, CURRENT_TIMESTAMP());
INSERT INTO feed_item (user_id, post_id, delivered_at) VALUES (3, 1, CURRENT_TIMESTAMP());
