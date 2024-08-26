package gdsc.sparkling_thon.book.domain.entity;

import gdsc.sparkling_thon.user.domain.UserCategoryEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name="category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "categories")  // 양방향 관계 설정
    private Set<BookEntity> books = new HashSet<>();  // 초기화

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserCategoryEntity> userCategories = new HashSet<>();

    // 책 추가 메서드 🎀
    public void addBook(BookEntity book) {
        this.books.add(book);
        book.getCategories().add(this);
    }
}
