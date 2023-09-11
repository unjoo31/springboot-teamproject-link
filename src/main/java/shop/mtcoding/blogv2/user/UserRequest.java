package shop.mtcoding.blogv2.user;


import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO{
        private String username;
        private String password;
        private String email;
        private Boolean companyUser;
        private MultipartFile Pic;
    }

    @Getter
    @Setter
    public static class LoginDTO{
        private String username;
        private String password;
        private Boolean companyUser;
    }

    @Getter
    @Setter
    public static class UpdateDTO{
        private String password;
        private String email;
        private Boolean companyUser;
        private MultipartFile pic;
        private String name;
        private String phonenumber;
        private String address;
        private Date age;
        private String business;
        private String form;
        private String performance;
    }
}
