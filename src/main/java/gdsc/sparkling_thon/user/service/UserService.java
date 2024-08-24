package gdsc.sparkling_thon.user.service;

import gdsc.sparkling_thon.exception.AppException;
import gdsc.sparkling_thon.exception.ErrorCode;
import gdsc.sparkling_thon.user.domain.User;
import gdsc.sparkling_thon.user.dto.request.UserLoginRequest;
import gdsc.sparkling_thon.user.dto.request.UserRequest;
import gdsc.sparkling_thon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public void join(UserRequest request) {
        String loginTelNum = request.getTelNum();

        userDuplicateCheck(loginTelNum);
        createUser(request);
    }

    public void userDuplicateCheck(String login_tel_num){
        userRepository.findByTelNum(login_tel_num)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERID_DUPICATED);
                });
    }

    public void createUser(UserRequest request){
        User user = request.toEntity(request.getPwd());
        userRepository.save(user);
    }

    public String login(UserLoginRequest request) {
        String loginId = request.getLoginId();
        String pwd = request.getPwd();
        User user = userRepository.findByTelNum(loginId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
        if (!pwd.equals(user.getPwd())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        return user.getTelNum();
    }


}
