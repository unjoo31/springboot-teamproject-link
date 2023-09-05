package shop.mtcoding.blogv2.hashskil;

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
    
}
