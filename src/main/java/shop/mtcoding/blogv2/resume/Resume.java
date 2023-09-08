package shop.mtcoding.blogv2.resume;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table (name = "resume_tb")
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer career;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Apply> applyList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HashSkil> hashSkilList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HashArea> hashAreaList = new ArrayList<>();

    @Builder
    public Resume(Integer id, Integer career, String content, User user, List<Apply> applyList,
            List<HashSkil> hashSkilList, List<HashArea> hashAreaList) {
        this.id = id;
        this.career = career;
        this.content = content;
        this.user = user;
        this.applyList = applyList;
        this.hashSkilList = hashSkilList;
        this.hashAreaList = hashAreaList;
    }       
}
    
