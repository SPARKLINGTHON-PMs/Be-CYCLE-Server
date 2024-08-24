package gdsc.sparkling_thon.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginRequest {

    private String loginId;
    private String pwd;

    @Builder
    public UserLoginRequest(String loginId, String pwd) {
        this.loginId = loginId;
        this.pwd = pwd;
    }
}
