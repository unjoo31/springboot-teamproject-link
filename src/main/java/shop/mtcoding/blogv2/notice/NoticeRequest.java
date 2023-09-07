package shop.mtcoding.blogv2.notice;
  

import java.sql.Date;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class NoticeRequest {

    


    @Getter
    @Setter
    public static class NoticeSaveDTO {

        // Notice 에서 뽑아낼 데이터
        private String username; // 기업명

        private Integer career; // 경력

        private String title; // 채용공고제목

        private String academicAbility; // 학력

        private String salary; // 급여

        private String typeOfWork; // 근무형태

        private Date endDate; // 마감일

        private String content; // 공고내용

        private ArrayList<String> skilList; // 요구 스킬
        private ArrayList<String> areaList; // 요구 지역

    }

    @Getter
    @Setter
    public static class NoticeUpdateDTO {

        // Notice 에서 뽑아낼 데이터
        private String username; // 기업명

        private Integer career; // 경력

        private String title; // 채용공고제목

        private String academicAbility; // 학력

        private String salary; // 급여

        private String typeOfWork; // 근무형태

        private Date endDate; // 마감일

        private String content; // 공고내용

        private ArrayList<String> skilList; // 요구 스킬
        private ArrayList<String> areaList; // 요구 지역

    }

}
