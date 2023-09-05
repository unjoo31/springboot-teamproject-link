package shop.mtcoding.blogv2.resume;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

public interface ResumeRepository extends JpaRepository<Resume, Integer>{

    // 이력서수정에 필요한 메소드
    List<Resume> findByUserId(@Param("userId") Integer userId);

    Optional<Resume> findByUser_Id(Integer id);

    @Query("Select r from Resume r where r.id = :resumeId")
    Resume mfindByResumeId(@Param("resumeId") Integer resumeId);


    

}
