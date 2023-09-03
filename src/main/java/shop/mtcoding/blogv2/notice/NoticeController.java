package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.stream.Collectors;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaResponse;
import shop.mtcoding.blogv2.area.AreaService;
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

    // index 화면
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request){

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
    public String filteredNotices(@RequestParam(name = "selectedSkill", required = false) String selectedSkill,
                                @RequestParam(name = "selectedArea", required = false) String selectedArea,
                                HttpServletRequest request) {

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);                            

        List<Notice> filteredNotices = noticeService.필터링된공고목록보기(selectedSkill, selectedArea);

        List<Map<String, Object>> filterDataList = new ArrayList<>();
        for (Notice filter : filteredNotices) {
            Map<String, Object> filterData = new HashMap<>();
            filterData.put("title", filter.getTitle());
            filterData.put("user", filter.getUser());
            filterData.put("hashSkilList", filter.getHashSkilList());
            filterData.put("hashAreaList", filter.getHashAreaList());
            
            Date startDate = filter.getCreatedAt();
            Date endDate = filter.getEndDate();

            long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
            long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
            filterData.put("timeDifference", timeDifferenceDays);

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
    public String corporationSupport(HttpServletRequest request, @RequestParam(name = "selectedSkill", required = false) String selectedSkill,
                                @RequestParam(name = "selectedArea", required = false) String selectedArea){

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

        // 스킬 리스트 보여주기
        List<Skill> skills = skillService.스킬리스트목록보기();
        request.setAttribute("skills", skills);

        // 지역 리스트 보여주기
        List<Area> areas = areaService.지역리스트목록보기();
        request.setAttribute("areas", areas);                            

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
    
    request.setAttribute("noticeDataList", noticeDataList);
    return "/corporation/corporationSupport";
}


    // 입사지원 화면 
    // 공고 작성 완료 이후에 세션 등록
    @GetMapping("/applyNotice")
    public String applyNotice(HttpServletRequest request){
        Notice notice = noticeService.공고상세보기(1);

        // 마감일 계산을 위해서 변수에 담아주기
        Date startDate = notice.getCreatedAt();
        Date endDate = notice.getEndDate();

        // 마감일 연산
        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();
        long timeDifferenceDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);
       
        
        System.out.println("테스트 : "+notice.getHashAreaList().get(0).getArea().getAreaName());
        request.setAttribute("notice", notice);
        request.setAttribute("timeDifferenceDays", timeDifferenceDays);
        return "seeker/applyNotice";
    }

  
}
