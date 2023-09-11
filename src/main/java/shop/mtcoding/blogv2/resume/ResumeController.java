package shop.mtcoding.blogv2.resume;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaService;
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
    public  String resumeForm(Model model1) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        List<Resume> resumeList = resumeService.이력서존재유무확인(sessionUser.getId());
        List<Skill> skill2 = hashSkilService.이력서선택한스킬목록(sessionUser.getId()); // 여기
        model1.addAttribute("userInfo", user);
        model1.addAttribute("existResume", resumeList);
        model1.addAttribute("selectSkill", skill2);
        return "/seeker/seekerResume";
    }

    @GetMapping("/seekerSupportForm")
    public String seekSupportForm() {
        return "/seeker/seekerSupport";
    }

    // @GetMapping("/seekerCompanies")
    // public String seekCompaniesForm() {
    //     return "/seeker/seekerCompanies";
    // }

    @GetMapping("/seekerSaveResumeForm")
    public String seekerSaveResumeForm(Model model1) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Optional<User> user = userService.회원조회하기(sessionUser.getId());
        List<Skill> skill = skillService.모든스킬가져오기();
        List<Area> area = areaService.모든지역가져오기();
        model1.addAttribute("skills", skill);
        model1.addAttribute("areas", area);
        model1.addAttribute("user", user);
        return "/seeker/seekerSaveResume";
    }


    @GetMapping("/seekerSaveResumeUpdateForm/{resumeId}")
    public String seekerSaveResumeUpdateForm(@PathVariable Integer resumeId, Model model1) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        Resume resume = resumeService.이력서가져오기(resumeId);

        // 스킬처리 로직 [1. 선택한 스킬 2. 선택하지 않은 스킬]
        List<Skill> skill2 = hashSkilService.이력서선택한스킬목록(resumeId);
        List<Skill> restSkill = skillService.이력서나머지스킬가져오기(resumeId); // 선택하고 남은 스킬

        // 지역처리 로직 [1. 선택한 지역 2. 선택하지 않은 지역]
        List<Area> area2 = hashAreaService.이력서선택한지역목룍(resumeId);
        List<Area> restArea = areaService.이력서나머지지역가져오기(resumeId);


        model1.addAttribute("userResume", resume); // 이력서를 보여주는 모델

        model1.addAttribute("selectSkill", skill2); // 선택한 기술을 보여주는 모델
        model1.addAttribute("restSkill", restSkill); // 선택하고 남은 기술을 보여주는 모델

        model1.addAttribute("selectArea", area2); // 선택한 지역을 보여주는 모델
        model1.addAttribute("restArea", restArea); // 선택하고 남은 지역을 보여주는 모델

        return "/seeker/seekerSaveResumeUpdate";
    }

    // Post맵핑 호출공간
    @PostMapping("/seekerSave")
    public String seekerSave(ResumeRequest.ResumeSaveDTO resumeSaveDTO) {
       try {
        User sessionUser = (User) session.getAttribute("sessionUser");
        resumeService.이력서등록(resumeSaveDTO, sessionUser.getId());
        return "redirect:/seekerResumeForm";
       } catch (Exception e) {
        throw new MyException("기술과 지역을 체크해주세요");
       } 
        
    }

    // 이력서 수정
    @PostMapping("/SeekerUpdate/{resumeId}")
    public @ResponseBody String seekerUpdate(@PathVariable Integer resumeId, ResumeRequest.ResumeUpdateDTO resumeUpdateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser"); 
        resumeService.이력서수정(resumeUpdateDTO, resumeId, sessionUser.getId());
        // return "redirect:/seekerResumeForm";
        return Script.href("/seekerResumeForm", "이력서 수정 완료");
    }

    // 이력서 삭제
    @PostMapping("/seekerResume/{resumeId}/delete")
    public @ResponseBody String seekerResumeDelete(@PathVariable Integer resumeId) {
        resumeService.이력서삭제(resumeId);
        // return "redirect:/seekerResumeForm";
        return Script.href("/seekerResumeForm", "이력서 삭제 완료");
    }


    //이력서 전송하기 
    @PostMapping("/resume/transmit")
    public @ResponseBody String resumeTransmit(ResumeRequest.TransmitDTO transmitDTO){
        try {
           User sessionUser = (User) session.getAttribute("sessionUser"); 
        Optional<Resume> resume  = resumeService.이력서조회하기(sessionUser.getId());
        resumeService.이력서전송하기(transmitDTO, resume);
        return Script.href("/", "이력서 전송 완료");  
        } catch (Exception e) {
            User sessionUser = (User) session.getAttribute("sessionUser"); 
            if (sessionUser.getName().isEmpty()) {
                return Script.href("/updateSeekerForm", "회원정보를 작성해주세요.");
            }
            return Script.href("/seekerResumeForm", "이력서를 작성해주세요.");
        }
       
    }


}