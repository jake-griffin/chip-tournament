INSERT INTO player
    (name)
VALUES
    ('Player'),
    ('Other Player');

INSERT INTO account
    (username, password, player_id, role, enabled)
VALUES
    ('admin', '$2a$10$4LH1zCAR5nx5KcKvBaNOj.L6Ug5jYUZBC.rz1KtOCVG.nKsXBU.Ma', NULL, 'ROLE_USER,ROLE_ADMIN', TRUE),
    ('otherAdmin', '$2a$10$4LH1zCAR5nx5KcKvBaNOj.L6Ug5jYUZBC.rz1KtOCVG.nKsXBU.Ma', NULL, 'ROLE_USER,ROLE_ADMIN', TRUE),
    ('user', '$2a$10$gVLhB..iMpFmO4jOW3LSKuF3LJh.szofnCFaawotln/qjRUu.pcBC', 1, 'ROLE_USER', TRUE),
    ('otherUser', '$2a$10$qcx9bPTJryLOu47kWuo4TOTuY.tQs7n5R5TJ2YL0bUXQ/OZkKbNcC', NULL, 'ROLE_USER', TRUE),
    ('disabledUser', '$2a$10$MT5dOabgQWH2RcTO3mYIRezsCss3FsH1xi5GqYvRO1NtDiGnzm4dO', NULL, 'ROLE_USER', FALSE);

INSERT INTO tournament
    (name, account_id, created)
VALUES
    ('Today', 1, CURRENT_DATE),
    ('Yesterday', 2, CURRENT_DATE - 1);
