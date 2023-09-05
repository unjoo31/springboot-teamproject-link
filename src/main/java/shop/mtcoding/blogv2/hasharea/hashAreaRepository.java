package shop.mtcoding.blogv2.hasharea;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.skill.Skill;

public interface HashAreaRepository extends JpaRepository<HashArea, Integer> {

    @Query("select h.area FROM HashArea h WHERE h.resume.id = :resumeId")
    List<Area> findAreasByResumeId(@Param("resumeId") Integer resumeId);

    // 디비에 데이터가 쌓이는것을 방지하기위해 초기화를 위한 딜리트 메서드 생성
    @Modifying
    @Query("DELETE FROM HashArea h WHERE h.resume.id = :resumeId")
    void deleteByResumeId(@Param("resumeId") Integer resumeId);

    @Query("select h.area FROM HashArea h WHERE h.notice.id = :noticeId")
    List<Area> findAreasByNoticeId(@Param("noticeId") Integer noticeId);

    void deleteByNoticeId(Integer id);
}
