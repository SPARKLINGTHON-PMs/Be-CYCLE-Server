package gdsc.sparkling_thon.book.domain.entity;

import java.util.HashSet;
import java.util.Set;

import gdsc.sparkling_thon.book.domain.enums.BookStateEnum;
import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import gdsc.sparkling_thon.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_book_id", nullable = false)
    private OriginalBookEntity originalBook;

    @Column(name = "image_id")
    private Long imageId;  // 이미지 ID 필드 추가 🎀

    @Enumerated(EnumType.STRING)
    private BookStateEnum status;

    @Enumerated(EnumType.STRING)
    private TradeStateEnum tradeState;

    @ManyToMany
    @JoinTable(
        name = "book_category",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories = new HashSet<>();  // 초기화

    @Builder
    public BookEntity(UserEntity user, OriginalBookEntity originalBook, Long imageId,
        BookStateEnum status, TradeStateEnum tradeState, Set<CategoryEntity> categories) {
        this.user = user;
        this.originalBook = originalBook;
        this.imageId = imageId;
        this.status = status;
        this.tradeState = tradeState;
        this.categories = categories != null ? categories : new HashSet<>(); // 카테고리가 null이면 빈 Set으로 초기화
    }

    // 카테고리 추가 메서드 🎀
    public void addCategory(CategoryEntity category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }
}
