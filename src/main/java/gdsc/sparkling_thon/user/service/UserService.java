package gdsc.sparkling_thon.user.service;

import gdsc.sparkling_thon.book.domain.CategoryEntity;
import gdsc.sparkling_thon.book.repository.CategoryRepository;
import gdsc.sparkling_thon.exception.AppException;
import gdsc.sparkling_thon.exception.ErrorCode;
import gdsc.sparkling_thon.user.domain.UserCategoryEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import gdsc.sparkling_thon.user.dto.request.CategoryRequest;
import gdsc.sparkling_thon.user.dto.request.UserLoginRequest;
import gdsc.sparkling_thon.user.dto.request.UserRequest;
import gdsc.sparkling_thon.user.dto.response.UserCategoryResponse;
import gdsc.sparkling_thon.user.repository.UserCategoryRepository;
import gdsc.sparkling_thon.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;
    public final CategoryRepository categoryRepository;
    public final UserCategoryRepository userCategoryRepository;

    public void join(UserRequest request) {
        String loginTelNum = request.getTelNum();
        userDuplicateCheck(loginTelNum);

        UserEntity newUser = createUser(request);
        createUserCategory(newUser.getId(), request.getCategories());
    }

    public UserEntity createUser(UserRequest request){

        //테스트용 더미데이터
        double testLatitude = 37.5665;
        double testLongitude = 126.978;

        UserEntity userEntity = request.toEntity(request.getPwd(), testLatitude, testLongitude);
        return userRepository.save(userEntity);
    }

    public void createUserCategory(Long userId, List<CategoryRequest> categories) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));

        for (CategoryRequest categoryRequest : categories) {
            CategoryEntity category = categoryRepository.findById(categoryRequest.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));

            UserCategoryEntity userCategoryEntity = UserCategoryEntity.builder()
                    .user(user)
                    .category(category)
                    .build();

            userCategoryRepository.save(userCategoryEntity);
        }
    }

    public void userDuplicateCheck(String login_tel_num){
        userRepository.findByTelNum(login_tel_num)
                .ifPresent(userEntity -> {
                    throw new AppException(ErrorCode.USERID_DUPICATED);
                });
    }

    public void login(UserLoginRequest request, HttpServletResponse response) {
        String loginId = request.getLoginId();
        String pwd = request.getPwd();
        UserEntity userEntity = userRepository.findByTelNum(loginId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_FOUND));
        if (!pwd.equals(userEntity.getPwd())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        Cookie cookie = new Cookie("userId", userEntity.getTelNum());
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // 모든 경로에서 유효
        response.addCookie(cookie);
    }

    public List<UserCategoryResponse> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new UserCategoryResponse(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }
}
