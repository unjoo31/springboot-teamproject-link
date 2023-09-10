package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogv2.hashskil.HashSkil;

public class NoticeResponse {

    @Getter
    @Setter
    public static class CorporationResume{
        private Integer noticeId;
        private String title;
        private Set<String> skillNames = new HashSet<>();


        public CorporationResume(Notice notice) {
            this.noticeId = notice.getId();
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
