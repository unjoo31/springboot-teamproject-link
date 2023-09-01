package shop.mtcoding.blogv2.notice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NoticeRepository extends JpaRepository <Notice, Long>{

    Page<Notice> findByTitleContaining(String keyword, Pageable pageable);

    // @Query("SELECT DISTINCT n FROM Notice n " +
    //    "LEFT JOIN n.hashSkilList skill " +
    //    "LEFT JOIN n.hashAreaList area " +
    //    "LEFT JOIN skill s ON skill.skillName = :selectedSkill " +
    //    "LEFT JOIN area a ON area.areaName = :selectedArea " +
    //    "WHERE (:selectedSkill IS NOT NULL AND skill.id IS NOT NULL) " +
    //    "OR (:selectedArea IS NOT NULL AND area.id IS NOT NULL)")
    // List<Notice> findByNoticeSkillOrArea(@Param("selectedSkill") String selectedSkill, @Param("selectedArea") String selectedArea);

    @Query(value = "SELECT * FROM notice_tb n " +
       "LEFT JOIN hash_skil_tb hs ON n.id = hs.notice_id " +
       "LEFT JOIN hash_area_tb ha ON n.id = ha.notice_id " +
       "LEFT JOIN skill_tb s ON hs.skill_id = s.id " +
       "LEFT JOIN area_tb a ON ha.area_id = a.id " +
       "WHERE s.skill_name = :selectedSkill " +
       "OR a.area_name = :selectedArea", nativeQuery = true)
    List<Notice> findByNoticeSkillOrArea(@Param("selectedSkill") String selectedSkill, @Param("selectedArea") String selectedArea);
}