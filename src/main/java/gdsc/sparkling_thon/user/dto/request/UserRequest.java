package gdsc.sparkling_thon.user.dto.request;

import gdsc.sparkling_thon.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRequest {

    private String telNum;
    private String name;
    private String pwd;
    private String province;
    private String city;
    private String category; // to-do: enum 설정

    @Builder
    public UserRequest(String telNum, String name, String pwd, String province, String city, String category) {
        this.telNum = telNum;
        this.name = name;
        this.pwd = pwd;
        this.province = province;
        this.city = city;
        this.category = category;
    }

    public UserEntity toEntity(String encodedPwd) {
        return UserEntity.builder()
                .telNum(telNum)
                .name(name)
                .pwd(encodedPwd)
                .province(province)
                .city(city)
                .category(category)
                .build();
    }
}
