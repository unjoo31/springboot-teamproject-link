package shop.mtcoding.blogv2.boomark;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.boomark.BookmarkRequest.BookmarkDTO;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;


@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean 북마크하기(BookmarkDTO bookmarkDTO, Integer sessionUserId) {
        System.out.println("북마크 테스트 3 : " + sessionUserId);
        Bookmark bookmark = Bookmark.builder()
        .targetId(bookmarkDTO.getUserId())
        .user(User.builder().id(sessionUserId).build())
        .build();
        bookmarkRepository.save(bookmark);
    
      return bookmarkRepository.save(bookmark) != null;
    }
    

    public List<User> 북마크기업찾기(Integer id) {
        List<Object[]> result = bookmarkRepository.findByBookmark(id);
        List<User> users = new ArrayList<>();

        for (Object[] row : result) {
            User user = new User();
            user.setName((String) row[6]);            
            user.setAddress((String) row[8]);
            user.setBusiness((String) row[10]);

            users.add(user);
        }

        return users;
    }

    public List<User> 북마크구직자찾기(Integer id) {
        List<Object[]> result = bookmarkRepository.findByBookmark(id);
        List<User> users = new ArrayList<>();

        for (Object[] row : result) {
            User user = new User();
            user.setEmail((String) row[3]);            
            user.setName((String) row[6]);            
            user.setAddress((String) row[8]);

            users.add(user);
        }

        return users;
    }

    public Boolean 북마크여부확인(User userId) {
        int count = bookmarkRepository.noticeBookmarkCheck(userId);
        if(count > 0){
            return true;
        }
        return false;
    }

    public List<User> 관심구직자찾기(Integer id) {
        return userRepository.findBookmarksByTargetIdAndUserIsNotCompanyUser(id);
    }

    public List<User> 관심기업찾기(Integer id) {
        return userRepository.findBookmarksByTargetIdAndUserIsNotUser(id);
    }



    public List<Bookmark> 나를북마크한구직자(Integer id) {
        return bookmarkRepository.findByUserId(id);
    }

}
