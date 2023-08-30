package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    // 로그인 화면
    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:";
    }
  
    // 로그아웃
    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }    
}
