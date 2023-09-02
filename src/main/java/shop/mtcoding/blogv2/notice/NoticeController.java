package shop.mtcoding.blogv2.notice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2.skill.Skill;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    

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