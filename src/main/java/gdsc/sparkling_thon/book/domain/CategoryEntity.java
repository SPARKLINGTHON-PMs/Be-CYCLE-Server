package gdsc.sparkling_thon.book.domain;

import gdsc.sparkling_thon.user.domain.UserCategoryEntity;
import gdsc.sparkling_thon.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name="category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private Set<OriginalBookEntity> books;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCategoryEntity> userCategories = new HashSet<>();
}
