package shop.mtcoding.blogv2.skill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillService {

    @Autowired
    SkillRepository skillRepository;


    public List<Skill> 모든스킬가져오기() {
        List<Skill> skill = skillRepository.findAll();       
        return skill;
    }
    
    public List<Skill> 스킬리스트목록보기() {
        return skillRepository.findAll();
    }
}
