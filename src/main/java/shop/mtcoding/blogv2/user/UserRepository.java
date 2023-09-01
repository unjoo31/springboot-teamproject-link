package shop.mtcoding.blogv2.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    // username으로 user_tb를 조회
    @Query(value = "select * from user_tb where username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    // username으로 company_user를 조회
    @Query(value = "select company_user from user_tb where username = :username", nativeQuery = true)
    User companyFindByUsername(@Param("username") String username);

    List<User> findByCompanyUserIsTrue();
}
