package shop.mtcoding.blogv2.hasharea;

import javax.persistence.CascadeType;
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
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "hash_area_tb")
@Entity
public class HashArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY )
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @Builder
    public HashArea(Integer id, User user, Notice notice, Resume resume, Area area) {
        this.id = id;
        this.user = user;
        this.notice = notice;
        this.resume = resume;
        this.area = area;
    }
}
