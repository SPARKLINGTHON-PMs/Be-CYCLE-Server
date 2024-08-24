package gdsc.sparkling_thon.user.controller;

import gdsc.sparkling_thon.user.dto.request.UserLoginRequest;
import gdsc.sparkling_thon.user.dto.request.UserRequest;
import gdsc.sparkling_thon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> login(@RequestBody UserLoginRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }
}
