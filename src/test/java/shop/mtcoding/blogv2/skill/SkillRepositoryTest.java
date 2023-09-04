package shop.mtcoding.blogv2.skill;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.hashskil.HashSkilRepository;


@DataJpaTest
public class SkillRepositoryTest {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private HashSkilRepository hashSkilRepository;

    

    // ㅠㅠㅠ 드디어.. 완성됬어. 할수있구나.
    @Test
    public void 나머지스킬가져오기_test () {

         List<Skill> skill = skillRepository.findAll();

        List<Skill> skillList = hashSkilRepository.findSkillsByResumeId(1);

        List<Skill> restSkill = skill.stream()
        .filter(s -> !skillList.contains(s))
        .collect(Collectors.toList());


        for (Skill skill2 : restSkill) {
            System.out.println(skill2);
        }
        
    }



    
}
