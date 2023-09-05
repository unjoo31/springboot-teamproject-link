package shop.mtcoding.blogv2.apply;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.apply.ApplyRequest.PassDTO;
import shop.mtcoding.blogv2.resume.Resume;
import shop.mtcoding.blogv2.user.User;


@Service
public class ApplyService {

    @Autowired 
    private ApplyRepository applyRepository;

    public List<Apply> 지원현황보기(Integer id) {
    return applyRepository.findByUserId(id);

    }

    public List<Apply> 지원자현황(Integer id) {
    return applyRepository.findByNoticeId(id);
    }

    public Optional<Apply> 지원현황상세보기(Integer id) {
        Optional<Apply> apply = applyRepository.findById(id);     
        return apply;
    }

    public Optional<Apply> 지원자이력서조회하기(Integer id) {
        return applyRepository.findById(id);
    }

    @Transactional
    public void 합격여부결정(PassDTO passDTO) {
        String newPass = passDTO.getPass();
        Integer applyId = passDTO.getApplyId();

        applyRepository.updatePassById(newPass,applyId);
    }

    public boolean 채용공고지원여부확인(User userId, Integer noticeId){
        int count = applyRepository.noticeApplyCheck(userId, noticeId);
        if(count > 0){
            return true;
        }
        return false;
    }    
}
    

