package shop.mtcoding.blogv2.hashskil;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.skill.Skill;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "hash_skil_tb")
@Entity
public class hashSkil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String skillSelect;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @Builder
    public hashSkil(Integer id, String skillSelect, Skill skill, Notice notice, Resume resume) {
        this.id = id;
        this.skillSelect = skillSelect;
        this.skill = skill;
        this.notice = notice;
        this.resume = resume;
    }
}
