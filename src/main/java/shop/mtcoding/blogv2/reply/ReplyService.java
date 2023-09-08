package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
   

    @Transactional
    public void 댓글쓰기(SaveDTO saveDTO, Integer sessionId) {
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();

         if (saveDTO.getComment() == null || saveDTO.getComment().isEmpty()) {
            throw new MyException("내용을 입력하세요");
        }
        
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();
        replyRepository.save(reply);
    }
    
     @Transactional
    public void 댓글삭제(Integer id, Integer sessionUserId) {

        Optional<Reply> replyOP = replyRepository.findById(id);

        if (replyOP.isEmpty()) {
            throw new MyApiException("삭제할 댓글이 없습니다");
        }

        Reply reply = replyOP.get(); 

        if (reply.getUser().getId() != sessionUserId) {
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다");
        }

        replyRepository.deleteById(id);
    }

   
}