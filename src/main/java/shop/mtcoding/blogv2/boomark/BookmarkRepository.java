package shop.mtcoding.blogv2.boomark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import shop.mtcoding.blogv2.user.User;

public interface BookmarkRepository extends JpaRepository <Bookmark, Integer>{

    @Query(value = "SELECT * FROM bookmark_tb t\n" + //
            "LEFT JOIN user_tb u ON t.target_id = u.id\n" + //
            "WHERE t.user_id IN :id", nativeQuery = true)
    List<User> findByBookmark(Integer id);
    
}
