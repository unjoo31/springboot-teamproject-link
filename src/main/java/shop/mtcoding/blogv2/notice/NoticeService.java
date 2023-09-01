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

    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }

    public List<Notice> 필터링된공고목록보기(String selectedSkill, String selectedArea) {
        List<Notice> filteredNotices = noticeRepository.findByNoticeSkillOrArea(selectedSkill, selectedArea);
        
        System.out.println("테스트"+filteredNotices.size());
        return filteredNotices;
    }
    
}
