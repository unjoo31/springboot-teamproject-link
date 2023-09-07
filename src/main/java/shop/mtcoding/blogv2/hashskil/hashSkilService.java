package shop.mtcoding.blogv2.hashskil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.skill.Skill;

@Service
public class HashSkilService {

    @Autowired
    private HashSkilRepository hashSkilRepository;

    public List<Skill> 이력서선택한스킬목록(Integer resumeId) {

        List<Skill> skillList = hashSkilRepository.findSkillsByResumeId(resumeId);

        return skillList;

    }

    

    public List<Skill> 채용공고선택한스킬목록(Integer noticeId) {

        List<Skill> skillList = hashSkilRepository.findSkillsByNoticeId(noticeId);

        return skillList;
    }


    
    public List<Skill> 채용공고선택한스킬목록2(Integer noticeId) {

        List<Skill> skillList = hashSkilRepository.findSkillsByNoticeId(noticeId);

        return skillList;
    }


    

    public List<HashSkil> 선택한스킬로기업조회하기(List<String> selectedSkills) {

        // 여기서는 파싱이 불가능해 자바차원이 아닌 자바 스크립트의 차원에서 진행해야돼.
        List<HashSkil> hashSkillList = new ArrayList<>();

        for (String selectedSkill : selectedSkills) {
            List<HashSkil> skillsForSkillName = hashSkilRepository.mfindSkillsBySkillName(selectedSkill);
            hashSkillList.addAll(skillsForSkillName);
        }

        return hashSkillList;
    }

    public List<HashSkil> 선택한스킬로이력서조회하기(List<String> selectedSkills) {
        // 여기서는 파싱이 불가능해 자바차원이 아닌 자바 스크립트의 차원에서 진행해야돼.
        List<HashSkil> hashSkillList = new ArrayList<>();

        for (String selectedSkill : selectedSkills) {
            List<HashSkil> skillsForSkillName = hashSkilRepository.mfindResumeBySkillName(selectedSkill);
            hashSkillList.addAll(skillsForSkillName);
        }

        return hashSkillList;
    }

}
