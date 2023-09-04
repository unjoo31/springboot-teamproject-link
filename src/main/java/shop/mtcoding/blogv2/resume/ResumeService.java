package shop.mtcoding.blogv2.resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.apply.Apply;
import shop.mtcoding.blogv2.apply.ApplyRepository;
import shop.mtcoding.blogv2.area.Area;
import shop.mtcoding.blogv2.area.AreaRepository;
import shop.mtcoding.blogv2.hasharea.HashArea;
import shop.mtcoding.blogv2.hashskil.HashSkil;
import shop.mtcoding.blogv2.notice.Notice;
import shop.mtcoding.blogv2.resume.ResumeRequest.transmitDTO;
import shop.mtcoding.blogv2.skill.Skill;
import shop.mtcoding.blogv2.skill.SkillRepository;
import shop.mtcoding.blogv2.skill.SkillResponse;
import shop.mtcoding.blogv2.user.User;
import shop.mtcoding.blogv2.user.UserRepository;

@Service
public class ResumeService {

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

                // 유저는 이미 만들어 져있다.
                User user = userRepository.findById(userId).get(); 

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

        public Optional<Resume> 이력서조회하기(Integer id) {
             return resumeRepository.findByUserId(id);
        }

        public void 이력서전송하기(transmitDTO transmitDTO, Optional<Resume> resume) {
                System.out.println("업데이트 쿼리 전");
                Apply apply = Apply.builder()
                .pass(transmitDTO.getPass())
                .user(User.builder().id(resume.get().getUser().getId()).build())
                .notice(Notice.builder().id(transmitDTO.getNoticeId()).build())
                .resume(Resume.builder().id(resume.get().getId()).build())
                .build();
                
                applyRepository.save(apply);
                System.out.println("업데이트 쿼리 완료");
               
        }
 


}
