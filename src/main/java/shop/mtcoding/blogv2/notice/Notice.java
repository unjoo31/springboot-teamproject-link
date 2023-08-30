package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.hasharea.hashArea;
import shop.mtcoding.blogv2.hashskil.hashSkil;
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

    private Integer career;
    
    private String title;

    private String academicAbility;

    private String salary;

    private String typeOfWork;

    private Date orderDate;

    private Date endDate;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<Apply> applyList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<hashSkil> hashSkilList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    private List<hashArea> hashAreaList = new ArrayList<>();

    @Builder
    public Notice(Integer id, Integer career, String title, String academicAbility, String salary, String typeOfWork,
            Date orderDate, Date endDate, String content, User user, List<Apply> applyList, List<hashSkil> hashSkilList,
            List<hashArea> hashAreaList) {
        this.id = id;
        this.career = career;
        this.title = title;
        this.academicAbility = academicAbility;
        this.salary = salary;
        this.typeOfWork = typeOfWork;
        this.orderDate = orderDate;
        this.endDate = endDate;
        this.content = content;
        this.user = user;
        this.applyList = applyList;
        this.hashSkilList = hashSkilList;
        this.hashAreaList = hashAreaList;
    }

       
}
