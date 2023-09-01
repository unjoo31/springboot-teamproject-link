package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyException;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }

    public Notice 공고상세보기(Integer id) {
       Optional<Notice> noticeOP = noticeRepository.findById(id);
       if (noticeOP.isPresent()) {
           return noticeOP.get();
       }else{
        throw new MyException(id + "는 찾을 수 없습니다.");
       }
        
    }
    
}
