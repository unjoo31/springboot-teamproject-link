package shop.mtcoding.blogv2.notice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import shop.mtcoding.blogv2.hasharea.HashAreaRepository;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.hashskil.HashSkilRepository;
import shop.mtcoding.blogv2.notice.NoticeRequest.NoticeSaveDTO;
import shop.mtcoding.blogv2.notice.NoticeRequest.NoticeUpdateDTO;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.notice.NoticeRequest.NoticeSaveDTO;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillRepository;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

@Service
public class NoticeService {

    @Autowired
    private HashAreaRepository hashAreaRepository;

    @Autowired
    private HashSkilRepository hashSkilRepository;

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

    public Page<Notice> 공고목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findAll(pageable);
    }

    public Page<Notice> 공고목록보기(Integer page, String keyword) {
        Pageable pageable = PageRequest.of(page, 8, Sort.Direction.DESC, "id");
        return noticeRepository.findByTitleContaining(keyword, pageable);
    }

    public List<Notice> 필터링된공고목록보기(List<String> selectedSkillNames, List<String> selectedAreaNames) {
        List<Notice> filteredNotices = noticeRepository.findByNoticeSkillsOrAreas(selectedSkillNames,
                selectedAreaNames);

        System.out.println("테스트 " + filteredNotices.size());
        return filteredNotices;
    }

    // 입사지원 화면

    public Notice 공고상세보기(Integer noticeId) {
        Optional<Notice> noticeOP = noticeRepository.findById(noticeId);
        if (noticeOP.isPresent()) {
            return noticeOP.get();
        } else {
            throw new MyException(noticeId + "는 찾을 수 없습니다.");
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

        Notice notice = Notice.builder()
                .user(User.builder().id(userId).build())
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
                    .user(User.builder().id(userId).build())
                    .skill(skill)
                    .notice(notice)
                    .build();
            hashSkilList.add(hashSkil);
        }

        for (String areaName : areaList) {
            Area area = areaRepository.findByAreaName(areaName);

            HashArea hashArea = HashArea.builder()
                    .user(User.builder().id(userId).build())
                    .area(area)
                    .notice(notice)
                    .build();
            hashAreaList.add(hashArea);
        }

        noticeRepository.save(notice);

    }

    public List<NoticeResponse.CorporationResume> 채용공고존재유무확인(Integer userId) {
        
        HashSet<Notice> noticeList = noticeRepository.mfindNoticesWithSkillsByUserId(userId);

        if (noticeList != null) {
            List<NoticeResponse.CorporationResume> resp = new ArrayList<>();

            for (Notice notice : noticeList) {
                NoticeResponse.CorporationResume noticeDTO = new NoticeResponse.CorporationResume(notice);
                resp.add(noticeDTO);
            }

            System.out.println("테스트 좋은말로 할때 튀어나온나 : " + resp);

            return resp;

            // Stream 버전이에요! 나중에 프로젝트 이후 공부하실때 꼭 숙지하세요. 코드가 간편해집니다.
            // return noticeList.stream().map(t -> new
            // NoticeResponse.CorporationResume(t)).collect(Collectors.toList());

        } else {
            System.out.println("값이 없습니다.");
            return null;
        }

    }

    public Notice 채용공고가져오기(Integer noticeId) {
        Optional<Notice> noticeOP = noticeRepository.findById(noticeId);

        // Optional에서 Notice 객체를 가져오거나, 없으면 빈 Notice 객체를 반환합니다.
        return noticeOP.orElse(new Notice());
    }

    @Transactional
    public void 채용공고수정(NoticeUpdateDTO noticeUpdateDTO, Integer noticeId) {

        // builder 를 사용하면 데이터가 쌓이기때문에 있는 데이터를 이용하여 값을 수정

        // Optional<Notice> notice = noticeRepository.findById(noticeId);

        Notice notice = noticeRepository.mfindByNoticeId(noticeId);

        notice.getUser().setUsername(noticeUpdateDTO.getUsername());
        notice.setCareer(noticeUpdateDTO.getCareer());
        notice.setTitle(noticeUpdateDTO.getTitle());
        notice.setAcademicAbility(noticeUpdateDTO.getAcademicAbility());
        notice.setSalary(noticeUpdateDTO.getSalary());
        notice.setTypeOfWork(noticeUpdateDTO.getTypeOfWork());
        notice.setEndDate(noticeUpdateDTO.getEndDate());
        notice.setContent(noticeUpdateDTO.getContent());

        // 여기서부터 지역과 스킬스택 처리 로직
        List<HashSkil> hashSkilList = new ArrayList<>();
        List<HashArea> hashAreaList = new ArrayList<>();

        // 필요없는 이전 데이터[쓰레기데이터]가 쌓이는 것을 방지 하기위해 삭제 [초기화 과정]
        hashSkilRepository.deleteByNoticeId(notice.getId());
        hashAreaRepository.deleteByNoticeId(notice.getId());

        List<String> skillList = noticeUpdateDTO.getSkilList();
        List<String> areaList = noticeUpdateDTO.getAreaList();

        for (String skillName : skillList) {
            Skill skill = skillRepository.findBySkillName(skillName);

            HashSkil hashSkil = HashSkil.builder()
                    .user(User.builder().id(notice.getUser().getId()).build())
                    .skill(skill)
                    .notice(notice)
                    .build();
            hashSkilList.add(hashSkil);
        }

        notice.setHashSkilList(hashSkilList);

        for (String areaName : areaList) {
            Area area = areaRepository.findByAreaName(areaName);

            HashArea hashArea = HashArea.builder()
                    .user(User.builder().id(notice.getUser().getId()).build())
                    .area(area)
                    .notice(notice)
                    .build();
            hashAreaList.add(hashArea);
        }

        notice.setHashAreaList(hashAreaList);

        // 쿼리를 날리는 갯수를 보면 그렇게 효율적인 방안은 아니지만 데이터베이스의 최적화를 위해선 효율적인 방안입니다.
    }

    @Transactional
    public void 채용공고삭제(Integer noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    public List<Notice> 등록한공고목록보기(Integer id) {
        return noticeRepository.findByUserId(id);
    }

    public com.mysql.cj.protocol.x.Notice getNoticeById(Integer noticeId) {
        return null;
    }

}
