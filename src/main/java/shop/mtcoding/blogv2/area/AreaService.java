package shop.mtcoding.blogv2.area;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.hasharea.HashAreaRepository;
import shop.mtcoding.blogv2.skill.Skill;

@Service
public class AreaService {

    @Autowired
    private HashAreaRepository hashAreaRepository;

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> 모든지역가져오기() {

        List<Area> area = areaRepository.findAll();

        return area;
    }

    public List<Area> 나머지지역가져오기(Integer id) {

        List<Area> area = areaRepository.findAll();

        List<Area> areaList = hashAreaRepository.findAreasByResumeId(id);

        List<Area> restArea = area.stream()
                .filter(s -> !areaList.contains(s))
                .collect(Collectors.toList());

        return restArea;
    }

}
