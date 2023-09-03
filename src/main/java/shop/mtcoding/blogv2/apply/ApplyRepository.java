package shop.mtcoding.blogv2.apply;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplyRepository extends JpaRepository<Apply, Integer>{

  List<Apply> findByUserId(@Param("userId") Integer userId);

  List<Apply> findByNoticeId(Integer id);
    
}
