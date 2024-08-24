package gdsc.sparkling_thon.user.domain;

import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String telNum;

    @ManyToMany
    private Set<CategoryEntity> interestCateogries;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String city;

    // TODO: 나중에 연결
    @Column(nullable = false)
    private String category;

    @Column(nullable = true)  // nullable을 허용하여 FCM 토큰이 없는 경우도 처리
    private String fcmToken;


    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    @Builder
    public UserEntity(String telNum, String name, String pwd, String province, String city, String category) {
        this.telNum = telNum;
        this.name = name;
        this.pwd = pwd;
        this.province = province;
        this.city = city;
        this.category = category;
    }
}
