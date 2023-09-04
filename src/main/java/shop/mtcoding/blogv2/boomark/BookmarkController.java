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

import shop.mtcoding.blogv2.user.User;

@Controller
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HttpSession session;

    
    // 관심구직자/기술스택
    @GetMapping("/seekerCompanies")
    public String seekCompaniesForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 기업 리스트 보여주기
        List<User> companyUsers = bookmarkService.북마크기업찾기(sessionUser.getId());

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

    // 입사지원 북마크
    @PostMapping("/applyNoticeBookmark/{noticeId}")
    public String applyNoticeBookmark(@PathVariable Integer noticeId){
        User sessionUser = (User) session.getAttribute("sessionUser");
        
        Bookmark bookmark = new Bookmark();
        bookmark.setTargetId(noticeId);
        bookmark.setUser(sessionUser);

        bookmarkService.북마크하기(bookmark, sessionUser.getId());
              
        return "redirect:/applyNotice/{noticeId}";
    }
}
