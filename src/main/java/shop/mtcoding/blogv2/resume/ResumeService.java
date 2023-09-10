package shop.mtcoding.blogv2.resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.apply.ApplyRepository;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaRepository;
import shop.mtcoding.blogv2.hasharea.HashArea;
import shop.mtcoding.blogv2.hasharea.HashAreaRepository;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.ResumeRequest.TransmitDTO;
import shop.mtcoding.blogv2.hashskil.HashSkilRepository;
import shop.mtcoding.blogv2.resume.ResumeRequest.ResumeUpdateDTO;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillRepository;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

@Service
public class ResumeService {

        @Autowired
        private HashSkilRepository hashSkilRepository;

        @Autowired
        private HashAreaRepository hashAreaRepository;

        @Autowired
        private ResumeRepository resumeRepository;

        @Autowired
        private SkillRepository skillRepository;

        @Autowired
        private AreaRepository areaRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired 
        private ApplyRepository applyRepository;

        @Transactional
        public void 이력서등록(ResumeRequest.ResumeSaveDTO resumeSaveDTO, Integer userId) {
                List<String> skillList = resumeSaveDTO.getSkilList();
                List<String> areaList = resumeSaveDTO.getAreaList();

                List<HashSkil> hashSkilList = new ArrayList<>();
                List<HashArea> hashAreaList = new ArrayList<>(); // 미리 선언
                
                // 이력서는 그 다음에 만들어진다.
                Resume resume = Resume.builder()
                                .career(resumeSaveDTO.getCareer())
                                .content(resumeSaveDTO.getContent())
                                .user(User.builder().id(userId).build())
                                .hashSkilList(hashSkilList)
                                .hashAreaList(hashAreaList) // 추가한 부분
                                .build();

                // 이력서가 만들어진 후에 스킬과 지역이 만들어진다.
                for (String skillName : skillList) {
                        Skill skill = skillRepository.findBySkillName(skillName);

                        HashSkil hashSkil = HashSkil.builder()
                                        .user(User.builder().id(userId).build())
                                        .skill(skill)
                                        .resume(resume) // 이 부분을 추가하여 resume 정보 설정
                                        .build();
                        hashSkilList.add(hashSkil);
                }

                for (String areaName : areaList) {
                        Area area = areaRepository.findByAreaName(areaName);

                        HashArea hashArea = HashArea.builder()
                                        .user(User.builder().id(userId).build())
                                        .area(area)
                                        .resume(resume)
                                        .build();
                        hashAreaList.add(hashArea);
                }

                resumeRepository.save(resume);
        }

        public Resume 이력서가져오기(Integer resumeId) {
                Optional<Resume> resumeOP = resumeRepository.findById(resumeId);

                return resumeOP.orElse(new Resume());
        }

        public List<Resume> 이력서존재유무확인(Integer userId) {
                List<Resume> resumeList = resumeRepository.findByUserId(userId);
                
                if (resumeList != null) {
                        System.out.println("값이 있습니다.");
                        return resumeList;
                    } else {
                        System.out.println("값이 없습니다.");
                        return null;
                    }
            }


        @Transactional
        public void 이력서수정(ResumeUpdateDTO resumeUpdateDTO, Integer resumeId, Integer sessionUserId) {

                // builder 를 사용하면 데이터가 쌓이기때문에 있는 데이터를 이용하여 값을 수정
                Resume resume = resumeRepository.mfindByResumeId(resumeId);

                resume.setContent(resumeUpdateDTO.getContent());
                resume.setCareer(resumeUpdateDTO.getCareer());
                resume.getUser().setId(sessionUserId);
              
                // 여기서부터 지역과 스킬스택 처리 로직
                List<HashSkil> hashSkilList = new ArrayList<>();
                List<HashArea> hashAreaList = new ArrayList<>();

                // 필요없는 이전 데이터[쓰레기데이터]가 쌓이는 것을 방지 하기위해 삭제 [초기화 과정]
                hashSkilRepository.deleteByResumeId(resume.getId());
                hashAreaRepository.deleteByResumeId(resume.getId());

                List<String> skillList = resumeUpdateDTO.getSkilList();
                List<String> areaList = resumeUpdateDTO.getAreaList();

                for (String skillName : skillList) {
                        Skill skill = skillRepository.findBySkillName(skillName);

                        HashSkil hashSkil = HashSkil.builder()
                                        .user(User.builder().id(resume.getUser().getId()).build())
                                        .skill(skill)
                                        .resume(resume)
                                        .build();
                        hashSkilList.add(hashSkil);
                }

                resume.setHashSkilList(hashSkilList);

                for (String areaName : areaList) {
                        Area area = areaRepository.findByAreaName(areaName);

                        HashArea hashArea = HashArea.builder()
                                        .user(User.builder().id(resume.getUser().getId()).build())
                                        .area(area)
                                        .resume(resume)
                                        .build();
                        hashAreaList.add(hashArea);
                }

                resume.setHashAreaList(hashAreaList);

                // 쿼리를 날리는 갯수를 보면 그렇게 효율적인 방안은 아니지만 데이터베이스의 최적화를 위해선 효율적인 방안입니다.
        }

        public void 이력서전송하기(TransmitDTO transmitDTO, Optional<Resume> resume) {
                Apply apply = Apply.builder()
                .pass(transmitDTO.getPass())
                .user(User.builder().id(resume.get().getUser().getId()).build())
                .notice(Notice.builder().id(transmitDTO.getNoticeId()).build())
                .resume(Resume.builder().id(resume.get().getId()).build())
                .build();
                
                applyRepository.save(apply);
               
        }

             @Transactional
        public void 이력서삭제(Integer resumeId) {
                resumeRepository.deleteById(resumeId);
                System.out.println("이 메소드가 뜨면 삭제되었다는 뜻이야!");
                System.out.println("콘솔창의 딜리트 쿼리와 h2-console 데이터 삭제가 되었는지 확인하세요 !");

        }

        // 이력서 전송을 위해 유저 아이디를 조회
        public Optional<Resume> 이력서조회하기(Integer id) {
        return resumeRepository.findByUser_Id(id);
        }


}
