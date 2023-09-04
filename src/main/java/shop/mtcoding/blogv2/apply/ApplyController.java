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

    // 합격, 불합격, 미정 
    @PostMapping("/apply/pass")
    public String pass(String pass){
        System.out.println("나 여기 있어 : "+pass);
        return "redirect:/corporation/corporationSupportDetail";
    }

    // 채용공고 (이력서 상세보기 전)
    // 지원자 현황 (이력서 상세보기 전)
    @GetMapping("/corporationSupportDetail")
    public String corporationSupportDetail(HttpServletRequest request){
        List<Apply> applyList = applyService.지원자현황(1);
        System.out.println("테스트 : " + applyList.get(0).getPass());
        System.out.println("테스트 : " + applyList.get(0).getUser().getName());
        System.out.println("테스트 : " + applyList.get(0).getResume().getUser().getName());
        request.setAttribute("applyList", applyList);

        return "/corporation/corporationSupportDetail";
    }

    // 지원현황 상세보기
    @GetMapping("/seekerSupportDetail")
    public String seekerSupportDetail(HttpServletRequest request){

    Optional<Apply> apply = applyService.지원현황상세보기(1);
    System.out.println("테스트 : " + apply.get().getPass());
    System.out.println("테스트 : " + apply.get().getUser().getName());
    System.out.println("테스트 : " + apply.get().getNotice().getHashAreaList());
    request.setAttribute("apply", apply);
    
    return "/seeker/seekerSupportDetail";

    }

    // 지원자 이력서 상세보기
    @GetMapping("/corporationSupportSeekerList")
    public String corporationSupportSeekerList(HttpServletRequest request){
    Optional<Apply> apply = applyService.지원자이력서조회하기(1);    
    request.setAttribute("apply", apply);
    System.out.println("테스트 : " +apply.get().getUser().getName());
    System.out.println("테스트 : " +apply.get().getResume().getCareer());
    System.out.println("테스트 : " +apply.get().getResume().getHashAreaList());
    System.out.println("테스트 : " +apply.get().getResume().getContent());
    System.out.println("테스트 : " + apply.get().getResume().getHashSkilList().get(0).getSkill().getSkillName());
    System.out.println("테스트 : " + apply.get().getId());

    return "corporation/corporationSupportSeekerList";
}


    // 합격, 불합격, 미정 
    @PostMapping("/apply/pass")
    public String pass(ResumeRequest.PassDTO passDTO){
    System.out.println("나 여기 있어 : "+passDTO.getPass());
    System.out.println("나 여기 있어 : "+passDTO.getApplyId());
    applyService.합격여부결정(passDTO);
    return "redirect:/corporation/corporationSupportDetail";
}

}
