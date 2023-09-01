package shop.mtcoding.blogv2.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> 모든지역가져오기() {

        List<Area> area = areaRepository.findAll();

        return area;
    }

}
