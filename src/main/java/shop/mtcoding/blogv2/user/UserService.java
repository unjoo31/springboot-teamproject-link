package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;

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
}
