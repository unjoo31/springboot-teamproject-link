package shop.mtcoding.blogv2.boomark;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.cj.protocol.x.Notice;

import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.hashskil.HashSkilService;
import shop.mtcoding.blogv2.notice.NoticeService;
import shop.mtcoding.blogv2.user.User;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HashSkilService hashSkilService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private HttpSession session;

    // 관심기업/기술스택
    @GetMapping("/seekerCompanies")
    public String seekCompaniesForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 기업 리스트 보여주기
        // List<User> companyUsers = bookmarkService.북마크기업찾기(sessionUser.getId());
        List<User> companyUsers = bookmarkService.관심기업찾기(sessionUser.getId());
        
        List<Map<String, Object>> companyDataList = new ArrayList<>();
        for (User company : companyUsers) {
            Map<String, Object> companyData = new HashMap<>();
            companyData.put("name", company.getName());
            companyData.put("business", company.getBusiness());
            companyData.put("address", company.getAddress());
            companyData.put("id", company.getId());
            companyDataList.add(companyData);
        }
        request.setAttribute("companyDataList", companyDataList);
        
        return "/seeker/seekerCompanies";
    }



    // 관심구직자/기술스택
    @GetMapping("/corporationSeeker")
    public String corporationSeeker(HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 구직자 리스트 보여주기
        // List<User> Users = bookmarkService.북마크구직자찾기(sessionUser.getId());
        List<User> users = bookmarkService.관심구직자찾기(sessionUser.getId());
        // bookmarkService.구직자정보찾기();
        List<Map<String, Object>> userDataList = new ArrayList<>();
        for (User user1 : users) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user1.getName());
            userData.put("business", user1.getBusiness());
            userData.put("address", user1.getAddress());
            userData.put("id", user1.getId());
            userDataList.add(userData);
        }


        request.setAttribute("userDataList", userDataList);
        return "/corporation/corporationSeeker";
    }

    
    // 채용공고 북마크
    @PostMapping("/applyNoticeBookmark")
    public @ResponseBody String applyNoticeBookmark(BookmarkRequest.BookmarkDTO bookmarkDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        System.out.println("북마크 테스트 1 :" + bookmarkDTO.getUserId());
        System.out.println("북마크 테스트 2 :" + sessionUser.getId());
    

        bookmarkService.북마크하기(bookmarkDTO, sessionUser.getId());
        return Script.href("/corporationDetail/" + bookmarkDTO.getUserId(), "북마크 등록 완료");      
        // return "redirect:/corporationDetail/" + bookmarkDTO.getUserId();
    }

    @GetMapping("/api/seekerCompanies")
    public @ResponseBody List<HashSkil> seekerCompanies(@RequestParam(defaultValue = "")  List<String> selectedSkills){
        List<HashSkil> HashSkilList = hashSkilService.선택한스킬로기업조회하기(selectedSkills);
        return HashSkilList;
    }

    @GetMapping("/api/corporationSeeker")
    public @ResponseBody List<HashSkil> corporationSeeker(@RequestParam(defaultValue = "")  List<String> selectedSkills){
        List<HashSkil> HashSkilList = hashSkilService.선택한스킬로이력서조회하기(selectedSkills);
        return HashSkilList;
    }



}
