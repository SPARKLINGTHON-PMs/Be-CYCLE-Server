package gdsc.sparkling_thon.user.service;

import gdsc.sparkling_thon.exception.AppException;
import gdsc.sparkling_thon.exception.ErrorCode;
import gdsc.sparkling_thon.user.domain.UserEntity;
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
                .ifPresent(userEntity -> {
                    throw new AppException(ErrorCode.USERID_DUPICATED);
                });
    }

    public void createUser(UserRequest request){
        UserEntity userEntity = request.toEntity(request.getPwd());
        userRepository.save(userEntity);
    }

    public String login(UserLoginRequest request) {
        String loginId = request.getLoginId();
        String pwd = request.getPwd();
        UserEntity userEntity = userRepository.findByTelNum(loginId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
        if (!pwd.equals(userEntity.getPwd())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        return userEntity.getTelNum();
    }


}
