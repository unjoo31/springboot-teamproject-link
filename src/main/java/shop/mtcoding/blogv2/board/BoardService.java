package shop.mtcoding.blogv2.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.user.User;

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

    public Board 게시글화면보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다");
        }
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public void 게시글수정하기(Integer id, UpdateDTO updateDTO) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
            boardRepository.save(board);
        } else {
            throw new MyException(id + "는 찾을 수 없습니다");
        }
    }

    @Transactional
    public void 삭제하기(Integer id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(id + "를 찾을 수 없어요");
        }
    }

}
