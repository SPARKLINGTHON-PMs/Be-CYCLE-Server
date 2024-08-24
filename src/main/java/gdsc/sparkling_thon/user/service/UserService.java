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

    private final UserRepository userRepository;

    // 회원가입 처리 로직
    public void join(UserRequest request) {
        String loginTelNum = request.getTelNum();

        // 중복 사용자 체크
        userDuplicateCheck(loginTelNum);
        // 사용자 생성
        createUser(request);
    }

    // 중복 사용자 체크 메소드
    private void userDuplicateCheck(String loginTelNum) {
        userRepository.findByTelNum(loginTelNum)
            .ifPresent(userEntity -> {
                throw new AppException(ErrorCode.USERID_DUPICATED);
            });
    }

    // 사용자 생성 메소드
    private void createUser(UserRequest request) {
        UserEntity userEntity = request.toEntity(request.getPwd());
        userRepository.save(userEntity);
    }

    // 로그인 처리 로직
    public String login(UserLoginRequest request) {
        String loginId = request.getLoginId();
        String pwd = request.getPwd();

        // 사용자를 전화번호로 찾기
        UserEntity userEntity = userRepository.findByTelNum(loginId)
            .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));

        // 비밀번호 검증
        if (!pwd.equals(userEntity.getPwd())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        // 로그인 성공 시 사용자 식별자 반환
        return userEntity.getTelNum();
    }
}
