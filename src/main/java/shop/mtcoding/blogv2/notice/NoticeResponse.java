package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogv2.hashskil.HashSkil;

public class NoticeResponse {

    @Getter
    @Setter
    public static class CorporationResume{
        private Integer notiecId;
        private String title;
        private List<String> skillNames = new ArrayList<>();


        public CorporationResume(Notice notice) {
            this.notiecId = notice.getId();
            this.title = notice.getTitle();
            
            List<HashSkil> hashskilList = notice.getHashSkilList();
            for (HashSkil hashSkil : hashskilList) {
                skillNames.add(hashSkil.getSkill().getSkillName());
            }

            // stream은 프로젝트 이후 연습하시면 됩니다! - 선생님이 알려주셨어요!
            // skillNames = notice.getHashSkilList().stream().map(
            //     hk -> hk.getSkill().getSkillName()).collect(Collectors.toList());
        }

    
    }
    
}
