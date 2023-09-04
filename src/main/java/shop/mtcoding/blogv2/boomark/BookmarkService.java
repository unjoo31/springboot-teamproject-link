package shop.mtcoding.blogv2.boomark;

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
        return bookmarkRepository.findByBookmark(id);
    } 
}
