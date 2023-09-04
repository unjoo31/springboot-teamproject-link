package shop.mtcoding.blogv2.boomark;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookmarkController {
    
    // 관심구직자/기술스택
    @GetMapping("/seekerCompanies")
    public String seekCompaniesForm() {
        return "/seeker/seekerCompanies";
    }

    // 입사지원 북마크
    @PostMapping("/applyNoticeBookmark")
    public void applyNoticeBookmark(){
        
    }
}
