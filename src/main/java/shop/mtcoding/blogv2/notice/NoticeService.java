package shop.mtcoding.blogv2.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Notice 공고목록보기(int s) {
        return noticeRepository.findById(s).get();
    }
    
}
