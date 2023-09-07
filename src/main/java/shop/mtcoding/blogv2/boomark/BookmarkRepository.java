package shop.mtcoding.blogv2.boomark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.user.User;

public interface BookmarkRepository extends JpaRepository <Bookmark, Integer>{

    @Query(value = "SELECT bt.id as bookmark_id, ut.id as user_id, ut.username, ut.email, ut.company_user, ut.pic_url, ut.name, ut.phonenumber, ut.address, ut.age, ut.business, ut.form, ut.performance FROM bookmark_tb bt\n" + //
        "LEFT JOIN user_tb ut ON bt.target_id = ut.id\n" + //
        "WHERE bt.user_id = :id", nativeQuery = true)
    List<Object[]> findByBookmark(@Param("id") Integer id);

    @Query(value = "SELECT COUNT(*) FROM bookmark_tb b\n" + //
            "LEFT JOIN notice_tb n ON b.target_id = :noticeId\n" + //
            "WHERE b.user_id = :userId and b.target_id = :noticeId", nativeQuery = true)
    int noticeBookmarkCheck(@Param("userId") User userId, @Param("noticeId") Integer noticeId);

    List<Bookmark> findByUserId(@Param("userId") Integer userId);

    
}
