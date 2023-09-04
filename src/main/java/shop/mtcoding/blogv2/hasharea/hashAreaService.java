package shop.mtcoding.blogv2.hasharea;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.skill.Skill;

@Service
public class HashAreaService {

    @Autowired
    private HashAreaRepository hashAreaRepository;

    public List<Area> 선택한지역목룍(Integer resumeId) {
      
        List<Area> areaList = hashAreaRepository.findAreasByResumeId(resumeId);

        return areaList;
    }
    
}
