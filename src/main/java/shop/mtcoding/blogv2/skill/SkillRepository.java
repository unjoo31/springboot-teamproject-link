package shop.mtcoding.blogv2.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.resume.Resume;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Skill findBySkillName(String skillName);

    


    
}
