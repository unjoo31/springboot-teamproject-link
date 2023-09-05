package shop.mtcoding.blogv2.boomark;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.user.User;


@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Transactional
    public void 북마크하기(Bookmark bookmark, Integer integer) {
        bookmarkRepository.save(bookmark);
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

}
