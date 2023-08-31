package shop.mtcoding.blogv2.notice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<Long> timeDifferenceList = new ArrayList<>();
        
        
    for (Notice notice : noticeList) {
        Date startDate = notice.getOrderDate();
        Date endDate = notice.getEndDate();

        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
        long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
        timeDifferenceList.add(timeDifferenceDays);
        System.out.println("테스트 : " + timeDifferenceList);
    }
    request.setAttribute("noticeList", noticeList);
    request.setAttribute("timeDifferenceList", timeDifferenceList);
    return "/corporation/corporationSupport";
}
}