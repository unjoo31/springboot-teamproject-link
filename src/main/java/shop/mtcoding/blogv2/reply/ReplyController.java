package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReplyController {

@Autowired
  private ReplyService replyService;

    // ===========================================================================================
  
    // 게시글 댓글작성 요청 응답
    @PostMapping("/reply/save")
    public String replyUpdate(@PathVariable Integer id, ReplyRequest.SaveDTO saveDTO) {
        replyService.댓글쓰기(id, saveDTO);
        return "redirect:/detailBoard";
    }

    // 게시글 댓글삭제 요청 응답
    @PostMapping("/reply/delete")
    public String replyDelete(@PathVariable Integer id) {
        replyService.댓글삭제(id);
        return "redirect:/detailBoard";
    }

    // ===========================================================================================

} // class