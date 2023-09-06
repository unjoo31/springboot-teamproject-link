package shop.mtcoding.blogv2.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Function;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Function function;

    // 회원가입
    @Transactional
    public void 회원가입(JoinDTO joinDTO) {

        User user = User.builder()
            .username(joinDTO.getUsername())
            .password(joinDTO.getPassword())
            .email(joinDTO.getEmail())
            .companyUser(joinDTO.getCompanyUser())
            .build();

        userRepository.save(user);
    }

    // 회원가입 중복체크
    public void 중복체크(String username){
        User user = userRepository.findByUsername(username);

        if (user != null) {
            throw new MyApiException("유저네임을 사용할 수 없습니다.");
        }
    }

    // 로그인
    @Transactional
    public User 로그인(LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if(user == null){
            throw new MyException("유저네임이 없습니다");
        }
        if(!user.getPassword().equals(loginDTO.getPassword())){
            throw new MyException("패스워드가 잘못되었습니다");
        }
        return user;
    }

    // 기업회원, 일반회원 구분
    public boolean 회원분류(String username) {
        User user = userRepository.findByUsername(username);
        System.out.println("로그인 테스트" + user.getCompanyUser());
        return user.getCompanyUser();
    } 

    // 회원정보 조회
    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    // 회원수정
    @Transactional
    public ApiUtil<String> 회원수정(UpdateDTO updateDTO, Integer id) {

        String fileName = function.saveImage(updateDTO.getPic());

        User user = userRepository.findById(id).get();

        user.setPassword(updateDTO.getPassword());
        user.setEmail(updateDTO.getEmail());
        user.setPhonenumber(updateDTO.getPhonenumber());
        user.setAddress(updateDTO.getAddress());
        user.setBusiness(updateDTO.getBusiness());
        user.setForm(updateDTO.getForm());
        user.setPerformance(updateDTO.getPerformance());
        user.setPicUrl(fileName);


        return new ApiUtil<String>(true, "회원정보 수정 완료");
    }

    // 기업회원 조회
    public List<User> 기업회원조회() {
        return userRepository.findByCompanyUserIsTrue();
    }   
}
