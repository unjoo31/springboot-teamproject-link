<img src = "images/main.png" width="900" height="500" />

# 스프링부트 팀프로젝트 Link

## 프로젝트 3조
- 박언주(팀장)
- 김언약
- 이정환
- 윤지환

## ⚒️기술스택

### Backend
|<img src = "https://blog.kakaocdn.net/dn/cKtAuQ/btrAIO5fzCU/NVWnU8UlhL93kq81Ve87uK/img.png" width="150" height="150" />|
|:--:|
|SpringBoot|

### Frontend
|<img src = "https://blog.kakaocdn.net/dn/cj5mLL/btrAJSMQt43/yfpTni01hZgrvKHmUdVjk1/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/eG2w1k/btrAD5NJ1dy/YwmkEkygLgmKevkYNgWiPk/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/dJtW2R/btrAIfhLlRL/cTJDpEZlRWh9m9QczAkGqK/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/biJtm8/btrAGfWUCEm/wLv8P9GuJP55PI0AWxOyS1/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/m3Phc/btrAGgBsKbm/FNYpkhIrVweUUEH4h5tsWK/img.png" width="150" height="150" />|
|:--:|:--:|:--:|:--:|:--:|
|HTML5|CSS|jQuery|Bootstrap|JavaScript|

### 협업도구
|<img src = "https://blog.kakaocdn.net/dn/eyjfrN/btrAKvXV0RA/zkyytdkZy7ESd85knYRDq1/img.png" width="150" height="150" />|<img src = "https://blog.kakaocdn.net/dn/mEK9t/btrAHjxWZX3/iEGILm2rWSrOKsfilmPUA1/img.png" width="150" height="150" />|
|:--:|:--:|
|Git|Github|

### 데이터베이스
|<img src = "https://blog.kakaocdn.net/dn/ccYmmf/btrAGfJoswn/gVqLJkEUq6WgY1MwOEopD1/img.png" width="150" height="150" />|
|:--:|
|MySQL|

### 의존성
```java
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-mustache'
implementation 'org.springframework.boot:spring-boot-starter-validation'
implementation 'org.springframework.boot:spring-boot-starter-web'
compileOnly 'org.projectlombok:lombok'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'com.h2database:h2'
runtimeOnly 'com.mysql:mysql-connector-j'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'

// third party
implementation group: 'ch.simas.qlrm', name: 'qlrm', version: '1.7.1'
implementation group: 'org.mindrot', name: 'jbcrypt', version: '0.4'
```

## 기능 설명
**Link**는 기업회원과 일반회원을 연결하여 기업은 구직자를 구하고 일반회원은 회사를 구하는 사이트입니다. 이력서 등록, 채용공고 등록을 토대로 북마크, 기술스택매칭 기능을 제공하여 **다양한 서비스를 제공**합니다.

### 🙋‍♀️유저 관련 기능
- 회원가입 시 유저 네임 중복 체크 기능
- 로그인 시 기업회원과 일반회원 메뉴 변경 기능
- 로그아웃 기능
- 회원정보 수정 시 이미지 변경 기능
- 기업회원 채용 공고 등록, 수정, 삭제 기능
- 기업회원 채용 공고 현황 내 지원자 합격, 불합격 여부 체크 기능
- 기업회원 북마크, 기술스택 매칭 기능
- 일반회원 이력서 등록, 수정, 삭제 기능
- 일반회원 지원현황 내 합격, 불합격 여부 확인 기능
- 일반회원 북마크, 기술스택 매칭 기능
- 게시판 글 작성, 수정, 삭제, 댓글 작성, 삭제 기능

## 보완할 점
- 기업회원 : 채용공고에 지원이 발생하는 경우 알람 기능
- 일반회원 : 지원한 채용공고의 합격, 불합격 여부 체크되면 알람 기능
- 자잘하게 오류들이 계속 발생하는 경우가 있는데 그런 부분들을 완벽하게 만들어야 할 것 같다

## 느낀점
- 개발시에 공통으로 작업이 필요한 경우 해당 팀원과 정리를 정확하게 하고 시작해서 완성도를 더 높이고 싶다
- 북마크한 기업이 공고를 등록했을시에 알림이 오는 기능 등 디테일하게 구현할 수 있는 기능들을 구현하지 못한 부분이 아쉽다
- 확실하게 알고 있는 기능을 구현할 수 있다고 생각했던 것에 대해서 정확하게 모르고 있었고 활용도가 이어지는 줄 몰랐던 점이 아쉽다
- 예외들을 처리하고 깔끔하게 동작하게 하는 작업이 오래걸려서 힘들었다
