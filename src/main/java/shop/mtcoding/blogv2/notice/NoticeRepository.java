package shop.mtcoding.blogv2.notice;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.h2.engine.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.skill.Skill;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

        Page<Notice> findByTitleContaining(String keyword, Pageable pageable);

        @Query(value = "SELECT * FROM notice_tb n " +
                        "LEFT JOIN hash_skil_tb hs ON n.id = hs.notice_id " +
                        "LEFT JOIN hash_area_tb ha ON n.id = ha.notice_id " +
                        "LEFT JOIN skill_tb s ON hs.skill_id = s.id " +
                        "LEFT JOIN area_tb a ON ha.area_id = a.id " +
                        "WHERE s.skill_name IN :selectedSkillNames " +
                        "AND a.area_name IN :selectedAreaNames", nativeQuery = true)
        List<Notice> findByNoticeSkillsOrAreas(@Param("selectedSkillNames") List<String> selectedSkillNames,
                        @Param("selectedAreaNames") List<String> selectedAreaNames);

        Optional<Notice> findById(int id);

        // 채용공고수정에 필요한 메서드
        @Query("SELECT n FROM Notice n WHERE n.user.id = :userId")
        Notice findNoticeByUserId(@Param("userId") Integer userId);

        @Query("Select n from Notice n where n.id = :noticeId")
        Notice mfindByNoticeId(@Param("noticeId") Integer noticeId);

        // 기존에 있는것
        @Query(value = "SELECT n FROM Notice n " +
        "left outer JOIN fetch n.hashSkilList hs " +
        "left outer JOIN fetch hs.skill s " +
        "WHERE n.user.id = :userId")
        HashSet<Notice> mfindNoticesWithSkillsByUserId(@Param("userId") Integer userId);

        List<Notice> findByUserId(Integer userId);

}
