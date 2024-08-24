package gdsc.sparkling_thon.user.domain;

import gdsc.sparkling_thon.book.domain.CategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCategoryEntity> userCategories = new HashSet<>();

    @Builder
    public UserEntity(String telNum, String name, String pwd, String address, Double latitude, Double longitude) {
        this.telNum = telNum;
        this.name = name;
        this.pwd = pwd;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //유저의 카테코리 목록을 가져오는 메소드
    public List<CategoryEntity> getCategories() {
        return userCategories.stream()
                .map(UserCategoryEntity::getCategory)
                .collect(Collectors.toList());
    }
}
