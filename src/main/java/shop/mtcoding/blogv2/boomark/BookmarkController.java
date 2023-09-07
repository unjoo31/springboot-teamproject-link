package shop.mtcoding.blogv2.boomark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
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
        List<User> companyUsers = bookmarkService.북마크기업찾기(sessionUser.getId());

        System.out.println("북마크" + sessionUser.getId());

        List<Map<String, Object>> companyDataList = new ArrayList<>();
        for (User companyuser : companyUsers) {
            Map<String, Object> companyData = new HashMap<>();
            companyData.put("name", companyuser.getName());
            companyData.put("business", companyuser.getBusiness());
            companyData.put("address", companyuser.getAddress());

            companyDataList.add(companyData);
        }

        request.setAttribute("companyDataList", companyDataList);

        return "/seeker/seekerCompanies";
    }

    // 채용공고 북마크
    // 채용공고 북마크
@PostMapping("/applyNoticeBookmark/{noticeId}")
public @ResponseBody String applyNoticeBookmark(@PathVariable Integer noticeId){
    User sessionUser = (User) session.getAttribute("sessionUser");
    System.out.println("여기 값이 머야" + noticeId);
    
    Bookmark bookmark = new Bookmark();
    bookmark.setTargetId(noticeId);
    bookmark.setUser(sessionUser);
    
    boolean mark = bookmarkService.북마크하기(bookmark, sessionUser.getId());
    System.out.println("mark에 머가 들어와?" + mark);
   if (mark == true) {
    return Script.href("/applyNotice/" + noticeId,"북마크 추가 성공");
   }else {
       // 알림 메시지를 표시하고 페이지를 리디렉션합니다.
       throw new MyException("북마크 추가에 실패했습니다.");
   }
}


    // 관심구직자/기술스택
    @GetMapping("/corporationSeeker")
    public String corporationSeeker(HttpServletRequest request){
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 구직자 리스트 보여주기
        // List<User> seekerUsers = bookmarkService.북마크구직자찾기(sessionUser.getId());
        List<Bookmark> seekerUsers = bookmarkService.나를북마크한구직자(sessionUser.getId());
        


        request.setAttribute("seekerUsers", seekerUsers);
        return "/corporation/corporationSeeker";
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
