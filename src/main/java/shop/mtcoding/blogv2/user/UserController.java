package shop.mtcoding.blogv2.user;


import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.boomark.BookmarkService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private HttpSession session;

    // 회원가입 화면
    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO){
        userService.회원가입(joinDTO);
        return "redirect:/loginForm";
    }

    // 회원가입 중복체크
    @GetMapping("/api/check")
    public @ResponseBody ApiUtil<String> check(String username){
        if(username.isBlank()){
            throw new MyApiException("유저네임을 입력하세요.");
        }
        userService.중복체크(username);
        return new ApiUtil<String>(true, "중복체크 완료");
    }

    // 로그인 화면
    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }


    // 로그인
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        // 로그인 기능
        User sessionUser = userService.로그인(loginDTO);

        session.setAttribute("sessionUser", sessionUser);

        // 로그인시 기업회원, 일반회원 구분
        boolean isCompanyUser = userService.회원분류(loginDTO.getUsername());
        int companyUser = 1;
        
        if(isCompanyUser == true){
            request.setAttribute("companyUser", companyUser);
        }
        
        return Script.href("/", "로그인 되었습니다.");
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }    

    // 일반회원 회원정보 화면
    @GetMapping("/updateSeekerForm")
    public String updateSeekerForm(HttpServletRequest request){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "seeker/seekerUpdateImformation";
    }

    // 기업회원 회원정보 화면
    @GetMapping("/updateCorporationForm")
    public String updateCorporationForm(HttpServletRequest request){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "corporation/corporationUpdateImformation";
    }

    //회원정보 업데이트
    @PostMapping("/user/update")
    public @ResponseBody String update(UserRequest.UpdateDTO updateDTO){
        User sessionUser = (User)session.getAttribute("sessionUser");
        try {   
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);
        // 기업회원의 경우 기업 회원정보 수정 페이지로 이동
        if(sessionUser.getCompanyUser() == true){
            return Script.href("/updateCorporationForm", "회원정보 수정완료");
        }
        if(sessionUser.getCompanyUser() ==  false){
            return Script.href("/updateSeekerForm", "회원정보 수정완료");
        }
        
        // 일반회원 경우 일반 회원정보 수정 페이지로 이동
       
         } catch (Exception e) {
           return Script.href("/", "에러가 발생했습니다. 문의 부탁드립니다.");
        }
           
        return "redirect:/";  
    }

    // 기업 유저 상세보기
    @GetMapping("/corporationDetail/{id}")
    public String corporationDetail(@PathVariable Integer id, HttpServletRequest request){
    Optional<User> user = userService.회원조회하기(id);
    User userId = userService.회원정보보기(id);
    Boolean isBookmark = bookmarkService.북마크여부확인(userId);
    System.out.println("북마크테스트" + isBookmark);
    request.setAttribute("user", user);
    request.setAttribute("isBookmark", isBookmark);
    System.out.println("북마크 테스트 0 : " + user.get().getId());
    
    return "/seeker/corporationDetail";
    }

    

    // 일반 유저 상세보기 
    @GetMapping("/userDetail/{id}")
    public String userDetail(@PathVariable Integer id, HttpServletRequest request){
    
    Optional<User> user = userService.회원조회하기(id);
    User userId = userService.회원정보보기(id);
    Boolean isBookmark = bookmarkService.북마크여부확인(userId);
    System.out.println("북마크테스트" + isBookmark);
    request.setAttribute("user", user);
    request.setAttribute("isBookmark", isBookmark);
    System.out.println("북마크 테스트 0 : " + user.get().getId());
    
    return "/seeker/userDetail";
    }
}
