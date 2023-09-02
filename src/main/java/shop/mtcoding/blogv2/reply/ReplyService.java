package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 댓글쓰기(Integer sessionId, SaveDTO saveDTO) {
        Board board = Board.builder().id(saveDTO.getBoardId()).build();

        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .build();
        replyRepository.save(reply);
    }
    @Transactional
    public void 댓글삭제(Integer id) {
       
        replyRepository.deleteById(id);
    }

}