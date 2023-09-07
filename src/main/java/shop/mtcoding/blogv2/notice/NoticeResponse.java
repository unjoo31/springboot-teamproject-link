package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogv2.hashskil.HashSkil;

public class NoticeResponse {
    
    @Getter @Setter
    public static class CorporationResume {
        private Integer noticeId;
        private String title;
        private List<String> skillNames = new ArrayList<>();

        public CorporationResume(Notice notice) {
            this.noticeId = notice.getId();
            this.title = notice.getTitle();

            // List<HashSkil> hashSkilList =  notice.getHashSkilList();
            // for (HashSkil hashSkil : hashSkilList) {
            //     skillNames.add(hashSkil.getSkill().getSkillName());
            // }
            skillNames = notice.getHashSkilList().stream().map(hk -> hk.getSkill().getSkillName()).collect(Collectors.toList());        }
    }
}
