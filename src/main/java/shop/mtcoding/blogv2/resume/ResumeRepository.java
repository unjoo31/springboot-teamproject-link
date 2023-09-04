package shop.mtcoding.blogv2.resume;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.apply.Apply;

public interface ResumeRepository extends JpaRepository<Resume, Integer>{

    Optional<Resume> findByUserId(@Param("userId") Integer userId);
    

}
