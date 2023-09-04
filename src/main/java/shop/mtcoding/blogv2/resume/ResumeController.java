package shop.mtcoding.blogv2.resume;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.apply.ApplyRequest;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaService;
import shop.mtcoding.blogv2.notice.NoticeRequest;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillService;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ResumeController {


    // DI 주입공간
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private HttpSession session;




    // Get맵핑 호출공간
    @GetMapping("/seekerResumeForm")
    public String resumeForm() {
        return "/seeker/seekerResume";
    }

    @GetMapping("/seekerCompanies")
    public String seekCompaniesForm() {
        return "/seeker/seekerCompanies";
    }

    @GetMapping("/seekerSupportForm")
    public String seekSupportForm() {
        return "/seeker/seekerSupport";
    }

    @GetMapping("/seekerSaveResumeForm")
    public String seekerSaveResumeForm(Model model1, Model model2) {
        List<Skill> skill = skillService.모든스킬가져오기();
        List<Area> area = areaService.모든지역가져오기();
        model1.addAttribute("skills", skill);
        model2.addAttribute("areas", area);
        return "/seeker/seekerSaveResume";
    }



    // Post맵핑 호출공간
    @PostMapping("/seekerSave")
    public String seekerSave(ResumeRequest.ResumeSaveDTO resumeSaveDTO) {
        User sessionUser = (User)session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeSaveDTO, sessionUser.getId());
        return "redirect:/seekerResumeForm";
    }

    // 지원자 이력서 상세보기 
    @GetMapping("/corporationSupportSeekerList")
    public String corporationSupportSeekerList(HttpServletRequest request){
    Optional<Resume> resume = resumeService.이력서조회하기(1);    
    System.out.println(resume.get().getUser().getName());
    System.out.println(resume.get().getUser().getPicUrl());
    System.out.println(resume.get().getHashAreaList());
    request.setAttribute("resume", resume);
    return "corporation/corporationSupportSeekerList";
}

    // 이력서 전송하기 
    @PostMapping("/resume/transmit")
    public String resumeTransmit(ResumeRequest.TransmitDTO transmitDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        Optional<Resume> resume  = resumeService.이력서조회하기(sessionUser.getId());
        System.out.println("테스트 : " + resume.get().getUser().getId());
        System.out.println("테스트 : " + resume.get().getId());
        System.out.println("테스트 : " + transmitDTO.getPass());
        System.out.println("테스트 : " + transmitDTO.getNoticeId());
        resumeService.이력서전송하기(transmitDTO, resume);
        return "redirect:/";
    }

        // 합격, 불합격, 미정 
        @PostMapping("/apply/pass")
        public String pass(ResumeRequest.PassDTO passDTO, HttpServletRequest request){
        System.out.println("나 여기 있어 : "+passDTO.getPass());
        System.out.println("나 여기 있어 : "+passDTO.getApplyId());
        resumeService.합격발표(passDTO);
        return "redirect:/corporation/corporationSupportDetail";
    }

}
