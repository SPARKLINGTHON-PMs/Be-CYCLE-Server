package gdsc.sparkling_thon.user.controller;

import gdsc.sparkling_thon.user.dto.request.UserLoginRequest;
import gdsc.sparkling_thon.user.dto.request.UserRequest;
import gdsc.sparkling_thon.user.dto.response.UserCategoryResponse;
import gdsc.sparkling_thon.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserRequest request){
        userService.join(request);
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request, HttpServletResponse response){
        Cookie cookie = userService.login(request, response);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.CREATED).body("로그인이 성공했습니다.");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<UserCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(userService.getAllCategories());
    }
}
