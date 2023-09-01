package shop.mtcoding.blogv2.notice;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") Integer page, HttpServletRequest request){

        // 채용공고 리스트 보여주기
        Page<Notice> noticePG = null; 

        if(keyword.isBlank()){
            noticePG = noticeService.채용공고목록보기(page);
        }else{
            noticePG = noticeService.채용공고목록보기(page, keyword);
            request.setAttribute("keyword", keyword);
        }

        List<List<String>> skillsList = noticeService.스킬리스트보기(noticePG);

        request.setAttribute("noticePG", noticePG);
        request.setAttribute("prevPage", noticePG.getNumber() - 1);
        request.setAttribute("nextPage", noticePG.getNumber() + 1);
        request.setAttribute("skillsList", skillsList);

        for (int i = 0; i < skillsList.size(); i++) {
            System.out.println("테스트 스킬" + (i + 1) + ": " + skillsList.get(i));
        }

        for (Notice notice : noticePG) {
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getContent());
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getTitle());
            System.out.println("테스트 공고" + notice.getId() + ": " + notice.getUser().getUsername());
        }

        // 기업리스트 보여주기

        return "index";
    }
}
