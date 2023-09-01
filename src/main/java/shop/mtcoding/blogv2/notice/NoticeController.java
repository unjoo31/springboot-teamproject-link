package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2.skill.Skill;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request){

        // 채용공고 리스트 보여주기
        Page<Notice> noticePG = null; 

        if(keyword.isBlank()){
            noticePG = noticeService.채용공고목록보기(page);
        }else{
            noticePG = noticeService.채용공고목록보기(page, keyword);
            request.setAttribute("keyword", keyword);
        }

        List<List<String>> skillsList = noticeService.스킬리스트보기(noticePG);

        request.setAttribute("noticePG", noticePG);
        request.setAttribute("prevPage", noticePG.getNumber() - 1);
        request.setAttribute("nextPage", noticePG.getNumber() + 1);
        request.setAttribute("skillsList", skillsList);

        for (int i = 0; i < skillsList.size(); i++) {
            System.out.println("테스트 스킬" + (i + 1) + ": " + skillsList.get(i));
        }

        for (Notice notice : noticePG) {
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getContent());
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getTitle());
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getUser().getUsername());
        }

        // 기업리스트 보여주기

        return "index";
    }

    // String
    @GetMapping("/corporationSupport")
    public String corporationSupport(HttpServletRequest request){

    List<Notice> noticeList = noticeService.공고목록보기();
    
    List<Map<String, Object>> noticeDataList = new ArrayList<>();
    for (Notice notice : noticeList) {
        Map<String, Object> noticeData = new HashMap<>();
        noticeData.put("title", notice.getTitle());
        noticeData.put("user", notice.getUser());
        noticeData.put("hashSkilList", notice.getHashSkilList());
        
        Date startDate = notice.getCreatedAt();
        Date endDate = notice.getEndDate();

        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
        long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
        noticeData.put("timeDifference", timeDifferenceDays);

        noticeDataList.add(noticeData);
    }
    
    request.setAttribute("noticeDataList", noticeDataList);
    return "/corporation/corporationSupport";

    }
}