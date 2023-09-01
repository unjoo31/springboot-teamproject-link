package shop.mtcoding.blogv2.notice;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    public Page<Notice> 채용공고목록보기(Integer page){
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findAll(pageable);
    }

    public Page<Notice> 채용공고목록보기(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findByTitleContaining(keyword, pageable);
    }   

    public List<List<String>> 스킬리스트보기(Page<Notice> noticePage) {
        List<List<String>> skillsList = new ArrayList<>();
        for (Notice notice : noticePage) {
            List<String> skills = notice.getHashSkilList().stream()
                    .map(hashSkil -> hashSkil.getSkill().getSkillName())
                    .collect(Collectors.toList());
            skillsList.add(skills);
        }
        return skillsList;
    }
    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }
    
}
