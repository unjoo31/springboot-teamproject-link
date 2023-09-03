package shop.mtcoding.blogv2.apply;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.resume.Resume;

@Service
public class ApplyService {

    @Autowired 
    private ApplyRepository applyRepository;

    public List<Apply> 지원현황보기(Integer id) {
    return applyRepository.findByUserId(id);

    }

    public Optional<Apply> 지원현황상세보기(Integer id) {
    Optional<Apply> apply = applyRepository.findById(id);     
    return apply;
    }

}
    

