package shop.mtcoding.blogv2.hashskil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.skill.Skill;

public interface HashSkilRepository extends JpaRepository<HashSkil, Integer> {

    @Query("select h.skill FROM HashSkil h WHERE h.resume.id = :resumeId")
    List<Skill> findSkillsByResumeId(@Param("resumeId") Integer resumeId);

    @Query("select h.skill FROM HashSkil h WHERE h.notice.id = :noticeId")
    List<Skill> findSkillsByNoticeId(@Param("noticeId") Integer noticeId);

    @Query(value ="SELECT skill_tb.skill_name, notice_tb.title " +
         "FROM skill_tb " +
         "INNER JOIN hash_skil_tb ON skill_tb.id = hash_skil_tb.skill.id " +
         "INNER JOIN notice_tb ON notice_tb.id = hashSkil_tb.notice.id " +
         "INNER JOIN user_tb ON user_tb.id = hashSkil_tb.user.id " +
         "WHERE user_tb.id = :userId", nativeQuery = true)
    List<Skill> findSkillsByUserId(@Param("userId") Integer userId);



    // 디비에 데이터가 쌓이는것을 방지하기위해 초기화를 위한 딜리트 메서드 생성
    @Modifying
    @Query("DELETE FROM HashSkil h WHERE h.resume.id = :resumeId")
    void deleteByResumeId(@Param("resumeId") Integer resumeId);

    void deleteByNoticeId(Integer id);

    @Query("SELECT h FROM HashSkil h INNER JOIN h.skill s INNER JOIN h.notice notice WHERE s.skillName = :skillName")
    List<HashSkil> mfindSkillsBySkillName(@Param("skillName") String selectedSkills);

    @Query("SELECT h FROM HashSkil h INNER JOIN h.skill s INNER JOIN h.resume resume WHERE s.skillName = :skillName")
    List<HashSkil> mfindResumeBySkillName(@Param("skillName") String selectedSkills);





}
