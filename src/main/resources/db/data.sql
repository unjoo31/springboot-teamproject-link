INSERT INTO user_tb (
  username, password, email, company_user,
  pic_url, name, phonenumber, address, age,
  business, form, performance
) VALUES (
  'ssar1', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원1', '123-456-7890', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
),
(
  'ssar2', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원2', '123-456-7890', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
),
(
  'ssar3', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원3', '123-456-7890', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
),
(
  'ssar4', '1234', 'ssar@nate.com', TRUE,
  'basic.jpg', '기업회원4', '123-456-7890', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
),
(
  'cos1', '1234', 'cos@nate.com', FALSE,
  'basic.jpg', '일반회원1', '987-654-3210', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
),
(
  'cos2', '1234', 'cos@nate.com', FALSE,
  'basic.jpg', '일반회원2', '987-654-3210', '123 Main St, City', '1985-05-20',
  'Tech Company', 'Software Development', 'Good'
);
INSERT INTO notice_tb (career, title, academic_ability, salary, type_of_work, created_at, end_date, content, user_id)
VALUES
<<<<<<< HEAD
    (1, 'Backend Developer Position', '학력무관', '면접 후 결정', '정규직', now(), '2023-09-03', 'We are seeking an experienced Backend Developer...', 1),
    (2, 'UI/UX Designer Internship', '학력무관', '면접 후 결정', '아르바이트', now(), '2023-09-05', 'Join our UI/UX Design team as an intern to...', 2),
=======
<<<<<<< HEAD
    (1, 'Backend Developer Position', '학력무관', '면접 후 결정', '정규직', now(), '2023-09-03', 'We are seeking an experienced Backend Developer...', 1),
    (2, 'UI/UX Designer Internship', '학력무관', '면접 후 결정', '아르바이트', now(), '2023-09-05', 'Join our UI/UX Design team as an intern to...', 2);
=======
>>>>>>> dev
    (2023, '프론트엔드 개발자 채용', 'React, Vue, Angular 중 1개 이상의 개발 경험', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', now(), '2023-08-31', '백엔드/퍼블리싱/앱 개발 경험 ...', 1),
    (2023, '웹 개발 경력 및 신입사원 채용', '웹 개발 및 시스템 운영', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', now(), '2023-08-31', 'JAVA개발자(Java, JSP, Spring 등)...', 1),
    (2023, '백엔드/프론트엔드개발자(java/php/react)', 'java/php/react', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', now(), '2023-08-31', 'java/php/react...', 1),
    (2023, 'Flutter/React-Native 개발자 채용', 'Flutter/React-Native 개발', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', now(), '2023-08-31', 'java/php/react...', 1),
    (2023, '엔드/ QC/ GUI디자이너 채용', 'java/php/react', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', '2023-08-01', now(), 'java/php/react...', 1),
    (2023, 'HR 클라우드 서비스 개발(C#) 신입', 'java/php/react', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', '2023-08-01', now(), 'java/php/react...', 1),
    (2023, '웹/ 서버/ 앱 개발/ 시스템 엔지니어', 'java/php/react', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', '2023-08-01', now(), 'java/php/react...', 1),
    (2023, 'BackEnd/PHP 개발자/보안솔루션 채용', '보안 웹하드 솔루션/서비스 BackEnd 개발', '시스템 소프트웨어 개발 및 공급업', '신입, 경력', now(), '2023-09-30', 'Python 개발 경력 3년 이상자...', 1);
>>>>>>> dev
INSERT INTO resume_tb (career, content, user_id)
VALUES
(1, '7 years of experience in web development.', 3),
(1, 'Recent graduate with strong programming skills.', 3);
INSERT INTO apply_tb (pass, user_id, notice_id, resume_id)
VALUES
('합격', 1, 1, 1),
('미정', 1, 1, 1),
('불합격', 2, 2, 2);
INSERT INTO skill_tb (skill_name) VALUES('CSS');
INSERT INTO skill_tb (skill_name) VALUES('C#');
INSERT INTO skill_tb (skill_name) VALUES('JSP');
INSERT INTO skill_tb (skill_name) VALUES('Android');
INSERT INTO skill_tb (skill_name) VALUES('AJAX');
INSERT INTO skill_tb (skill_name) VALUES('Flutter');
INSERT INTO skill_tb (skill_name) VALUES('HTML');
INSERT INTO skill_tb (skill_name) VALUES('MySQL');
INSERT INTO skill_tb (skill_name) VALUES('Java');
INSERT INTO skill_tb (skill_name) VALUES('JavaScript');
INSERT INTO skill_tb (skill_name) VALUES('Git');
INSERT INTO skill_tb (skill_name) VALUES('AWS');

INSERT INTO area_tb (area_name)
VALUES
    ('Busan'),
    ('Seoul'),
    ('Gyeonggi'),
    ('Daegu');
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
(3, 1, null, 1),
(3, 2, null, 1),
(3, 3, null, 1),
(1, 1, 1, null),
(1, 2, 2, null),
(1, 3, 3, null),
(1, 4, 4, null),
(2, 5, 5, null),
(2, 5, 6, null),
(2, 6, 7, null),
(2, 7, 8, null);
INSERT INTO hash_area_tb (user_id, notice_id, resume_id, area_id) VALUES
<<<<<<< HEAD
(3, null, 1, 1),
(3, null, 1, 2),
(1, 1, null, 1),
(1, 1, null, 2),
(1, 1, null, 3),
(1, 1, null, 4),
(2, 2, null, 4);

=======
<<<<<<< HEAD
(3, null, 1, 1),
(3, null, 1, 2),
(1, 1, null, 1),
(1, 1, null, 2),
(1, 1, null, 3),
(1, 1, null, 4),
(2, 2, null, 4);
=======
(1, 1, null, 1),
(2, 2, null, 1),
(3, null, 2, 1);
>>>>>>> dev
>>>>>>> dev
