package shop.mtcoding.blogv2.resume;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

public class ResumeRequest {

    @Getter
    @Setter
    public static class ResumeSaveDTO {


        // User 에서 뽑아낼 데이터
        private String name;
        private String email;
        private String address;
        private String phoneNumber;
        private Date date;

        // Resume 에서 뽑아낼 데이터
        private Integer career;
        private String content;

        // Resume 에서 뽑아낼 데이터 [배열 타입으로]
        // -> OneToMany관계에선 DTO를 안받아도 된다.
        private ArrayList<String> skilList;
        private ArrayList<String> areaList;

    }

    @Getter
    @Setter    
    public static class TransmitDTO{
        private String pass;
        private Integer noticeId;
    }

    @Getter
    @Setter
    public static class PassDTO{
        private String pass;
        private Integer applyId;

    }

}
