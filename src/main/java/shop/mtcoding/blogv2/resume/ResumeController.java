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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.apply.ApplyRequest;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaService;
import shop.mtcoding.blogv2.notice.NoticeRequest;
import shop.mtcoding.blogv2.hasharea.HashAreaService;
import shop.mtcoding.blogv2.hashskil.HashSkilService;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillService;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserService;

@Controller
public class ResumeController {

    // DI 주입공간
    @Autowired
    private ResumeService resumeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private HashSkilService hashSkilService;

    @Autowired
    private HashAreaService hashAreaService;

    @Autowired
    private HttpSession session;

    // Get맵핑 호출공간
    @GetMapping("/seekerResumeForm")
    public String resumeForm(Model model1, Model model2, Model model3, Integer userId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        Resume resume = resumeService.이력서존재유무확인(sessionUser.getId());
        List<Skill> skill2 = hashSkilService.선택한스킬목록(sessionUser.getId());
        model1.addAttribute("userInfo", user);
        model2.addAttribute("existResume", resume);
        model3.addAttribute("selectSkill", skill2);
        return "/seeker/seekerResume";
    }

    @GetMapping("/seekerSupportForm")
    public String seekSupportForm() {
        return "/seeker/seekerSupport";
    }

    @GetMapping("/seekerCompanies")
    public String seekCompaniesForm() {
        return "/seeker/seekerCompanies";
    }

    @GetMapping("/seekerSaveResumeForm")
    public String seekerSaveResumeForm(Model model1, Model model2) {
        List<Skill> skill = skillService.모든스킬가져오기();
        List<Area> area = areaService.모든지역가져오기();
        model1.addAttribute("skills", skill);
        model2.addAttribute("areas", area);
        return "/seeker/seekerSaveResume";
    }

    @GetMapping("/seekerSaveResumeUpdateForm")
    public String seekerSaveResumeUpdateForm(Model model1, Model model2, Model model3, Model model4, Model model5) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        Resume resume = resumeService.이력서가져오기(sessionUser.getId());

        // 스킬처리 로직 [1. 선택한 스킬 2. 선택하지 않은 스킬]
        List<Skill> skill2 = hashSkilService.선택한스킬목록(sessionUser.getId());
        List<Skill> restSkill = skillService.이력서나머지스킬가져오기(resume.getId()); // 선택하고 남은 스킬

        // 지역처리 로직 [1. 선택한 지역 2. 선택하지 않은 지역]
        List<Area> area2 = hashAreaService.선택한지역목룍(sessionUser.getId());
        List<Area> restArea = areaService.나머지지역가져오기(resume.getId());

        model1.addAttribute("userResume", resume); // 이력서를 보여주는 모델

        model2.addAttribute("selectSkill", skill2); // 선택한 기술을 보여주는 모델
        model3.addAttribute("restSkill", restSkill); // 선택하고 남은 기술을 보여주는 모델

        model4.addAttribute("selectArea", area2); // 선택한 지역을 보여주는 모델
        model5.addAttribute("restArea", restArea); // 선택하고 남은 지역을 보여주는 모델
        return "/seeker/seekerSaveResumeUpdate";
    }

    // Post맵핑 호출공간
    @PostMapping("/seekerSave")
    public String seekerSave(ResumeRequest.ResumeSaveDTO resumeSaveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeSaveDTO, sessionUser.getId());
        return "redirect:/seekerResumeForm";
    }

    @PostMapping("/SeekerUpdate")
    public String seekerUpdate(ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("sessionUser : " + sessionUser.getId());
        resumeService.이력서수정(resumeUpdateDTO, sessionUser.getId());
        return "redirect:/seekerResumeForm";
    }

    @PostMapping("/seekerResume/{resumeId}/delete")
    public String seekerResumeDelete(@PathVariable Integer resumeId) {
        System.out.println("컨트롤러에 진입합니다");
        resumeService.이력서삭제(resumeId);
        System.out.println("서비스 계층 진입 후 컨트롤러 복귀완료");
        return "redirect:/seekerResumeForm";
    }





    //이력서 전송하기 
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


}