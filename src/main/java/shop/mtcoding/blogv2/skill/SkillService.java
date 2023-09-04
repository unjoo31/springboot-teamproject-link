package shop.mtcoding.blogv2.skill;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.hashskil.HashSkilRepository;

@Service
public class SkillService {

    @Autowired
    private HashSkilRepository hashSkilRepository;


    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> 모든스킬가져오기() {

        List<Skill> skill = skillRepository.findAll();

        return skill;
    }

    public List<Skill> 이력서나머지스킬가져오기(Integer id) {

        List<Skill> skill = skillRepository.findAll();

        List<Skill> skillList = hashSkilRepository.findSkillsByResumeId(id);

        List<Skill> restSkill = skill.stream()
                .filter(s -> !skillList.contains(s)) // s는 skill의 원소들인데 스킬리스트에 포함되지않는것을 
                .collect(Collectors.toList());

        return restSkill;
    }

      public List<Skill> 채용공고나머지스킬가져오기(Integer id) {

        List<Skill> skill = skillRepository.findAll();

        List<Skill> skillList = hashSkilRepository.findSkillsByNoticeId(id);

        List<Skill> restSkill = skill.stream()
                .filter(s -> !skillList.contains(s)) // s는 skill의 원소들인데 스킬리스트에 포함되지않는것을 
                .collect(Collectors.toList());

        return restSkill;
    }


    
    public List<Skill> 스킬리스트목록보기() {
        return skillRepository.findAll();
    }
}
