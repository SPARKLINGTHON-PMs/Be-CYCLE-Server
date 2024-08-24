package gdsc.sparkling_thon.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tel_num;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String city;

    // to-do: 나중에 연결
    @Column(nullable = false)
    private String category;

    @Builder
    public User(String tel_num, String name, String pwd, String province, String city, String category) {
        this.tel_num = tel_num;
        this.name = name;
        this.pwd = pwd;
        this.province = province;
        this.city = city;
        this.category = category;
    }
}
