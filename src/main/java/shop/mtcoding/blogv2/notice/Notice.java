package shop.mtcoding.blogv2.notice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.hasharea.HashArea;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "notice_tb")
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer career; // 경력

    private String title; // 제목

    private String academicAbility; // 학력

    private String salary; // 급여

    private String typeOfWork; // 근무형태

    @CreationTimestamp // now를 표시하기 위한 어노테이션
    private Timestamp createdAt; // 선택한 날짜를 넣을 수 있다

    private Date endDate; // 마감일

    @Lob
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Apply> applyList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HashSkil> hashSkilList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HashArea> hashAreaList = new ArrayList<>();

    @Builder
    public Notice(Integer id, Integer career, String title, String academicAbility, String salary, String typeOfWork,
            Timestamp createdAt, Date endDate, String content, User user, List<Apply> applyList,
            List<HashSkil> hashSkilList, List<HashArea> hashAreaList) {

        this.id = id;
        this.career = career;
        this.title = title;
        this.academicAbility = academicAbility;
        this.salary = salary;
        this.typeOfWork = typeOfWork;
        this.createdAt = createdAt;
        this.endDate = endDate;
        this.content = content;
        this.user = user;
        this.applyList = applyList;
        this.hashSkilList = hashSkilList;
        this.hashAreaList = hashAreaList;
    }
}
