package shop.mtcoding.blogv2.area;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    Area findByAreaName(String areaName);

}
