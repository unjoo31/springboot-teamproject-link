package shop.mtcoding.blogv2.skill;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.resume.Resume;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "skill_tb")
@Entity
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    private String skillName;

    @Builder
    public Skill(Integer id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }           
}
