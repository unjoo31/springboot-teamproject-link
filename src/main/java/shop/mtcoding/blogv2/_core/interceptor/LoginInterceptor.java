package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        String startEndPoint = request.getRequestURI().split("/")[1];

        if (sessionUser == null) {
            if (startEndPoint.equals("api")) {
                response.setHeader("Content-Type", "application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                ApiUtil<String> apiUtil = new ApiUtil<>(false, "로그인후 사용 가능합니다");
                String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                out.println(responseBody);
            } else {
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(Script.href("/loginForm", "로그인후 사용 가능합니다"));
            }
            return false;
        }
        return true;
    }
    
    
}
