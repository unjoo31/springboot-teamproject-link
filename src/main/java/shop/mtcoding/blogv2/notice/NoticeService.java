package shop.mtcoding.blogv2.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }
    
}
