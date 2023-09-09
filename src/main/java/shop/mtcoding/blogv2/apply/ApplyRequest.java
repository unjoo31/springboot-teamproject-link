package shop.mtcoding.blogv2.apply;

import lombok.Getter;
import lombok.Setter;

public class ApplyRequest {
    
    
    @Getter
    @Setter
    public static class PassDTO{
        private String pass;
        private Integer applyId;
        private Integer noticeId;

    }

}
