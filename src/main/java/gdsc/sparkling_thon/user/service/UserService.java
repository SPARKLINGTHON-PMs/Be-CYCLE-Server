package gdsc.sparkling_thon.user.service;

import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
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

import javax.security.sasl.AuthenticationException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;

    public void join(UserRequest request) {
        String loginTelNum = request.getTelNum();
        userDuplicateCheck(loginTelNum);

        UserEntity newUser = createUser(request);
        createUserCategory(newUser.getId(), request.getCategories());
    }

    private UserEntity createUser(UserRequest request) {
        // pwd가 null이 아닌지 확인하는 로직 추가
        if (request.getPwd() == null || request.getPwd().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 항목입니다.");
        }

        UserEntity userEntity = request.toEntity(request.getPwd(), request.getLatitude(), request.getLongitude());
        return userRepository.save(userEntity);
    }

    private void createUserCategory(Long userId, List<CategoryRequest> categories) {
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

    private void userDuplicateCheck(String loginTelNum) {
        userRepository.findByTelNum(loginTelNum)
            .ifPresent(userEntity -> {
                throw new AppException(ErrorCode.USERID_DUPICATED);
            });
    }

    public Cookie login(UserLoginRequest request, HttpServletResponse response) {
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

        return cookie;
    }

    public List<UserCategoryResponse> getAllCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
            .map(category -> new UserCategoryResponse(category.getId(), category.getName()))
            .collect(Collectors.toList());
    }
}
