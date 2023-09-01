package shop.mtcoding.blogv2.resume;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResumeController {
    
    @GetMapping("seekerSupport")
    public String seekerSupport(){
        return "/seeker/seekerSupport";
    }

}
