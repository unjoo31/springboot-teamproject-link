package shop.mtcoding.blogv2.resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaRepository;
import shop.mtcoding.blogv2.hasharea.HashArea;
import shop.mtcoding.blogv2.hasharea.HashAreaRepository;
import shop.mtcoding.blogv2.hashskil.HashSkil;
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

        @Transactional
        public void 이력서등록(ResumeRequest.ResumeSaveDTO resumeSaveDTO, Integer userId) {
                List<String> skillList = resumeSaveDTO.getSkilList();
                List<String> areaList = resumeSaveDTO.getAreaList();

                List<HashSkil> hashSkilList = new ArrayList<>();
                List<HashArea> hashAreaList = new ArrayList<>(); // 미리 선언

                // 유저는 이미 만들어 져있다.
                User user = userRepository.findById(userId).get();
                user.setEmail(resumeSaveDTO.getEmail());
                user.setName(resumeSaveDTO.getName());
                user.setPhonenumber(resumeSaveDTO.getPhoneNumber());
                user.setAddress(resumeSaveDTO.getAddress());
                user.setAge(resumeSaveDTO.getDate());

                // 이력서는 그 다음에 만들어진다.
                Resume resume = Resume.builder()
                                .career(resumeSaveDTO.getCareer())
                                .content(resumeSaveDTO.getContent())
                                .user(user)
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

        public Resume 이력서가져오기(Integer userId) {
                Resume resume = resumeRepository.findById(userId).get();
                return resume;
        }

        public Resume 이력서존재유무확인(Integer userId) {
                Optional<Resume> resumeOP = resumeRepository.findById(userId);
                
                if (resumeOP.isPresent()) {
                    return resumeOP.get();
                } else {
                    // 이력서를 찾을 수 없는 경우에 대한 처리
                    // 예: 빈 이력서를 생성하여 반환하거나 예외를 던질 수 있음
                    return new Resume(); // 빈 이력서를 반환하는 예시
                }
            }


        @Transactional
        public void 이력서수정(ResumeUpdateDTO resumeUpdateDTO, Integer id) {

                // builder 를 사용하면 데이터가 쌓이기때문에 있는 데이터를 이용하여 값을 수정
                Resume resume = resumeRepository.findByUserId(id);

                resume.setContent(resumeUpdateDTO.getContent());
                resume.setCareer(resumeUpdateDTO.getCareer());
                resume.getUser().setName(resumeUpdateDTO.getName());
                resume.getUser().setEmail(resumeUpdateDTO.getEmail());
                resume.getUser().setAddress(resumeUpdateDTO.getAddress());
                resume.getUser().setPhonenumber(resumeUpdateDTO.getPhoneNumber());
                resume.getUser().setAge(resumeUpdateDTO.getDate());

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
                                        .user(User.builder().id(id).build())
                                        .skill(skill)
                                        .resume(resume)
                                        .build();
                        hashSkilList.add(hashSkil);
                }

                resume.setHashSkilList(hashSkilList);

                for (String areaName : areaList) {
                        Area area = areaRepository.findByAreaName(areaName);

                        HashArea hashArea = HashArea.builder()
                                        .user(User.builder().id(id).build())
                                        .area(area)
                                        .resume(resume)
                                        .build();
                        hashAreaList.add(hashArea);
                }

                resume.setHashAreaList(hashAreaList);

                // 쿼리를 날리는 갯수를 보면 그렇게 효율적인 방안은 아니지만 데이터베이스의 최적화를 위해선 효율적인 방안입니다.
        }
}
