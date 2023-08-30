INSERT INTO user_tb (
  username, password, email, company_user,
  pic_url, name, phonenumber, address, age,
  business, form, performance
) VALUES (
  'ssar1', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원1', '123-456-7890', '123 Main St, City', NULL,
  'Tech Company', 'Software Development', 'Good'
);
INSERT INTO user_tb (
  username, password, email, company_user,
  pic_url, name, phonenumber, address, age,
  business, form, performance
) VALUES (
  'ssar2', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원2', '123-456-7890', '123 Main St, City', NULL,
  'Tech Company', 'Software Development', 'Good'
);
INSERT INTO user_tb (
  username, password, email, company_user,
  pic_url, name, phonenumber, address, age,
  business, form, performance
) VALUES (
  'cos1', '1234', 'cos@nate.com', FALSE,
  'basic.jpg', '일반회원1', '987-654-3210', NULL, '1985-05-20',
  NULL, NULL, NULL
);
INSERT INTO notice_tb (career, title, academic_ability, salary, type_of_work, order_date, end_date, content, user_id)
VALUES
    (2023, 'Backend Developer Position', 'Bachelor Degree in Computer Science', 'Competitive', 'Full-time', '2023-08-01', '2023-08-31', 'We are seeking an experienced Backend Developer...', 1),
    (2023, 'UI/UX Designer Internship', 'Enrolled in a Design-related program', 'Unpaid', 'Internship', '2023-09-01', '2023-09-30', 'Join our UI/UX Design team as an intern to...', 2);
INSERT INTO resume_tb (career, content, user_id)
VALUES
(1, '7 years of experience in web development.', 1),
(1, 'Recent graduate with strong programming skills.', 2);
INSERT INTO apply_tb (pass, user_id, notice_id, resume_id)
VALUES
('합격', 1, 1, 1),
('미정', 1, 1, 1),
('불합격', 2, 2, 2);
INSERT INTO skill_tb (skill_name)
VALUES
  ('Java'),
  ('Javascript'),
  ('CSS'),
  ('Python');
INSERT INTO area_tb (area_name)
VALUES
  ('Seoul'),
  ('Busan'),
  ('Daegu'),
  ('Gyeonggi');
INSERT INTO bookmark_tb (target_id)
VALUES
(1),
(2);
INSERT INTO board_tb (title, content, user_id)
VALUES
('First Post', 'Hello, world!', 1),
('Tips for Programming', 'Here are some useful tips for coding.', 2);
INSERT INTO reply_tb (comment, user_id, board_id)
VALUES
('Great post!', 1, 1),
('Thanks for the tips!', 2, 2);
INSERT INTO hash_skil_tb (user_id, skill_id, notice_id, resume_id) VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 2, 2);
INSERT INTO hash_area_tb (user_id, notice_id, resume_id, area_id) VALUES
(1, 1, 1, 1),
(2, 2, 1, 2),
(3, 1, 2, 1);