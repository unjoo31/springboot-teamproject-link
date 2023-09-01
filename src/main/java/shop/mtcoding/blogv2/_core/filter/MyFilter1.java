package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter1 implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        // 세션유저정보가 없거나 브라우저가 아니거나 Postman으로 유입하는 경우
        if(req.getHeader("User-Agent").contains("XBox") || req.getHeader("User-Agent").contains("Postman")){
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = resp.getWriter();

            req.setAttribute("name", "불청객");
            out.println("<h1>나가주세요"+req.getAttribute("name")+"</h1>");
            return;
        }
        
        chain.doFilter(request, response);     
    }        
}
