package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.stream.Collectors;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaResponse;
import shop.mtcoding.blogv2.area.AreaService;
import shop.mtcoding.blogv2.hasharea.HashAreaService;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.hashskil.HashSkilService;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.resume.ResumeRequest;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillService;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserService;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;

    @Autowired
    private HashSkilService hashSkilService;

    @Autowired
    private HashAreaService hashAreaService;

    @Autowired
    private HttpSession session;

    // index 화면
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);

        // 채용공고 리스트 보여주기
        List<Notice> noticeList = noticeService.공고목록보기();

        List<Map<String, Object>> noticeDataList = new ArrayList<>();
        for (Notice notice : noticeList) {
            Map<String, Object> noticeData = new HashMap<>();
            noticeData.put("title", notice.getTitle());
            noticeData.put("user", notice.getUser());
            noticeData.put("notice", notice.getId());
            noticeData.put("hashSkilList", notice.getHashSkilList());
            noticeData.put("hashAreaList", notice.getHashAreaList());

            Date startDate = notice.getCreatedAt();
            Date endDate = notice.getEndDate();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            noticeData.put("timeDifference", timeDifferenceDays);

            noticeDataList.add(noticeData);
        }

        request.setAttribute("noticeDataList", noticeDataList);

        // 기업 리스트 보여주기
        List<User> companyUsers = userService.기업회원조회();

        List<Map<String, Object>> companyDataList = new ArrayList<>();
        for (User companyuser : companyUsers) {
            Map<String, Object> companyData = new HashMap<>();
            companyData.put("name", companyuser.getName());
            companyData.put("business", companyuser.getBusiness());
            companyData.put("address", companyuser.getAddress());
            companyData.put("picUrl", companyuser.getPicUrl());

            companyDataList.add(companyData);
        }

        request.setAttribute("companyDataList", companyDataList);

        return "index";
    }

    // 필터를 통해 채용공고 조회하기
    @GetMapping("/filtered-notices")
    public String filteredNotices(
            @RequestParam(name = "selectedSkills", required = false) List<String> selectedSkillNames,
            @RequestParam(name = "selectedAreas", required = false) List<String> selectedAreaNames,
            HttpServletRequest request) {

        // System.out.println("테스트" + selectedSkills.size());
        // System.out.println("테스트" + selectedAreas.size());

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);

        List<Notice> filteredNotices = noticeService.필터링된공고목록보기(selectedSkillNames, selectedAreaNames);

        List<Map<String, Object>> filterDataList = new ArrayList<>();
        for (Notice filter : filteredNotices) {
            Map<String, Object> filterData = new HashMap<>();
            filterData.put("title", filter.getTitle());
            filterData.put("user", filter.getUser());
            filterData.put("notice", filter.getId());
            filterData.put("hashSkilList", filter.getHashSkilList());
            filterData.put("hashAreaList", filter.getHashAreaList());

            Date startDate = filter.getCreatedAt();
            Date endDate = filter.getEndDate();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            filterData.put("timeDifference", timeDifferenceDays);

            System.out.println("테스트" + filter.getTitle());
            System.out.println("테스트" + filter.getUser());
            System.out.println("테스트" + filter.getHashSkilList());
            System.out.println("테스트" + filter.getHashAreaList());

            filterDataList.add(filterData);
        }

        request.setAttribute("filterDataList", filterDataList);

        // 기업 리스트 보여주기
        List<User> companyUsers = userService.기업회원조회();

        List<Map<String, Object>> companyDataList = new ArrayList<>();
        for (User companyuser : companyUsers) {
            Map<String, Object> companyData = new HashMap<>();
            companyData.put("name", companyuser.getName());
            companyData.put("business", companyuser.getBusiness());
            companyData.put("address", companyuser.getAddress());
            companyData.put("picUrl", companyuser.getPicUrl());

            companyDataList.add(companyData);
        }

        request.setAttribute("companyDataList", companyDataList);

        return "index";
    }

    // 채용공고 페이지
    @GetMapping("/corporationSupport")
    public String corporationSupport(HttpServletRequest request) {

        List<Notice> noticeList = noticeService.공고목록보기();

        List<Map<String, Object>> noticeDataList = new ArrayList<>();
        for (Notice notice : noticeList) {
            Map<String, Object> noticeData = new HashMap<>();
            noticeData.put("title", notice.getTitle());
            noticeData.put("user", notice.getUser());
            noticeData.put("hashSkilList", notice.getHashSkilList());

            Date startDate = notice.getCreatedAt();
            Date endDate = notice.getEndDate();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            noticeData.put("timeDifference", timeDifferenceDays);

            noticeDataList.add(noticeData);
        }

        request.setAttribute("noticeDataList", noticeDataList);
        return "/corporation/corporationSupport";
    }

    // 입사지원 화면
    // 공고 작성 완료 이후에 세션 등록
    @GetMapping("/applyNotice/{noticeId}")
    public String applyNotice(@PathVariable Integer noticeId, HttpServletRequest request){
        Notice notice = noticeService.공고상세보기(noticeId);

        // 마감일 계산을 위해서 변수에 담아주기
        Date startDate = notice.getCreatedAt();
        Date endDate = notice.getEndDate();

        // 마감일 연산
        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
        long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);

        System.out.println("테스트 : " + notice.getHashAreaList().get(0).getArea().getAreaName());
        request.setAttribute("notice", notice);
        request.setAttribute("timeDifferenceDays", timeDifferenceDays);
        return "seeker/applyNotice";
    }

    @GetMapping("/corporationSaveResumeUpdate")
    public String corporationSaveResumeUpdateForm(Model model1, Model model2, Model model3, Model model4,
            Model model5) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        Notice notice = noticeService.채용공고가져오기(sessionUser.getId());

        // 스킬처리 로직 [1. 선택한 스킬 2. 선택하지 않은 스킬]
        List<Skill> skill2 = hashSkilService.선택한스킬목록(sessionUser.getId());
        List<Skill> restSkill = skillService.채용공고나머지스킬가져오기(notice.getId()); // 선택하고 남은 스킬

        // 지역처리 로직 [1. 선택한 지역 2. 선택하지 않은 지역]
        List<Area> area2 = hashAreaService.선택한지역목룍(sessionUser.getId());
        List<Area> restArea = areaService.채용공고나머지지역가져오기(notice.getId());

        model1.addAttribute("userNotice", notice); // 이력서를 보여주는 모델

        model2.addAttribute("selectSkill", skill2); // 선택한 기술을 보여주는 모델
        model3.addAttribute("restSkill", restSkill); // 선택하고 남은 기술을 보여주는 모델

        model4.addAttribute("selectArea", area2); // 선택한 지역을 보여주는 모델
        model5.addAttribute("restArea", restArea); // 선택하고 남은 지역을 보여주는 모델
        return "/corporation/corporationSaveResumeUpdate";
    }

    @GetMapping("/corporationResume")
    public String corporationResume(Model model1, Model model2, Model model3) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        List<Notice> notice = noticeService.채용공고존재유무확인(sessionUser.getId());
        List<Skill> skill = hashSkilService.선택한스킬목록(sessionUser.getId());
        model1.addAttribute("userInfo", user);
        model2.addAttribute("existNotice", notice);
        model3.addAttribute("selectSkill", skill);
        return "/corporation/corporationResume";
    }

    @GetMapping("/corporationSaveResume")
    public String corporationSaveResumeForm(Model model1, Model model2) {
        List<Skill> skill = skillService.모든스킬가져오기();
        List<Area> area = areaService.모든지역가져오기();
        model1.addAttribute("skills", skill);
        model2.addAttribute("areas", area);
        return "/corporation/corporationSaveResume";
    }

    @PostMapping("/corporationSave")
    public String corporationSaveResume(NoticeRequest.NoticeSaveDTO noticeSaveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        noticeService.채용공고등록(noticeSaveDTO, sessionUser.getId());
        return "redirect:/corporationResume";
    }

    @PostMapping("/corporationUpdate")
    public String corporationUpdate(NoticeRequest.NoticeUpdateDTO noticeUpdateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        noticeService.채용공고수정(noticeUpdateDTO, sessionUser.getId());
        return "redirect:/corporationResume";
    }

  
}
