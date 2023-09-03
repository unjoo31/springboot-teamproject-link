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

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaService;
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


}
