package shop.mtcoding.blogv2.apply;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import shop.mtcoding.blogv2.resume.Resume;

@Controller
public class ApplyController {

    @Autowired 
    private ApplyService applyService;

    // 이력서 등록 현황
    @GetMapping("/seekerSupport")
    public String seekerSupport(HttpServletRequest request){
    // 공고현황 화면에 보여줄 값 담기
    List<Apply> applyList = applyService.지원현황보기(1);
    
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

}
