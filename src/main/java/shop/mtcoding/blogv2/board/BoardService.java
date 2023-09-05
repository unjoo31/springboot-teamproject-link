package shop.mtcoding.blogv2.board;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.board.BoardRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @PersistenceContext
    private EntityManager em; // EntityManager를 주입받습니다.

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

    // 게시글 목록보기
    public Page<Board> board(String keyword, Integer page) {
        final int SIZE = 5;
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by("id").descending());

        Page<Board> boardPage;
        if (keyword == null || keyword.isBlank()) {
            boardPage = boardRepository.findAll(pageable);
        } else {
            boardPage = boardRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
        }

        return boardPage;
    }

    @Transactional
    public Board 게시글수정화면보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다");
        }
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
    public void 게시글삭제하기(Integer id) {
        boardRepository.deleteById(id);
    }

    public Optional<Board> 게시글작성자조회하기(Integer id) {
        return boardRepository.findById(id);
    }

    public Board 댓글화면보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다");
        }
    }


} // class