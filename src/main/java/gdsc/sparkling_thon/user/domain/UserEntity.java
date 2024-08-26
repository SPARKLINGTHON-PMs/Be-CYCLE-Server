package gdsc.sparkling_thon.user.domain;

import gdsc.sparkling_thon.book.domain.entity.CategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCategoryEntity> userCategories = new HashSet<>();

    @Builder
    public UserEntity(String telNum, String name, String pwd, String address, double latitude, double longitude) {
        this.telNum = telNum;
        this.name = name;
        this.pwd = pwd;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
