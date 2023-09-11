package shop.mtcoding.blogv2.board;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.user.User;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpSession session;

    // 고객센터 글목록 화면 호출
    @GetMapping("/board")
    public String board(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") Integer page,
            HttpServletRequest request) {

        Page<Board> boardPage = boardService.board(keyword, page);

        request.setAttribute("keyword", keyword);
        request.setAttribute("boardPG", boardPage);
        request.setAttribute("prevPage", boardPage.hasPrevious() ? page - 1 : 0);
        request.setAttribute("nextPage", boardPage.hasNext() ? page + 1 : 0);
        request.setAttribute("first", !boardPage.hasPrevious());
        request.setAttribute("last", !boardPage.hasNext());
        request.setAttribute("totalPage", boardPage.getTotalPages());
        request.setAttribute("totalCount", boardPage.getTotalElements());
        return "board/board";
    }

    // 게시글 글쓰기 화면 호출
    @GetMapping("/board/saveBoard")
    public String saveBoard(HttpServletRequest request) {
        return "board/saveBoard";
    }

    // 게시글 글쓰기 요청 응답
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.글쓰기(saveDTO, sessionUser.getId());
        return "redirect:/board";
    }

    // 게시글 글수정,삭제 화면 호출
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Optional<Board> board = boardService.게시글작성자조회하기(id);
        if (board.get().getUser().getId() != sessionUser.getId()) {
            throw new MyException("본인의 글만 수정 가능 합니다");
        }

        Board boardUpdate = boardService.게시글수정화면보기(id);
        request.setAttribute("board", boardUpdate);
        return "board/updateForm";
    }

    // 게시글 글수정 요청 응답
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO) {
        boardService.게시글수정하기(id, updateDTO);
        return "redirect:/board";
    }

    // 게시글 글삭제 요청 응답
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        boardService.게시글삭제하기(id);
        return "redirect:/board";
    }

    // 게시글 댓글 화면 호출 - 작성,삭제 post요청은 reply컨트롤러
    @GetMapping("/board/{id}/detailBoard")
    public String detailForm(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardService.댓글화면보기(id);
        request.setAttribute("board", board);
        return "board/detailBoard";
    }

} // class
