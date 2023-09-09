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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysql.cj.protocol.x.Notice;

import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.resume.ResumeRequest;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ApplyController {

    
    @Autowired
    private HttpSession session;

    @Autowired 
    private ApplyService applyService;

    // 지원 등록 현황
    @GetMapping("/seekerSupport")
    public String seekerSupport(HttpServletRequest request){
    User sessionUser = (User) session.getAttribute("sessionUser");    
    System.out.println("테스트 : " + sessionUser.getId());

    int passCount = 0;
    int failCount = 0;
    int undecidedCount = 0;
    // 공고현황 화면에 보여줄 값 담기
    List<Apply> applyList = applyService.지원현황보기(sessionUser.getId());
    
    List<Map<String, Object>> applyDataList = new ArrayList<>();
        for (Apply apply : applyList) {
            Map<String, Object> applyData = new HashMap<>();
            applyData.put("title", apply.getNotice().getTitle());
            applyData.put("name", apply.getUser().getName());
            applyData.put("pass", apply.getPass());
            applyData.put("applyId", apply.getId());
             if ("합격".equals(apply.getPass())) {
                passCount++;
            }else if("불합격".equals(apply.getPass())){
                failCount++;
            }else if("미정".equals(apply.getPass())){
                undecidedCount++;
            }

            applyDataList.add(applyData);
        }
        request.setAttribute("applyDataList", applyDataList);
        request.setAttribute("pass", passCount);
        request.setAttribute("fail", failCount);
        request.setAttribute("undecided", undecidedCount);

    return "seeker/seekerSupport";

}


    // 채용공고 (이력서 상세보기 전)
    // 지원자 현황 (이력서 상세보기 전)
    @GetMapping("/corporationSupportDetail/{id}")
    public String corporationSupportDetail(@PathVariable Integer id, HttpServletRequest request){
        System.out.println("테스트 : 111"+ id);
        User sessionUser = (User) session.getAttribute("sessionUser");
        
        List<Apply> applyList = applyService.지원자현황(id);

        int passCount = 0;
        int failCount = 0;
        int undecidedCount = 0;

        List<Map<String, Object>> applyDataList = new ArrayList<>();
        for (Apply apply : applyList) {
            Map<String, Object> applyData = new HashMap<>();
            if ("합격".equals(apply.getPass())) {
                passCount++;
            }else if("불합격".equals(apply.getPass())){
                failCount++;
            }else if("미정".equals(apply.getPass())){
                undecidedCount++;
            }
            applyDataList.add(applyData);
        }
        request.setAttribute("applyList", applyList);
        request.setAttribute("applyDataList", applyDataList);
        request.setAttribute("pass", passCount);
        request.setAttribute("fail", failCount);
        request.setAttribute("undecided", undecidedCount);

        return "/corporation/corporationSupportDetail";
    }

    // 지원현황 상세보기
    @GetMapping("/seekerSupportDetail/{id}")
    public String seekerSupportDetail(@PathVariable Integer id, HttpServletRequest request){
    Optional<Apply> apply = applyService.지원현황상세보기(id);
    request.setAttribute("apply", apply);
    
    return "/seeker/seekerSupportDetail";

    }

    // 지원자 이력서 상세보기
    @GetMapping("/corporationSupportSeekerList/{id}")
    public String corporationSupportSeekerList(@PathVariable Integer id, HttpServletRequest request){
    Optional<Apply> apply = applyService.지원자이력서조회하기(id);    
    request.setAttribute("apply", apply);
     

    return "corporation/corporationSupportSeekerList";
}


    // 합격, 불합격, 미정 
    @PostMapping("/apply/pass")
    public String pass(ApplyRequest.PassDTO passDTO){
    applyService.합격여부결정(passDTO);
    return "redirect:/corporationSupportDetail/" + passDTO.getNoticeId();
}

}
