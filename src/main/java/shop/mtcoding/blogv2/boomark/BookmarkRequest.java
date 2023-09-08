package shop.mtcoding.blogv2.boomark;

import lombok.Getter;
import lombok.Setter;

public class BookmarkRequest {

    @Getter
    @Setter
    public static class BookmarkDTO{
        private Integer userId;
        private Integer noticeId;
    }

}
