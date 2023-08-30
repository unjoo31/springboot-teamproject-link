package shop.mtcoding.blogv2.hasharea;

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
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.skill.Skill;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "hash_area_tb")
@Entity
public class hashArea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String areaSelect;

    @ManyToOne(fetch = FetchType.LAZY)
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;

    @Builder
    public hashArea(Integer id, String areaSelect, Notice notice, Resume resume, Area area) {
        this.id = id;
        this.areaSelect = areaSelect;
        this.notice = notice;
        this.resume = resume;
        this.area = area;
    } 
}
