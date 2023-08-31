package shop.mtcoding.blogv2.notice;

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

        Notice notice = noticeService.공고목록보기(1);
        System.out.println("테스트 : " + notice.getTitle());
        System.out.println("테스트 : " + notice.getHashSkilList().get(0).toString());
        System.out.println("테스트 : " + notice.getUser().getName());
        request.setAttribute("notice", notice);
        return "/corporation/corporationSupport";
    }
}
