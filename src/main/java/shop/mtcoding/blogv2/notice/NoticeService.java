package shop.mtcoding.blogv2.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Page<Notice> 채용공고목록보기(Integer page){
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return noticeRepository.findAll(pageable);
    }

    public Page<Notice> 채용공고목록보기(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return noticeRepository.findByTitleContaining(keyword, pageable);
    }    
}
