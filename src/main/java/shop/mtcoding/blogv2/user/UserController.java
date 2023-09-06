package shop.mtcoding.blogv2.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
        System.out.println("로그인 테스트" + isCompanyUser);
        System.out.println("로그인 테스트" + companyUser);
        
        return Script.href("/");
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
    public String update(UserRequest.UpdateDTO updateDTO){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        // 기업회원의 경우 기업 회원정보 수정 페이지로 이동
        if(sessionUser.getCompanyUser() == true){
            return "redirect:/updateCorporationForm";
        }

        if(sessionUser.getCompanyUser() ==  false){
            return "redirect:/updateSeekerForm";
        }
        // 일반회원 경우 일반 회원정보 수정 페이지로 이동
        return "redirect:/";
    }
}
