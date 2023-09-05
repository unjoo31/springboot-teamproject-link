package shop.mtcoding.blogv2.apply;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.user.User;

public interface ApplyRepository extends JpaRepository<Apply, Integer>{

  List<Apply> findByUserId(@Param("userId") Integer userId);

  List<Apply> findByNoticeId(@Param("noticeId") Integer noticeId);

  @Modifying
  @Query("UPDATE Apply a SET a.pass = :newPass WHERE a.id = :applyId")
  void updatePassById(@Param("newPass") String newPass, @Param("applyId") Integer applyId);

  @Query("SELECT a FROM Apply a left join fetch a.user u WHERE u.id = :userId")
  Optional<Apply> mFindByUserId(@Param("userId") Integer userId);

  @Query(value = "SELECT COUNT(*) FROM APPLY_TB at\n" + //
      "where notice_id = :noticeId and user_id = :userId", nativeQuery = true)
  int noticeApplyCheck(@Param("userId") User userId, @Param("noticeId") Integer noticeId);
}



    

