package gdsc.sparkling_thon.book.domain.dto;

import gdsc.sparkling_thon.book.domain.entity.BookEntity;
import gdsc.sparkling_thon.book.domain.entity.OriginalBookEntity;
import lombok.Getter;

@Getter
public class BookInfoDto {
	private Long id;
	private String title;
	private String author;
	private String publisher;

	// OriginalBookEntity에서 필요한 데이터를 전달받아 초기화
	public BookInfoDto(Long id, String title, String author, String publisher) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
	}

	// OriginalBookEntity를 직접 받는 생성자 추가
	public BookInfoDto(OriginalBookEntity originalBookEntity) {
		this.id = originalBookEntity.getId();
		this.title = originalBookEntity.getTitle();
		this.author = originalBookEntity.getAuthor();
		this.publisher = originalBookEntity.getPublisher();
	}

	// BookEntity에서 필요한 데이터를 전달받아 초기화하는 생성자 추가
	public BookInfoDto(BookEntity bookEntity) {
		this.id = bookEntity.getId();
		this.title = bookEntity.getOriginalBook().getTitle();
		this.author = bookEntity.getOriginalBook().getAuthor();
		this.publisher = bookEntity.getOriginalBook().getPublisher();
	}
}
