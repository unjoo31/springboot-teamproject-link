package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.stream.Collectors;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.apply.ApplyRequest;
import shop.mtcoding.blogv2.apply.ApplyService;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaResponse;
import shop.mtcoding.blogv2.area.AreaService;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.boomark.BookmarkService;
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
    private BookmarkService bookmarkService;

    @Autowired
    private ApplyService applyService;

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

        User sessionUser = (User) session.getAttribute("sessionUser");

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);

        // 채용공고 리스트 보여주기

        Page<Notice> noticeList = null;

        if (keyword.isBlank()) {
            noticeList = noticeService.공고목록보기(page);
        } else {
            noticeList = noticeService.공고목록보기(page, keyword);
            request.setAttribute("keyword", keyword);
        }

        // List<Notice> noticeList = noticeService.공고목록보기();

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
        request.setAttribute("prevPage", noticeList.getNumber() - 1);
        request.setAttribute("nextPage", noticeList.getNumber() + 1);

        // 기업 리스트 보여주기
        List<User> companyUsers = userService.기업회원조회();
        List<Map<String, Object>> companyDataList = new ArrayList<>();
        for (User companyuser : companyUsers) {
            Map<String, Object> companyData = new HashMap<>();
            companyData.put("name", companyuser.getName());
            companyData.put("business", companyuser.getBusiness());
            companyData.put("address", companyuser.getAddress());
            companyData.put("picUrl", companyuser.getPicUrl());
            companyData.put("id", companyuser.getId());
            companyDataList.add(companyData);
        }

        // 유저 리스트 보여주기
        List<User> users = userService.일반회원조회();
        List<Map<String, Object>> userDataList = new ArrayList<>();
        for (User user : users) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user.getName());
            userData.put("business", user.getBusiness());
            userData.put("address", user.getAddress());
            userData.put("picUrl", user.getPicUrl());
            userData.put("id", user.getId());
            userDataList.add(userData);
        }
        request.setAttribute("userDataList", userDataList);
        request.setAttribute("companyDataList", companyDataList);

        int companyUser = 1;

        if (sessionUser == null) {
            return "index";
        }
        if (sessionUser.getCompanyUser() == true) {
            request.setAttribute("companyUser", companyUser);
        }

        return "index";
    }

    // 필터를 통해 채용공고 조회하기
    @GetMapping("/filtered-notices")
    public String filteredNotices(
            @RequestParam(name = "selectedSkills", required = false) List<String> selectedSkillNames,
            @RequestParam(name = "selectedAreas", required = false) List<String> selectedAreaNames,
            HttpServletRequest request) {

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);

        List<Notice> filteredNotices = noticeService.필터링된공고목록보기(selectedSkillNames, selectedAreaNames);

        List<Map<String, Object>> filterDataList = new ArrayList<>();
        Set<Integer> addedNoticeIds = new HashSet<>(); // 이미 추가된 공고 ID를 추적하기 위한 Set

        for (Notice filter : filteredNotices) {
            // 중복되는 공고인지 확인
            if (!addedNoticeIds.contains(filter.getId())) {
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

                filterDataList.add(filterData);

                // 이 공고 ID를 Set에 추가하여 중복 추가를 방지
                addedNoticeIds.add(filter.getId());
            }
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
    @GetMapping("/corporationSupport/{id}")
    public String corporationSupport(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<Notice> noticeList = noticeService.등록한공고목록보기(id);
        List<Map<String, Object>> noticeDataList = new ArrayList<>();

        int expiredNoticeCount = 0;

        for (Notice notice : noticeList) {
            Map<String, Object> noticeData = new HashMap<>();
            noticeData.put("title", notice.getTitle());
            noticeData.put("user", notice.getUser());
            noticeData.put("hashSkilList", notice.getHashSkilList());
            noticeData.put("noticeId", notice.getId());

            Date startDate = notice.getCreatedAt();
            Date endDate = notice.getEndDate();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            if (timeDifferenceDays <= 0) {
                expiredNoticeCount++;
            }
            noticeData.put("timeDifference", timeDifferenceDays);
            System.out.println("테스트 : " + expiredNoticeCount);
            noticeDataList.add(noticeData);

        }
        request.setAttribute("ongoing", noticeDataList.size() - expiredNoticeCount);
        request.setAttribute("expiredNoticeCount", expiredNoticeCount);
        request.setAttribute("count", noticeDataList.size());
        request.setAttribute("noticeDataList", noticeDataList);
        return "/corporation/corporationSupport";
    }

    // 입사지원 화면
    // 공고 작성 완료 이후에 세션 등록
    @GetMapping("/applyNotice/{noticeId}")
    public String applyNotice(@PathVariable Integer noticeId, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyException("로그인이 필요합니다.");
        }
        User userId = userService.회원정보보기(sessionUser.getId());
        Notice notice = noticeService.공고상세보기(noticeId);
        Boolean ischeck = applyService.채용공고지원여부확인(userId, noticeId);

        // 마감일 계산을 위해서 변수에 담아주기
        Date startDate = notice.getCreatedAt();
        Date endDate = notice.getEndDate();

        // 마감일 연산
        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
        long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);

        int companyUser = 1;

        if (sessionUser.getCompanyUser() == true) {
            request.setAttribute("companyUser", companyUser);
        }

        request.setAttribute("notice", notice);
        request.setAttribute("timeDifferenceDays", timeDifferenceDays);
        request.setAttribute("ischeck", ischeck);
        return "seeker/applyNotice";
    }

    @GetMapping("/corporationSaveResumeUpdate/{noticeId}")
    public String corporationSaveResumeUpdateForm(@PathVariable Integer noticeId, Model model1) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        Notice notice = noticeService.채용공고가져오기(noticeId);

        // 스킬처리 로직 [1. 선택한 스킬 2. 선택하지 않은 스킬]
        List<Skill> skill2 = hashSkilService.채용공고선택한스킬목록(noticeId);
        List<Skill> restSkill = skillService.채용공고나머지스킬가져오기(notice.getId()); // 선택하고 남은 스킬

        // 지역처리 로직 [1. 선택한 지역 2. 선택하지 않은 지역]
        List<Area> area2 = hashAreaService.채용공고선책한지역목록(notice.getId());
        List<Area> restArea = areaService.채용공고나머지지역가져오기(notice.getId());

        model1.addAttribute("userNotice", notice); // 이력서를 보여주는 모델
        System.out.println("Noticie 테스트 : " + notice.getEndDate());

        model1.addAttribute("selectSkill", skill2); // 선택한 기술을 보여주는 모델
        model1.addAttribute("restSkill", restSkill); // 선택하고 남은 기술을 보여주는 모델

        model1.addAttribute("selectArea", area2); // 선택한 지역을 보여주는 모델
        model1.addAttribute("restArea", restArea); // 선택하고 남은 지역을 보여주는 모델
        return "/corporation/corporationSaveResumeUpdate";
    }

    @GetMapping("/corporationResume")
    public String corporationResume(Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        List<NoticeResponse.CorporationResume> noticeList = noticeService.채용공고존재유무확인(sessionUser.getId());
        // List<Skill> skill = hashSkilService.채용공고선택한스킬목록2(sessionUser.getId());
        model.addAttribute("userInfo", user);
        model.addAttribute("noticeList", noticeList);
        return "/corporation/corporationResume";
        // return noticeList;
    }

    @GetMapping("/corporationSaveResume")
    public String corporationSaveResumeForm(Model model1) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        List<Skill> skill = skillService.모든스킬가져오기();
        List<Area> area = areaService.모든지역가져오기();
        model1.addAttribute("userInfo", user);
        model1.addAttribute("skills", skill);
        model1.addAttribute("areas", area);
        return "/corporation/corporationSaveResume";
    }

    
    @PostMapping("/corporationSave")
    public String corporationSaveResume(NoticeRequest.NoticeSaveDTO noticeSaveDTO) {
        try {
        User sessionUser = (User) session.getAttribute("sessionUser");
        noticeService.채용공고등록(noticeSaveDTO, sessionUser.getId());
        return "redirect:/corporationResume";    
        } catch (Exception e) {
           throw new MyException("기술과 지역을 체크해주세요");
        }
        
    }

    @PostMapping("/corporationUpdate/{noticeId}")
    public String corporationUpdate(@PathVariable Integer noticeId, NoticeRequest.NoticeUpdateDTO noticeUpdateDTO) {
        noticeService.채용공고수정(noticeUpdateDTO, noticeId);
        return "redirect:/corporationResume";
    }

    @PostMapping("/corporationNoticeDelete/{noticeId}/delete")
    public String corporationDelete(@PathVariable Integer noticeId) {
        noticeService.채용공고삭제(noticeId);
        return "redirect:/corporationResume";
    }

}
