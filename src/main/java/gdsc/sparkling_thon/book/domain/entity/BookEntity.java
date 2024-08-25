package gdsc.sparkling_thon.book.domain.entity;


import gdsc.sparkling_thon.book.domain.enums.BookStateEnum;
import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import gdsc.sparkling_thon.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private OriginalBookEntity originalBook;

    @Column(nullable = false)
    private String image;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private BookStateEnum status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TradeStateEnum tradeState;

    // 다대다 관계 설정 (카테고리)
    @ManyToMany
    @JoinTable(
        name = "book_category",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<CategoryEntity> categories;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public BookEntity(UserEntity user, OriginalBookEntity originalBook, String image, BookStateEnum status, TradeStateEnum tradeState, Set<CategoryEntity> categories) {
        this.user = user;
        this.originalBook = originalBook;
        this.image = image;
        this.status = status;
        this.tradeState = tradeState;
        this.categories = categories;
        this.createdAt = LocalDateTime.now(); // 자동 생성
        this.updatedAt = LocalDateTime.now(); // 자동 생성
    }
}
