package gdsc.sparkling_thon.user.dto.request;

import gdsc.sparkling_thon.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserRequest {

    private String telNum;
    private String name;
    private String pwd;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<CategoryRequest> categories;

    @Builder
    public UserRequest(String telNum, String name, String pwd, String address) {
        this.telNum = telNum;
        this.name = name;
        this.pwd = pwd;
        this.address = address;
    }

    public UserEntity toEntity(String pwd, Double latitude, Double longitude) {
        return UserEntity.builder()
                .telNum(telNum)
                .name(name)
                .pwd(pwd)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
