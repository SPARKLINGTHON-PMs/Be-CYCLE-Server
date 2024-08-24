package gdsc.sparkling_thon.book.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "original_book", indexes = @Index(name="title_author_index", columnList = "title, author"))
public class OriginalBookEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String publisher;

    @Column
    private Date publicationDate;

    @ManyToMany
    private List<CategoryEntity> categories;
}
