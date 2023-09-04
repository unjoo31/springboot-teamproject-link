package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaRepository;
import shop.mtcoding.blogv2.hasharea.HashArea;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.notice.NoticeRequest.NoticeSaveDTO;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillRepository;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Notice> 채용공고목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findAll(pageable);
    }

    public Page<Notice> 채용공고목록보기(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findByTitleContaining(keyword, pageable);
    }

    // 공고현황 조회할때 사용
    public List<Notice> 공고목록보기() {
        return noticeRepository.findAll();
    }

    public List<Notice> 필터링된공고목록보기(List<String> selectedSkillNames, List<String> selectedAreaNames) {
        List<Notice> filteredNotices = noticeRepository.findByNoticeSkillsOrAreas(selectedSkillNames,
                selectedAreaNames);

        System.out.println("테스트 " + filteredNotices.size());
        return filteredNotices;
    }

    // 입사지원 화면
    public Notice 공고상세보기(int id) {
        Optional<Notice> noticeOP = noticeRepository.findById(id);
        if (noticeOP.isPresent()) {
            return noticeOP.get();
        } else {
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void 채용공고등록(NoticeSaveDTO noticeSaveDTO, Integer userId) {
        List<String> skillList = noticeSaveDTO.getSkilList();
        List<String> areaList = noticeSaveDTO.getAreaList();

        List<HashSkil> hashSkilList = new ArrayList<>();
        List<HashArea> hashAreaList = new ArrayList<>(); // 미리 선언

        // 로그인을 한 상황에서 진행이 되기 때문에 유저는 따로 만들 필요가 없다.
        // 어떤 유저가 채용공고를 작성하는지만 데이터에 들어 가면 된다.
        User user = userRepository.findById(userId).get();

        Notice notice = Notice.builder()
        .user(user)
        .career(noticeSaveDTO.getCareer())
        .title(noticeSaveDTO.getTitle())
        .academicAbility(noticeSaveDTO.getAcademicAbility())
        .salary(noticeSaveDTO.getSalary())
        .typeOfWork(noticeSaveDTO.getTypeOfWork())
        .endDate(noticeSaveDTO.getEndDate())
        .content(noticeSaveDTO.getContent())
        .hashSkilList(hashSkilList)
        .hashAreaList(hashAreaList)
        .build();

               // 채용공고가 만들어진 후에 스킬과 지역이 만들어진다.
                for (String skillName : skillList) {
                        Skill skill = skillRepository.findBySkillName(skillName);

                        HashSkil hashSkil = HashSkil.builder()
                                        .user(user)
                                        .skill(skill)
                                        .notice(notice) 
                                        .build();
                        hashSkilList.add(hashSkil);
                }

                for (String areaName : areaList) {
                        Area area = areaRepository.findByAreaName(areaName);

                        HashArea hashArea = HashArea.builder()
                                        .user(user)
                                        .area(area)
                                        .notice(notice) 
                                        .build();
                        hashAreaList.add(hashArea);
                }

                noticeRepository.save(notice);

    }

}
