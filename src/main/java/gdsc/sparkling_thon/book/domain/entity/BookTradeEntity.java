package gdsc.sparkling_thon.book.domain.entity;

import gdsc.sparkling_thon.book.domain.enums.TradeStateEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_trade")
public class BookTradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BookEntity buyBook;

    @ManyToOne
    private BookEntity sellBook;

    @Enumerated(value = EnumType.STRING)
    @Column
    private TradeStateEnum status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public BookTradeEntity(BookEntity buyBook, BookEntity sellBook, TradeStateEnum status) {
        this.buyBook = buyBook;
        this.sellBook = sellBook;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(TradeStateEnum status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
