package shop.mtcoding.blogv2.hashskil;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "hash_skil_tb")
@Entity
public class hashSkil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @Builder
    public hashSkil(Integer id, User user, Skill skill, Notice notice, Resume resume) {
        this.id = id;
        this.user = user;
        this.skill = skill;
        this.notice = notice;
        this.resume = resume;
    }
    
}
