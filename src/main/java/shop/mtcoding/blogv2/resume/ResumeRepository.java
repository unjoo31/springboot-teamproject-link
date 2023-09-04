package shop.mtcoding.blogv2.resume;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ResumeRepository extends JpaRepository<Resume, Integer>{

    Resume findByUserId(@Param("userId") Integer userId);

    

}
