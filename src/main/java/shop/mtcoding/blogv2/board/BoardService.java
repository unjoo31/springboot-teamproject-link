package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {

      @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        // Board 객체 생성 및 데이터 설정
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build()) // 게시글 작성자 설정
                .build();

        // 생성한 Board 객체를 Repository를 통해 데이터베이스에 저장
        boardRepository.save(board);
    }
}
