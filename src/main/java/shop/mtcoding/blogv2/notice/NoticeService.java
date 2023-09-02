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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillRepository;


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

    // 공고현황 조회할때 사용
    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }

    // 입사지원 화면 
    public Notice 공고상세보기(int id) {
       Optional<Notice> noticeOP = noticeRepository.findById(id);
       if (noticeOP.isPresent()) {
           return noticeOP.get();
       }else{
        throw new MyException(id + "는 찾을 수 없습니다.");
       }
        
    }

    
}
