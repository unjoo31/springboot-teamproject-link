package shop.mtcoding.blogv2.apply;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ApplyController {

    
    @Autowired
    private HttpSession session;

    @Autowired 
    private ApplyService applyService;

    // 이력서 등록 현황
    @GetMapping("/seekerSupport")
    public String seekerSupport(HttpServletRequest request){
    User sessionUser = (User) session.getAttribute("sessionUser");    
    System.out.println("테스트 : " + sessionUser.getId());
    // 공고현황 화면에 보여줄 값 담기
    List<Apply> applyList = applyService.지원현황보기(sessionUser.getId());
    
    List<Map<String, Object>> applyDataList = new ArrayList<>();
        for (Apply apply : applyList) {
            Map<String, Object> applyData = new HashMap<>();
            applyData.put("title", apply.getNotice().getTitle());
            applyData.put("name", apply.getUser().getName());
            applyData.put("pass", apply.getPass());
            
            applyDataList.add(applyData);
        }
        request.setAttribute("applyDataList", applyDataList);

    return "seeker/seekerSupport";
}
}
