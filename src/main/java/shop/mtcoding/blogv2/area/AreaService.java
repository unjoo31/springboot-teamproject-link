package shop.mtcoding.blogv2.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AreaService {

    @Autowired
    AreaRepository areaRepository;

    public List<Area> 지역리스트목록보기() {
        return areaRepository.findAll();
    }

    public List<Area> 모든지역가져오기() {
        List<Area> area = areaRepository.findAll();
        return area;
    }
}
