package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    // 로그인
    @Transactional
    public User 로그인(LoginDTO loginDTO){
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 유저네임 검증
        if(user == null){
            throw new MyException("유저네임이 없습니다");
        }

        // 패스워드 검증
        if(!user.getPassword().equals(loginDTO.getPassword())){
            throw new MyException("패스워드가 잘못되었습니다");
        }

        // 로그인 성공
        return user;
    }
}
