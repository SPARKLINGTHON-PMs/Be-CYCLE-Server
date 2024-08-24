package gdsc.sparkling_thon.user.domain;

import gdsc.sparkling_thon.book.domain.CategoryEntity;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Table(name = "app_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
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

    // to-do: 나중에 연결
    @Column(nullable = false)
    private String category;

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
