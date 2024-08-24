package gdsc.sparkling_thon.book.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "original_book", indexes = @Index(name="title_author_index", columnList = "title, author"))
public class OriginalBookEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private LocalDateTime publishedAt;

    @ManyToMany
    private List<CategoryEntity> categories;
}
