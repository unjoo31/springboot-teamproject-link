package shop.mtcoding.blogv2.hashSkil;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.hashskil.HashSkilRepository;
import shop.mtcoding.blogv2.skill.Skill;

@DataJpaTest
public class HashSkilTest {

    @Autowired
    private HashSkilRepository hashSkilRepository;

    @Test
    public void findByResumeSkil_test() {
        List<Skill>  hashSkil = hashSkilRepository.findSkillsByResumeId(1);

        for (Skill hashSkil2 : hashSkil) {
        System.out.println(hashSkil2);
        }
    }

}
